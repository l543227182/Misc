package main.cn.lc.rpc.Serializer;


import com.esotericsoftware.kryo.Kryo;

public class KryoHolder
{
	private static ThreadLocal<Kryo> threadLocalKryo = new ThreadLocal<Kryo>()
	{
		protected Kryo initialValue() 
		{
			Kryo kryo = new KryoReflectionFactory();
						
			return kryo;
		};
	};
	
	public static Kryo get()
	{
		return threadLocalKryo.get();
	}
}