package com.lc.misc.classLoader;
public class Demo01 {
	static{
		System.out.println("��̬��ʼ��Demo01");
	}

	public static void main(String[] args) throws Exception {
		//System.out.println("Demo01��main������");
		//System.out.println(System.getProperty("java.class.path"));
		//��������
	//	new A();
	//System.out.println(A.width);
	//Class.forName("main.cn.lc.classLoader.A");
		
		
		//��������
		System.out.println(A.MAX);
		A[] as = new A[10];
		System.out.println(B.width);

	}
}

class B  extends A {
	static {
		System.out.println("��̬��ʼ��B");
	}
}

class A extends A_Father {
	public static int width=100;   //��̬��������̬��    field
	public static final  int MAX=100; 
	
	static {
		System.out.println("��̬��ʼ����A");
		width=300;
	}
	public A(){
		System.out.println("����A��Ķ���");
	}
}

class A_Father extends Object {
	static {
		System.out.println("��̬��ʼ��A_Father");
	}
}
