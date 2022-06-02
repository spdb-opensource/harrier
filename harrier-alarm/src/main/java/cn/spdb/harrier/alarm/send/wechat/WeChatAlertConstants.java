
package cn.spdb.harrier.alarm.send.wechat;

/**
 * WeChatAlertConstants
 */
public class WeChatAlertConstants {

    private WeChatAlertConstants() {
        throw new IllegalStateException(WeChatAlertConstants.class.getName());
    }

    static final String MARKDOWN_QUOTE = ">";

    static final String MARKDOWN_ENTER = "\n";

    static final String CHARSET = "UTF-8";

    static final String WE_CHAT_PUSH_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token={token}";

    static final String WE_CHAT_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={secret}";
}
