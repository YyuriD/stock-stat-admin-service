package telran.java51.user.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3805622168674190373L;

	
	public UserNotFoundException() {
		super();
	}


	public UserNotFoundException(String message) {
		super(message);
	}
	
}
