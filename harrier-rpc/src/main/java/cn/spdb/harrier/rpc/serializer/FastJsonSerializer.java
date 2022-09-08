package cn.spdb.harrier.rpc.serializer;

import com.alibaba.fastjson.JSON;

public class FastJsonSerializer implements Serializer {


	
	@Override
	public <T> byte[] serialize(T object) {
		return JSON.toJSONBytes(object);
	}

	@Override
	public <T> T deserialize(byte[] body, Class<T> clazz) {
		return JSON.parseObject(body, clazz);
	}

}