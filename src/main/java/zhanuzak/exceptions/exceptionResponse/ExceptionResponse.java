package zhanuzak.exceptions.exceptionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String message;
}
