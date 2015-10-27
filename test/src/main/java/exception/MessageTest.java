package exception;

public class MessageTest {

	public static class TestException extends RuntimeException {
		@Override
		public String getMessage() {
			return super.getMessage() + " (message id)";
		}
	}

	public static void main(String[] args) {
		throw new TestException();
	}

}