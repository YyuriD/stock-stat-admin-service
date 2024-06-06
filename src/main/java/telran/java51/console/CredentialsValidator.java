package telran.java51.console;

import telran.java51.admin.model.AdminRole;
import telran.java51.console.exceptions.IncorrectInputException;

public class CredentialsValidator {

	public static void checkLogin(String login) {
		if (!login.matches("^\\w{3,30}$") || login == null) {
			throw new IncorrectInputException("Incorrect login");
		}	
	}
	
	public static void checkPassword(String password) {
		if (!password.matches("^[a-zA-Z0-9-+]{4,10}$") || password == null) {
			throw new IncorrectInputException("Incorrect password");
		}
	}

	public static void checkAdminRole(String role) { // TODO pass generic enum to check different roles(admin, user, client etc.)
		for(AdminRole r : AdminRole.values()) {
			if (r.name().equals(role)) {
				return;
			}
		}
		throw new IncorrectInputException("Incorrect role");	
	}
	
	public static void checkLoginPass(String login, String password ) {
		checkLogin(login);
		checkPassword(password);
	}
	
	public static void checkLoginRole(String login, String role ) {
		checkLogin(login);
		checkAdminRole(role);
	}
	
	public static void checkLoginPassRole(String login, String password, String role ) {
		checkLogin(login);
		checkPassword(password);
		checkAdminRole(role);
	}
	
}
