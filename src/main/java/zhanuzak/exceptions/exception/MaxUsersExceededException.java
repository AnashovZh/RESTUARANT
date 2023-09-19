package zhanuzak.exceptions.exception;

public class MaxUsersExceededException extends RuntimeException{
    public MaxUsersExceededException(String message) {
        super(message);
    }
}
