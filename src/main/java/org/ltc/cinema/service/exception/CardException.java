package org.ltc.cinema.service.exception;

/**
 * @author TXG
 * @date 2022/8/7$
 * 会员卡相关的异常
 */
public class CardException extends ServiceException{
    public CardException() {
        super();
    }

    public CardException(String message) {
        super(message);
    }

    public CardException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardException(Throwable cause) {
        super(cause);
    }

    protected CardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
