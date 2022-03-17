package connectfour.core;

public class ColumnOutOfBoundsException extends ConnectFourRuntimeException {
    public ColumnOutOfBoundsException() {
        super();
    }

    public ColumnOutOfBoundsException(String message) {
        super(message);
    }
}
