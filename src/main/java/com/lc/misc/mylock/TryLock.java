package com.lc.misc.mylock;

import java.util.concurrent.TimeUnit;

public interface TryLock{

    void lock();

    boolean trylock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

}
