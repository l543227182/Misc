package com.lc.misc.demo;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    //�����������
    private String name;
    //�������·��
    private String path = "D:/";
    private final String fileType = ".class";

    public MyClassLoader(String name) {
        //��ϵͳ���������Ϊ�� ��������ĸ�������
        super();
        this.name = name;
    }

    public MyClassLoader(ClassLoader parent, String name) {
        //��ʾָ������������ĸ�������
        super(parent);
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * ��ȡ.class�ļ����ֽ�����
     *
     * @param name
     * @return
     */
    private byte[] loaderClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.name = this.name.replace(".", "/");
        try {
            is = new FileInputStream(new File(path + name + fileType));
            int c = 0;
            while (-1 != (c = is.read())) {
                baos.write(c);
            }
            data = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * ��ȡClass����
     */
    @Override
    public Class<?> findClass(String name) {
        byte[] data = loaderClassData(name);
        System.out.println(data);
        return this.defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
  /*      //loader1�ĸ�������Ϊϵͳ�������
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("D:/");
        //loader2�ĸ�������Ϊloader1
        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
        loader2.setPath("D:/");
        //loader3�ĸ�������Ϊ���������*/
        MyClassLoader loader = new MyClassLoader("loader3");
        loader.setPath("D:\\");
        Class clazz = loader.loadClass("Sample");
        Object object = clazz.newInstance();
    }
}

class Sample {

    public Sample() {
        System.out.println("Sample is loaded by " + this.getClass().getClassLoader());
        new A();
    }
}

class A {

    public A() {
        System.out.println("A is loaded by " + this.getClass().getClassLoader());
    }
}