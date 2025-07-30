package study.min.myblog.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException; // 비밀번호 불일치 예외
import org.springframework.security.core.userdetails.UsernameNotFoundException; // 사용자 없음 예외
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import study.min.myblog.common.exception.EmailExistException;

@Slf4j
@ControllerAdvice // 애플리케이션의 모든 컨트롤러에 걸쳐 예외를 처리합니다.
public class GlobalExceptionHandler {

    /**
     * EmailExistException (이메일 중복) 예외를 처리합니다.
     * @param ex 발생한 EmailExistException 객체
     * @return 409 Conflict 상태 코드와 에러 메시지를 포함하는 응답
     */
    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<Map<String, String>> handleEmailExistException(EmailExistException ex) {
        log.info("Email Exist Exception 발생");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.CONFLICT.toString()); // HTTP 409 Conflict
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * MethodArgumentNotValidException (DTO 유효성 검사 실패) 예외를 처리합니다.
     * @param ex 발생한 MethodArgumentNotValidException 객체
     * @return 400 Bad Request 상태 코드와 유효성 검사 실패 상세 정보를 포함하는 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("Method Argument Not Valid Exception 발생");
        
        Map<String, String> errors = new HashMap<>();
        // 모든 필드 에러를 순회하며 필드명과 에러 메시지를 추출하여 맵에 저장
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "유효성 검사 실패");
        errorResponse.put("details", errors.toString()); // 상세 오류 내용
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString()); // HTTP 400 Bad Request
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * UsernameNotFoundException (사용자를 찾을 수 없음) 예외를 처리합니다.
     * Spring Security의 loadUserByUsername 메서드에서 발생할 수 있습니다.
     * @param ex 발생한 UsernameNotFoundException 객체
     * @return 404 Not Found 또는 401 Unauthorized 상태 코드와 메시지를 포함하는 응답
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.info("Username Not Found Exception 발생");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.toString()); // 404 Not Found (사용자 리소스를 찾을 수 없음)

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * BadCredentialsException (비밀번호 불일치) 예외를 처리합니다.
     * Spring Security의 인증 과정에서 발생할 수 있습니다.
     * @param ex 발생한 BadCredentialsException 객체
     * @return 401 Unauthorized 상태 코드와 메시지를 포함하는 응답
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
        log.info("Bad Credentials Exception 발생");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "이메일 또는 비밀번호가 일치하지 않습니다."); // 보안을 위해 구체적인 메시지 대신 일반적인 메시지
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.toString()); // HTTP 401 Unauthorized
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    /**
     * 그 외 예상치 못한 모든 일반 예외를 처리합니다.
     * @param ex 발생한 Exception 객체
     * @return 500 Internal Server Error 상태 코드와 에러 메시지를 포함하는 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.info("알 수 없는 에러 발생");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "서버 오류가 발생했습니다: " + ex.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString()); // HTTP 500 Internal Server Error
        ex.printStackTrace(); // 개발 중에는 스택 트레이스를 출력하여 오류를 확인합니다. 운영에서는 로깅 시스템 사용.
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
