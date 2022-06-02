package cn.spdb.harrier.api.service.system;

public interface ISysConfigService {

	boolean selectCaptchaOnOff();

	Object selectConfigByKey(String string);

}
