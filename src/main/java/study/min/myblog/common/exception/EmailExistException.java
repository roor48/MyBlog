package study.min.myblog.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
      super(message, cause);
    }
}
