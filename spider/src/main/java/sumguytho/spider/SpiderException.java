package sumguytho.spider;

public class SpiderException extends Exception {
	public SpiderException() {}

	public SpiderException(String message) {
		super(message);
	}

	public SpiderException(Throwable cause) {
		super(cause);
	}

	public SpiderException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpiderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
