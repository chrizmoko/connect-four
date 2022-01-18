package connectfour.core;

public class ColumnOutOfBoundsException extends ConnectFourException {
    public ColumnOutOfBoundsException() {
        super();
    }

    public ColumnOutOfBoundsException(String message) {
        super(message);
    }
}
