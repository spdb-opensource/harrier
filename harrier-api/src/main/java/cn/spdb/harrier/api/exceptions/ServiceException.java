
package cn.spdb.harrier.api.exceptions;

import cn.spdb.harrier.api.enums.Status;


/**
 * service exception
 */
public class ServiceException extends RuntimeException {

    /**
     * code
     */
    private Integer code;

    public ServiceException() {
    }

    public ServiceException(Status status) {
        super(status.getMsg());
        this.code = status.getCode();
    }

    public ServiceException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}