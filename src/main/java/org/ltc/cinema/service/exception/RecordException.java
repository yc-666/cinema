package org.ltc.cinema.service.exception;

/**
 * @author PpxiA
 */
public class RecordException extends ServiceException{
  public RecordException() {
    super();
  }

  public RecordException(String message) {
    super(message);
  }

  public RecordException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecordException(Throwable cause) {
    super(cause);
  }

  protected RecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
