package manager.exception;

public class OccupiedTimeIntervalException extends RuntimeException {
    public OccupiedTimeIntervalException(String msg) {
        super(msg);
    }
}
