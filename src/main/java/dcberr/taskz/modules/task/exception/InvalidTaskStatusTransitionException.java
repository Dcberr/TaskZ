package dcberr.taskz.modules.task.exception;

public class InvalidTaskStatusTransitionException extends RuntimeException {

    public InvalidTaskStatusTransitionException(String message) {
        super(message);
    }
}
