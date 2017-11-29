package main.cn.lc.MyThreadPoolExecutor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by luochao.byron on 2017/7/10.
 */
public class ThreadPoolExecutor implements ExecutorService {

    private static int DEFAULT_MAXPS = 1 << 29 - 1;

    private static int DEFAULT_MINPS = 5;

    private volatile int maxPoolthreadSize = 16;

    private volatile int minPoolThreadSize = 5;

    private volatile int corePoolSize = 6;

    // ��ʾ�߳�״̬ ����λ��ʾ״̬ ��29λ��ʾ�̳߳ع����߳�
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private void decrementWorkerCount() {
        do {
        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    private final BlockingQueue<Runnable> workQueue;

    private ReentrantLock mainLock = new ReentrantLock();

    private Condition termination = mainLock.newCondition();

    private HashSet<Worker> workers = new HashSet<>();

    private ThreadPoolExecutor(int Maxps, int Minps, BlockingQueue workQueue) {
        this.workQueue = workQueue;
    }

    public static ThreadPoolExecutor newInstance() {
        return new ThreadPoolExecutor(DEFAULT_MAXPS, DEFAULT_MAXPS, new LinkedBlockingQueue());
    }
    final void tryTerminate() {
        for (;;) {
            int c = ctl.get();
            if (isRunning(c) ||
                    runStateAtLeast(c, TIDYING) ||
                    (runStateOf(c) == SHUTDOWN && ! workQueue.isEmpty()))
                return;

            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try {

                    } finally {
                        ctl.set(ctlOf(TERMINATED, 0));
                        termination.signalAll();
                    }
                    return;
                }
            } finally {
                mainLock.unlock();
            }
            // else retry on failed CAS
        }
    }
    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        while(!workQueue.isEmpty()){}
        for (;;) {
            int c = ctl.get();
            if (runStateAtLeast(c, STOP) ||
                    ctl.compareAndSet(c, ctlOf(STOP, workerCountOf(c))))
                break;
        }
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
            }
        } finally {
            mainLock.unlock();
        }
        //tryTerminate();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();

        if (workerCountOf(ctl.get()) >= corePoolSize || !addIfUnderCorePoolSize(command)) {
            if (isRunning(ctl.get()) && workQueue.offer(command)) {
                System.out.println("add queue "+workQueue.size());
            }
            // �н���� ���ܾ�����
            else if (addIfUnderMaximumPoolSize()) {
                //reject
            }
        }
    }

    private boolean addIfUnderMaximumPoolSize() {
        return false;
    }

    private boolean addIfUnderCorePoolSize(Runnable command) {
        Thread thread = null;
        final ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            if (workerCountOf(ctl.get()) <= corePoolSize && isRunning(ctl.get())) {
                thread = addThread(command);
            }
            if (thread != null) {
                thread.start();
                return true;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            reentrantLock.unlock();
        }
        return false;
    }

    private Thread addThread(Runnable command) {
        Worker worker = new Worker(command);
        Thread thread = ThreadFactory.newThread(worker);
        if (thread != null) {
            compareAndIncrementWorkerCount(ctl.get());
            worker.thread = thread;
            workers.add(worker);
        }
        return thread;
    }


    public class Worker extends AbstractQueuedSynchronizer implements Runnable {

        private ThreadPoolExecutor threadPoolExecutor;

        public Thread thread;

        private Runnable firstTask;


        public Worker(Runnable command) {
            this.firstTask = command;
        }

        protected boolean tryAcquire(int unused) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int unused) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public void lock() {
            acquire(1);
        }

        public boolean tryLock() {
            return tryAcquire(1);
        }

        public void unlock() {
            release(1);
        }

        public boolean isLocked() {
            return isHeldExclusively();
        }

        @Override
        public void run() {
            runTask(this);
        }

        private void runTask(Worker worker) {
            Thread thread = worker.thread;
            Runnable task = worker.firstTask;
            worker.firstTask = null;

            try {
                while (task != null || (task = getTask()) != null) {
                    worker.lock();
                    try {
                        task.run();
                    } finally {
                        task = null;
                        worker.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }

        public Runnable getTask()   {

            for (; ; ) {
                int c = ctl.get();
                int rs = runStateOf(c);
                // Check if queue empty only if necessary.
                if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                    decrementWorkerCount();
                    return null;
                }
                if (isRunning(ctl.get()) && workerCountOf(ctl.get()) < maxPoolthreadSize) {
                    Runnable r = null;
                    try {
                        r = workQueue.take();
                    } catch (InterruptedException e) {
                    }
                    if (r != null)
                        return r;
                }
            }
        }
    }
}

class ThreadFactory {
    public static Thread newThread(ThreadPoolExecutor.Worker worker) {
        return new Thread(worker);
    }
}
