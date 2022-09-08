

package cn.spdb.harrier.alarm.send.email.exception;

public class AlertEmailException extends RuntimeException {

    /**
     * Create Runtime exception
     *
     * @param errMsg - Error message
     */
    public AlertEmailException(String errMsg) {
        super(errMsg);
    }

    /**
     * Create Runtime exception
     *
     * @param errMsg - Error message
     * @param cause - cause
     */
    public AlertEmailException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }
}
