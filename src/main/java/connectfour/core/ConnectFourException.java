package connectfour.core;

public class ConnectFourException extends Exception {
	public ConnectFourException() {
		super();
	}
	
	public ConnectFourException(String message) {
		super(message);
	}

	public ConnectFourException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectFourException(Throwable cause) {
		super(cause);
	}
}
