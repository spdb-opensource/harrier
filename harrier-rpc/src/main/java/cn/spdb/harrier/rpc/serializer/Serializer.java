package cn.spdb.harrier.rpc.serializer;

public interface Serializer {

	<T> byte[] serialize(T object);

	<T> T deserialize(byte[] body, Class<T> clazz);
}
