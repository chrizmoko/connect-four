package connectfour.core;

public class ConnectFourRuntimeException extends RuntimeException {
    public ConnectFourRuntimeException() {
		super();
	}
	
	public ConnectFourRuntimeException(String message) {
		super(message);
	}

	public ConnectFourRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectFourRuntimeException(Throwable cause) {
		super(cause);
	}
}
