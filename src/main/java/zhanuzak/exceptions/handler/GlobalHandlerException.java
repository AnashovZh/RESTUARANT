package zhanuzak.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zhanuzak.exceptions.exception.*;
import zhanuzak.exceptions.exceptionResponse.ExceptionResponse;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException n) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .exceptionClassName(n.getClass().getSimpleName())
                .message(n.getMessage())
                .build();
    }

    @ExceptionHandler(MaxUsersExceededException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionResponse nonNullException(MaxUsersExceededException n) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClassName(n.getClass().getSimpleName())
                .message(n.getMessage())
                .build();
    }

    @ExceptionHandler(NonNullException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionResponse nonNullException(NonNullException n) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClassName(n.getClass().getSimpleName())
                .message(n.getMessage())
                .build();
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse alreadyExistException(AlreadyExistException n) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .exceptionClassName(n.getClass().getSimpleName())
                .message(n.getMessage())
                .build();
    }

    @ExceptionHandler(BadCreadentialException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse badCreadentialException(BadCreadentialException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse badRequestException(BadRequest e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(NonUniqueResultException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse nonUniqueResultException(NonUniqueResultException n) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .exceptionClassName(n.getClass().getSimpleName())
                .message(n.getMessage())
                .build();
    }

}
