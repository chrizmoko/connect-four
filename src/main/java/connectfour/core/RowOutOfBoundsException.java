package connectfour.core;

public class RowOutOfBoundsException extends ConnectFourException {
    public RowOutOfBoundsException() {
        super();
    }

    public RowOutOfBoundsException(String message) {
        super(message);
    }
}