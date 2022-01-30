package dachertanov.pal.palbackendservice.controller.handler;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ResponseStatus responseStatus =
                AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            return ResponseEntity.status(responseStatus.value()).body(new ErrorResponse(ex.getMessage()));
        }

        if (ex instanceof HttpClientErrorException) {
            HttpClientErrorException exception = (HttpClientErrorException) ex;
            return ResponseEntity.status((exception.getRawStatusCode()))
                    .body(new ErrorResponse(exception.getStatusText(), String.valueOf(exception.getRawStatusCode())));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
