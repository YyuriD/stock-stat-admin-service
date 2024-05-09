package telran.java51.console;

public class InputDataValidator {

	public static void check(String login) {
		if (!login.matches("^\\w{3,30}$") || login == null) {
			throw new IncorrectInputException("Incorrect login");
		}	
	}
	
	public static void check(String login, String password) {
		check(login);
		if (!password.matches("^[a-zA-Z0-9-+]{4,10}$") || password == null) {
			throw new IncorrectInputException("Incorrect password");
		}
	}

	public static void check(String login, String password, String accessLevel) {
		check(login, password);
		if (!accessLevel.matches("^[1-9]|10$") || accessLevel == null) {
			throw new IncorrectInputException("Incorrect access level");
		}
	}
}
