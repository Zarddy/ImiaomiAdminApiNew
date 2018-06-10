package cn.imiaomi.admin.api.exception;

public class ImiaoException extends Exception {

    public ImiaoException() {
    }

    public ImiaoException(String message) {
        super(message);
    }

    public ImiaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
