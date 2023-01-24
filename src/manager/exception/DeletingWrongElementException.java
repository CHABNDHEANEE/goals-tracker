package manager.exception;

public class DeletingWrongElementException extends RuntimeException {
    public DeletingWrongElementException(String msg) {
        super(msg);
    }
}
