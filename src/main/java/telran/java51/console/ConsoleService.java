package telran.java51.console;

import static telran.java51.console.Constants.GREETING_MESSAGE;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.model.Admin;
import telran.java51.admin.service.AdminServiceImpl;
import telran.java51.trading.service.TradingServiceImpl;
import telran.java51.utils.UsersTableHeaders;
import telran.java51.utils.Utils;

@Configuration
@ShellComponent
public class ConsoleService {
	private boolean isAuthenticated = false;
	private boolean isCorrectInput = false;
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

	@PostConstruct
	private void greeting() {
		System.out.println(GREETING_MESSAGE);
//		System.out.println(loginExec());
	}

	@ShellMethod(key = "login", value = "login to admin service", prefix = "-")
	String login() {
		return loginExec();
	}

	private String loginExec() {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String login = "";
		String password = "";
		String message = "";
		
		do {
			while (!isCorrectInput) {
				try {
					System.out.print("Enter login: ");
					login = br.readLine();
					System.out.print("Enter password: ");
					if (System.console() != null) {
						password = new String(System.console().readPassword());
					} else {
						password = br.readLine();
					}
					InputDataValidator.check(login, password);
					isCorrectInput = true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} 
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

	@ShellMethod(key = "add_user", value = "add user to admin service( -u username -p password -l access level)", prefix = "-")
	public String addUser(@ShellOption(value = "u") String login, @ShellOption(value = "p") String password,
			@ShellOption(value = "l") String level) {
		try {
			InputDataValidator.check(login, password, level);
			adminService.addUser(login, password, level);
			return "Success!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = "update_user", value = "update exist user(-u username -p password -l access level)", prefix = "-")
	public String updateUser(@ShellOption(value = "u") String login, @ShellOption(value = "p") String password,
			@ShellOption(value = "l") String level) {
		try {
			InputDataValidator.check(login, password, level);
			adminService.updateUser(login, password, level);
			return "Success!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = "delete_user", value = "delete user", prefix = "-")
	public String deleteUser(@ShellOption(value = "u") String login) {
		try {
			InputDataValidator.check(login);
			adminService.deleteUser(login);
			return "Success!";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}
	
	@ShellMethod(key = "print_users", value = "print all exists users", prefix = "-")
	public String printAllUsers() {
		try {
			String[] users = adminService.getAllUsers().stream()
					.map(u-> u.toString())
					.toArray(String[]::new);
			String[] headers = Arrays.stream(UsersTableHeaders.values())
					.map(h->h.toString())
					.toArray(String[]::new);			
			Utils.printTable(users, headers);		
			return "Printed " + users.length + " users ";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}

	@ShellMethod(key = "logout", value = "logout from admin service")
	public String logout() {
		isCorrectInput = false;
		SecurityContextHolder.getContext().setAuthentication(null);
		return "Goodbye!\n";
	}

	@ShellMethod(key = "ls", value = "display all csv files in directory")
	public String showFilesInDirectory() {
		String directory = Utils.getCurrentDirectory();
		try {
			long quantity = Utils.getFilesList(directory).stream().filter(f -> f.contains(".csv")).count();
			if (quantity > 0) {
				Utils.getFilesList(directory).stream().filter(f -> f.contains("csv"))
						.forEach(f -> System.out.println(f));
				return "found " + quantity + " files";
			}
			return "CSV files not found";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fail to show files";
		}
	}

	@ShellMethod(key = "print_csv", value = "print data from csv (-f filename)", prefix = "-")
	public String printCsv(@ShellOption(value = "f") String fileName) {
		String filePath = Utils.getFullPath(fileName);
		try {		
			long quantity = Utils.printCsv(filePath);
			return "Printed " + quantity + "items from " + filePath;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}

	@ShellMethod(key = "import_csv", value = "upload data from csv to db(-f file name)", prefix = "-")
	public String importCsv(@ShellOption(value = "f") String fileName) {
		String filePath = Utils.getFullPath(fileName);
		try {
			long quantity = tradingService.addData(Utils.parseTradingSessions(filePath));
			if (quantity > 0) {
				return "Success! Added " + quantity + " items.";
			} else {
				return "Nothing to add or ulready exist.";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}

	@ShellMethod(key = "upload_remote", value = "upload data from remote service to db(-n ticker name)", prefix = "-")
	public String importFromRemote(@ShellOption(value = "n") String tickereName) {
		try {
			long quantity = tradingService.addData(tradingService.getDataFromRemoteService(tickereName));
			if (quantity > 0) {
				return "Success! Added " + quantity + " items.";
			} else {
				return "Nothing to add or ulready exist.";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}

	@ShellMethodAvailability({ "add_user", "update_user", "delete_user", "print_users" })
	public Availability accessCheck() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Availability.unavailable("Must by authorized");
		}
		Set<String> set = AuthorityUtils.authorityListToSet(authentication.getAuthorities());// TODO not work without
																								// set?
		if (!set.contains("ROLE_" + Admin.MAX_ACCESS_LEVEL)) {
			return Availability.unavailable("User access level must by " + Admin.MAX_ACCESS_LEVEL);
		}
		return Availability.available();
	}

}
