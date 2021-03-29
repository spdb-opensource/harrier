package cn.com.spdb.uds.core.rpc.event;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import cn.com.spdb.uds.UdsConstant;

/**
 * 消息事件
 * 
 * @author T-luzl
 *
 */
public class UdsRpcEvent implements Cloneable {

	/** 目标服务器 */
	@Protobuf
	private String targetId;
	/** 源服务器 */
	@Protobuf
	private String sourceId;
	/** 命令ID */
	@Protobuf
	private int command;
	/** 数据 */
	@Protobuf
	private String data;
	/** 转发数据类型 */
	@Protobuf
	private byte[] bytes;
	/** 是否为回调信息 */
	@Protobuf
	private boolean isCallBack = false;
	/*** 重调次数 */
	private transient byte num = 0;

	/** 属性数据 */
	private transient Map<String, Object> attributes = new HashMap<String, Object>();

	public static UdsRpcEvent buildUdsRpcEvent(String serverName, int command) {
		UdsRpcEvent udsRpcEvent = new UdsRpcEvent();
		udsRpcEvent.setTargetId(serverName);
		udsRpcEvent.setSourceId(UdsConstant.SERVER_NAME);
		udsRpcEvent.setCommand(command);
		return udsRpcEvent;
	}

	public static UdsRpcEvent buildBroadcastEvent(int command) {
		UdsRpcEvent udsRpcEvent = new UdsRpcEvent();
		udsRpcEvent.setSourceId(UdsConstant.SERVER_NAME);
		udsRpcEvent.setCommand(command);
		return udsRpcEvent;
	}

	public UdsRpcEvent callBackEvent() {
		UdsRpcEvent callBackEvent = new UdsRpcEvent();
		callBackEvent.sourceId = this.targetId;
		callBackEvent.targetId = this.sourceId;
		callBackEvent.command = this.command;
		callBackEvent.isCallBack = true;
		return callBackEvent;
	}

	/**
	 * 序列化
	 */
	public void outputSerialize() {
		this.data = JSONArray.toJSONString(attributes);
	}

	/**
	 * 反序列化
	 */
	public void inputSerialize() {
		this.attributes = JSONArray.parseObject(data, HashMap.class);
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public <T> T getAttribute(String key) {
		return (T) attributes.get(key);
	}

	public void addAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public boolean isCallBack() {
		return isCallBack;
	}

	public void setCallBack(boolean isCallBack) {
		this.isCallBack = isCallBack;
	}

	@Override
	public String toString() {
		return "UdsRpcEvent [targetId=" + targetId + ", sourceId=" + sourceId + ", command=" + command + ", isCallBack="
				+ isCallBack + "]";
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public byte getNum() {
		return num;
	}

	public void setNum(byte num) {
		this.num = num;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public UdsRpcEvent clone() {
		UdsRpcEvent event = UdsRpcEvent.buildBroadcastEvent(this.command);
		event.setAttributes(this.attributes);
		event.setBytes(this.getBytes());
		event.setCallBack(this.isCallBack);
		event.setCommand(this.command);
		event.setData(this.data);
		event.setNum(this.num);
		event.setSourceId(this.sourceId);
		event.setTargetId(this.targetId);
		return event;
	}

}
