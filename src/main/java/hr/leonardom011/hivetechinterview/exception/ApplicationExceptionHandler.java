package hr.leonardom011.hivetechinterview.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.security.GeneralSecurityException;

@ControllerAdvice
public class ApplicationExceptionHandler implements ProblemHandling {

    @ExceptionHandler(KanbanException.class)
    public ResponseEntity<Problem> handleEasyBookException(KanbanException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(
                Problem.builder()
                        .withTitle(exception.getStatusCode().getReasonPhrase())
                        .withDetail(exception.getMessage())
                        .withStatus(Status.valueOf(exception.getStatusCode().value()))
                        .build()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Problem> handleAuthenticationException(AuthenticationException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(
                Problem.builder()
                        .withTitle(status.getReasonPhrase())
                        .withDetail(exception.getMessage())
                        .withStatus(Status.valueOf(status.value()))
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Problem> handleAccessDeniedException(AccessDeniedException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(
                Problem.builder()
                        .withTitle(status.getReasonPhrase())
                        .withDetail(exception.getMessage())
                        .withStatus(Status.valueOf(status.value()))
                        .build()
        );
    }

    @ExceptionHandler(GeneralSecurityException.class)
    public ResponseEntity<Problem> handleGeneralSecurityException(GeneralSecurityException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(
                Problem.builder()
                        .withTitle(status.getReasonPhrase())
                        .withDetail(exception.getMessage())
                        .withStatus(Status.valueOf(status.value()))
                        .build()
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Problem> handleJwtException(JwtException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(
                Problem.builder()
                        .withTitle(status.getReasonPhrase())
                        .withDetail(exception.getMessage())
                        .withStatus(Status.valueOf(status.value()))
                        .build()
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Problem> handleNoResourceFoundException(NoResourceFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(
                Problem.builder()
                        .withTitle(status.getReasonPhrase())
                        .withDetail(exception.getMessage())
                        .withStatus(Status.valueOf(status.value()))
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleOtherExceptions(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(
                Problem.builder()
                        .withTitle(status.getReasonPhrase())
                        .withDetail("Unknown internal server error.")
                        .withStatus(Status.valueOf(status.value()))
                        .build()
        );
    }
}
