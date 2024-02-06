package KTE.Labs.exceptions;

import KTE.Labs.models.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> handleObjectNotFound(final ObjectNotFoundException e) {
        return createAppError(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleConflict(final ConflictException e) {
        return createAppError(e, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<AppError> createAppError(Throwable e, HttpStatus status) {
        return new ResponseEntity<>(
                AppError.builder()
                        .message(e.getMessage())
                        .build(),
                status
        );
    }
}
