package hr.leonardom011.hivetechinterview.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class KanbanException extends RuntimeException {

    private final HttpStatus statusCode;

    public KanbanException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}

