
package cn.spdb.harrier.api.enums;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * status enum      
 */
public enum Status {

    SUCCESS(0, "success", "成功"),
    INTERNAL_SERVER_ERROR_ARGS(10000, "Internal Server Error: {0}", "服务端异常: {0}"),
    SUBMIT_AGAIN(10001,"do not submit again,try again later","不允许重复提交，请稍候再试"),
    ACCESS_TOKEN_NOT_EXIST(10002,"access token is not exist","认证用户Token不存在"), 
    REGISTER_NOT_OPEN(10003,"register not open","注册未开启"),
    EXIT_SUCCESS(10004,"exit sucess","退出成功"), 
    USER_LOGIN_FAILURE(10005,"user login failuer","登录失败"),
    QUERY_UNAUTHORIZED_PROJECT_ERROR(10006, "query unauthorized project error", "查询未授权项目错误"),
    
    ;

 
	
	

    private final int code;
    private final String enMsg;
    private final String zhMsg;

    Status(int code, String enMsg, String zhMsg) {
        this.code = code;
        this.enMsg = enMsg;
        this.zhMsg = zhMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
            return this.zhMsg;
        } else {
            return this.enMsg;
        }
    }
}
