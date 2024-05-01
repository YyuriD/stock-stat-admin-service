package telran.java51.console;

import static telran.java51.console.Constants.ADD_USER_COMMAND;
import static telran.java51.console.Constants.DELETE_USER_COMMAND;
import static telran.java51.console.Constants.GREETING_MESSAGE;
import static telran.java51.console.Constants.IMPORT_CSV_COMMAND;
import static telran.java51.console.Constants.LOGIN_COMMAND;
import static telran.java51.console.Constants.LOGOUT_COMMAND;
import static telran.java51.console.Constants.LS_COMMAND;
import static telran.java51.console.Constants.MAX_ACCESS_LEVEL;
import static telran.java51.console.Constants.MAX_LOGIN_LENGTH;
import static telran.java51.console.Constants.MAX_PASS_LENGTH;
import static telran.java51.console.Constants.MIN_ACCESS_LEVEL;
import static telran.java51.console.Constants.MIN_LOGIN_LENGTH;
import static telran.java51.console.Constants.MIN_PASS_LENGTH;
import static telran.java51.console.Constants.PRINT_CSV_COMMAND;
import static telran.java51.console.Constants.UPDATE_USER_COMMAND;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.exceptions.UserExistsException;
import telran.java51.admin.exceptions.UserNotFoundException;
import telran.java51.admin.model.Admin;
import telran.java51.admin.service.AdminServiceImpl;
import telran.java51.trading.service.TradingServiceImpl;
import telran.java51.utils.ConsoleUtils;
import telran.java51.utils.CsvUtils;


@Configuration
@ShellComponent
public class ConsoleCommands {
	boolean isAuthenticated = false;

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AdminServiceImpl adminService;
	
	@Autowired
	TradingServiceImpl tradingService;
	@Autowired
	AuthenticationManager authenticationManager;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	@PostConstruct
	private void greeting() {
		System.out.println(GREETING_MESSAGE);
		System.out.println(loginExec());
	}

	@ShellMethod(key = LOGIN_COMMAND, value = "login to admin service", prefix = "-")
	String login() {
		return loginExec();
	}

	private String loginExec() {
		String login = "";
		String password = "";
		String message = "";

		do {
			try {
				System.out.print("Enter login: "); // TODO validation input data
				login = br.readLine();
				System.out.print("Enter password: ");
				if (System.console() != null) {
					password = new String(System.console().readPassword());
				} else {
					password = br.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Authentication result = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(login, password));
				SecurityContextHolder.getContext().setAuthentication(result);
				isAuthenticated = true;
				message = "Welcome to admin service!";
			} catch (AuthenticationException e) {
				System.out.println("Authentication failed");
			}
		} while (!isAuthenticated);
		return message;
	}

	@ShellMethod(key = ADD_USER_COMMAND, value = "add user to admin service( -u username -p password -l access level)", prefix = "-")
	public String addUser(
			@ShellOption(value = "u") @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH) @NotEmpty String login,
			@ShellOption(value = "p") @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH) @NotEmpty String password,
			@ShellOption(value = "l") @Size(min = MIN_ACCESS_LEVEL, max = MAX_ACCESS_LEVEL) @NotEmpty String level) {
		try {
			adminService.addUser(login, password, level);
			return "Success!";
		} catch (UserExistsException e) {
			return "User already exist";
		}
	}

	@ShellMethod(key = UPDATE_USER_COMMAND, value = "update exist user(-u username -p password -l access level)", prefix = "-")
	public String updateUser(
			@ShellOption(value = "u") @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH) @NotEmpty String login,
			@ShellOption(value = "p") @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH) @NotEmpty String password,
			@ShellOption(value = "l") @Size(min = MIN_ACCESS_LEVEL, max = MAX_ACCESS_LEVEL) @NotEmpty String level) {
		try {
			adminService.updateUser(login, password, level);
			return "Success!";
		} catch (UserNotFoundException e) {
			return "User not found";
		}
	}

	@ShellMethod(key = DELETE_USER_COMMAND, value = "delete user", prefix = "-")
	public String deleteUser(
			@ShellOption(value = "u") @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH) @NotEmpty String login) {
		try {
			adminService.deleteUser(login);
			return "Success!";
		} catch (UserNotFoundException e) {
			return "User not found";
		}
	}

	@ShellMethod(key = LOGOUT_COMMAND, value = "logout from admin service")
	public String logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "Goodbye!\n";
	}

	@ShellMethod(key = LS_COMMAND, value = "display all files in directory")
	public String showFilesInDirectory() {
		String directory = ConsoleUtils.getCurrentDirectory();
		try {
			long quantity = ConsoleUtils.getFilesList(directory).stream().filter(f -> f.contains(".csv")).count();
			if (quantity > 0) {
				ConsoleUtils.getFilesList(directory).stream().filter(f -> f.contains("csv"))
						.forEach(f -> System.out.println(f));
				return "found " + quantity + " files";
			}
			return "CSV files not found";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail to show files";
		}
	}


	@ShellMethod(key = PRINT_CSV_COMMAND, value = "print data from csv", prefix = "-")
	public String printCsv(@ShellOption(value = "f") String fileName) {
		String filePath = ConsoleUtils.getCurrentDirectory() + "\\" + fileName;// TODO "\" in other OS ???
		try {
			CsvUtils.printCsv(filePath);
			return "Printed from " + filePath;
		} catch (IOException e) {
			return "fault";
		}
	}

	@ShellMethod(key = IMPORT_CSV_COMMAND, value = "upload data from csv to db", prefix = "-")
	public String importCsv(@ShellOption(value = "f") String fileName) {
		String filePath = ConsoleUtils.getCurrentDirectory() + "\\" + fileName;// TODO "\" in other OS ???
		try {			
			tradingService.addData(CsvUtils.parseCsvToTradingSessions(filePath));
			return "Success!";
		} catch (IOException e) {
			return "fault";
		}
	}
	
	@ShellMethodAvailability({ "add_user", "update_user", "delete_user" })
	public Availability accessCheck() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Availability.unavailable("Must by authorized");
		}
		Set<String> set = AuthorityUtils.authorityListToSet(authentication.getAuthorities());// TODO not work without
																								// set?
		if (!set.contains("ROLE_" + Admin.MAX_ACCESS_LEVEL)) {
			return Availability.unavailable("User access level must by " + MAX_ACCESS_LEVEL);
		}
		return Availability.available();
	}

}
