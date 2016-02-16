package com.benson.exception;

/**
 * Created by zhangchen on 2016/2/16.
 */
public class JobException extends Exception {
    public JobException() {
    }

    public JobException(String message) {
        super(message);
    }

    public JobException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobException(Throwable cause) {
        super(cause);
    }
}
