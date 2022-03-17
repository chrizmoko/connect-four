package connectfour.core;

public class RowOutOfBoundsException extends ConnectFourRuntimeException {
    public RowOutOfBoundsException() {
        super();
    }

    public RowOutOfBoundsException(String message) {
        super(message);
    }
}