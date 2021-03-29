package cn.com.spdb.uds.core.rpc.handler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通讯协议
 * 
 * @author T-luzl 包扫描范围 com.amarsoft.uds.core.rpc.protocol
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcEventProtocol {

	int value() default 0;

	int concurrent() default 0;

}
