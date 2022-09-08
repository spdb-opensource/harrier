package cn.spdb.harrier.rpc.client;

import cn.spdb.harrier.rpc.common.ConsumerConfigConstant;
import cn.spdb.harrier.rpc.common.DefaultRpcCallBack;
import cn.spdb.harrier.rpc.common.InterfaceRpcCallBack;
import cn.spdb.harrier.rpc.compress.CompressEnum;

public class ConsumerConfig {

	private String handlerMethName;

	private Boolean async = ConsumerConfigConstant.DEFAULT_SYNC;
	private Boolean callBack = ConsumerConfigConstant.DEFAULT_CALL_BACK;
	private Integer retries = ConsumerConfigConstant.DEFAULT_RETRIES;
	private Class<? extends InterfaceRpcCallBack> seriveCallBackClass = DefaultRpcCallBack.class;
	private Byte compressType = CompressEnum.NO.getType();
	private Integer timeOut=ConsumerConfigConstant.TIME_OUT;
	
	public boolean getAsync() {
		return async;
	}

	public int getRetries() {
		return retries;
	}

	public Class<? extends InterfaceRpcCallBack> getSeriveCallBackClass() {
		return seriveCallBackClass;
	}

	public void setSeriveCallBackClass(Class<? extends InterfaceRpcCallBack> seriveCallBackClass) {
		this.seriveCallBackClass = seriveCallBackClass;
	}

	public String getHandlerMethName() {
		return handlerMethName;
	}

	public void setHandlerMethName(String handlerMethName) {
		this.handlerMethName = handlerMethName;
	}

	public Boolean getCallBack() {
		return callBack;
	}

	public void setCallBack(Boolean callBack) {
		this.callBack = callBack;
	}

	public void setAsync(Boolean async) {
		this.async = async;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public Byte getCompressType() {
		return compressType;
	}

	public void setCompressType(Byte compressType) {
		this.compressType = compressType;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

}
