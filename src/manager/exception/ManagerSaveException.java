package manager.exception;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String msg) {
        super(msg);
    }
}
