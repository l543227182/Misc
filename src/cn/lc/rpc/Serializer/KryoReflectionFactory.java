package cn.lc.rpc.Serializer;

import java.lang.reflect.InvocationHandler;
import java.net.URI;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import cn.lc.rpc.RpcRequest;
import cn.lc.rpc.RpcResponse;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import de.javakaffee.kryoserializers.*;


public class KryoReflectionFactory extends KryoReflectionFactorySupport
{
	public KryoReflectionFactory()
	{
		this.setRegistrationRequired(false);
		setReferences(true); 
		register(RpcRequest.class, new RpcRequestSerializer());
		register(RpcResponse.class, new RpcResponseSerializer());
        register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
        register(Collections.EMPTY_LIST.getClass(), new DefaultSerializers.CollectionsEmptyListSerializer());
        register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
        register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
        register(Collections.singletonList("").getClass(), new CollectionsSingletonListSerializer());
        register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer());
        register(Collections.singletonMap("", "").getClass(), new DefaultSerializers.CollectionsSingletonMapSerializer());
        register(Pattern.class, new RegexSerializer());
        register(BitSet.class, new BitSetSerializer());
        register(URI.class, new URISerializer());
        register(UUID.class, new UUIDSerializer());
        register(GregorianCalendar.class, new GregorianCalendarSerializer());
        register(InvocationHandler.class, new JdkProxySerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(this);
        SynchronizedCollectionsSerializer.registerSerializers(this);
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Serializer<?> getDefaultSerializer(Class clazz)
	{		
		if(EnumSet.class.isAssignableFrom(clazz)) 
			return new EnumSetSerializer();
        
        if(EnumMap.class.isAssignableFrom(clazz))
            return new EnumMapSerializer();
        
        if(Collection.class.isAssignableFrom(clazz))
            return new CopyForIterateCollectionSerializer();
        
        if(Map.class.isAssignableFrom(clazz)) 
            return new CopyForIterateMapSerializer();
        
        if(Date.class.isAssignableFrom(clazz))
            return new DateSerializer( clazz );
        
        if (SubListSerializers.ArrayListSubListSerializer.canSerialize(clazz) 
        		|| SubListSerializers.JavaUtilSubListSerializer.canSerialize(clazz))
			return SubListSerializers.createFor(clazz);		
        
        return super.getDefaultSerializer(clazz);
	}	
}
