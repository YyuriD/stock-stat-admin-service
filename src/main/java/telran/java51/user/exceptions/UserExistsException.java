package telran.java51.user.exceptions;

public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = 2170528501957178466L;
	
	public UserExistsException() {
		super();
	}

	public UserExistsException(String message) {
		super(message);
	}

}
