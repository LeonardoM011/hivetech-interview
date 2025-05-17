package hr.leonardom011.hivetechinterview.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

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
}
