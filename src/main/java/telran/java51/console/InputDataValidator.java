package telran.java51.console;

import telran.java51.admin.model.Role;
import telran.java51.console.exceptions.IncorrectInputException;

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

	public static void check(String login, String password, String role) {
		check(login, password);
		for(Role r : Role.values()) {
			if (r.name().equals(role)) {
				return;
			}
		}
		throw new IncorrectInputException("Incorrect role");	
	}
}
