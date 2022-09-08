package cn.spdb.harrier.api.service.system;

public interface ISysConfigService {

	boolean selectCaptchaOnOff(String key);

	Object selectConfigByKey(String string);

}
