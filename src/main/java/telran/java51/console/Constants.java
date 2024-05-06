package telran.java51.console;

final class Constants {
	static final String GREETING_MESSAGE = "\nType 'help' for help.\n";
	
	//console commands
	static final String LOGIN_COMMAND = "login";
	static final String ADD_USER_COMMAND = "add_user";
	static final String UPDATE_USER_COMMAND = "update_user";
	static final String DELETE_USER_COMMAND = "delete_user";
	static final String LOGOUT_COMMAND = "logout";
	static final String LS_COMMAND = "ls";
	static final String PRINT_CSV_COMMAND = "print_csv";
	static final String IMPORT_CSV_COMMAND = "import_csv";
	static final String UPLOAD_REMOTE_COMMAND = "upload_remote";

	static final int MIN_LOGIN_LENGTH = 4;
	static final int MAX_LOGIN_LENGTH = 30;
	static final int MIN_PASS_LENGTH = 4;
	static final int MAX_PASS_LENGTH = 20;
	static final int MIN_ACCESS_LEVEL = 1;
	static final int MAX_ACCESS_LEVEL = 10;

	//console prompt
	static final String NOT_AUTHENTICATED = "Not authenticated";
	static final String AUTHORIZED = "Admin-service";
	static final String POSTFIX = ":>";
	static final String ADMIN_SIGN = "#";
	static final String USER_SIGN = "@";
}
