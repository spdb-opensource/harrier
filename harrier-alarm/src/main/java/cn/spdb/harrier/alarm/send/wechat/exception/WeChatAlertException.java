
package cn.spdb.harrier.alarm.send.wechat.exception;

public class WeChatAlertException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Create Runtime Exception
     *
     * @param errMsg - Error message
     */
    public WeChatAlertException(String errMsg) {
        super(errMsg);
    }
}
