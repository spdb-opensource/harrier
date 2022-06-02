package cn.spdb.harrier.rpc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.spdb.harrier.rpc.compress.CompressEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcMethod {

	int retries() default 1;

	boolean async() default false;

	int timeOut() default 3000;
	
	CompressEnum compressType() default CompressEnum.NO;
	
	Class<? extends InterfaceRpcCallBack> serviceCallBack() default DefaultRpcCallBack.class;

}
