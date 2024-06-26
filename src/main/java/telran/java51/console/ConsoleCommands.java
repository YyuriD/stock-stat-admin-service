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
import telran.java51.trading.model.TradingTableHeaders;
import telran.java51.trading.service.TradingServiceImpl;
import telran.java51.user.model.UsersTableHeaders;
import telran.java51.user.model.Role;
import telran.java51.user.service.UserAccountServiceImpl;
import telran.java51.utils.Utils;

@Configuration
@ShellComponent
public class ConsoleCommands {
	private boolean isAuthenticated = false;
	private boolean isCorrectInput = false;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserAccountServiceImpl userAccountService;
	@Autowired
	TradingServiceImpl tradingService;
	@Autowired
	AuthenticationManager authenticationManager;

	@PostConstruct
	private void greeting() {
		System.out.println(GREETING_MESSAGE);
//		System.out.println(loginExec());
	}

	@ShellMethod(key = "login", value = "login to admin service")
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
					CredentialsValidator.checkLoginPass(login, password);
					isCorrectInput = true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} 
			}
			isCorrectInput = false;
			try {
				Authentication result = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(login, password));
				SecurityContextHolder.getContext().setAuthentication(result);
				isAuthenticated = true;
				message = "Welcome to admin service!";
			} catch (AuthenticationException e) {
				System.out.println(e.getMessage());
				System.out.println("Authentication failed");
			}
		} while (!isAuthenticated);
		return message;
	}

	@ShellMethod(key = "add_user", value = "add user to admin service")
	public String addUser(
			@ShellOption(help = "user name") String u, 
			@ShellOption(help = "user password") String p) {
		try {
			CredentialsValidator.checkLoginPass(u, p);
			userAccountService.addUser(u, p);
			return "Success!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = "update_user", value = "update exist user")
	public String updateUser(
			@ShellOption(help = "user name") String u, 
			@ShellOption(help = "user password") String p,
			@ShellOption(help = "user role") String r) {
		try {
			CredentialsValidator.checkLoginPassRole(u, p, r);
			userAccountService.updateUser(u, p, r);
			return "Success!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@ShellMethod(key = "remove_role", value = "remove admins role")
	public String removeAdminRole(
			@ShellOption(help = "user name") String u, 
			@ShellOption(help = "user role") String r) {
		try {
			CredentialsValidator.checkLoginRole(u, r);
			if(userAccountService.changeRolesList(u, r, false)) {
				return "Success!";
			}
			else {
				return "fault";
			}		
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@ShellMethod(key = "add_role", value = "add role to admin")
	public String addAdminRole(
			@ShellOption(help = "user name") String u, 
			@ShellOption(help = "user role") String r) {
		try {
			CredentialsValidator.checkLoginRole(u, r);
			if(userAccountService.changeRolesList(u, r, true)) {
				return "Success!";
			}
			else {
				return "fault";
			}		
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = "delete_user", value = "delete user")
	public String deleteUser(@ShellOption(help = "user name") String u) {
		try {
			CredentialsValidator.checkLogin(u);
			userAccountService.removeUser(u);
			return "Success!";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}
	
	@ShellMethod(key = "find_user", value = "find user")
	public String findUser(@ShellOption(help = "user name") String u) {
		try {
			CredentialsValidator.checkLogin(u);		
			return userAccountService.findByName(u).toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}
	
	@ShellMethod(key = "print_users", value = "print all exists admins")
	public String printAllAdmins() {
		try {
			String[] users = userAccountService.getAllUsers().stream()
					.map(u-> u.toStringForTable())
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
		isAuthenticated = false;
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

	@ShellMethod(key = "print_from_csv", value = "print data from csv")
	public String printFromCsv(@ShellOption(help = "file name") String f) {
		String filePath = Utils.getFullPath(f);
		try {		
			long quantity = Utils.printCsv(filePath);
			return "Printed " + quantity + "items from " + filePath;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}

	@ShellMethod(key = "upload_from_csv", value = "upload data from csv to db")
	public String uploadFromCsv(
			@ShellOption(help = "file name") String f,
			@ShellOption(help = "source name") String s) {
		String filePath = Utils.getFullPath(f);
		try {
			long quantity = tradingService.addData(Utils.parseTradingSessions(filePath, s));
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

	@ShellMethod(key = "upload_from_server", value = "upload data from remote server to db")
	public String uploadFromServer(
			@ShellOption(help = "ticker name") String n, 
			@ShellOption(help = "source name") String s, 
			@ShellOption(help = "from date") String fd, 
			@ShellOption(help = "to date") String td) {
		try {
			long quantity = tradingService.addData(tradingService.getDataFromRemoteService(n, fd, td, s));
			if (quantity > 0) {
				return "Success! Added " + quantity + " items from date "+ fd + " to date" + td;
			} else {
				return "Nothing to add or ulready exist.";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}
	
	@ShellMethod(key = "print_from_server", value = "print trading sessions from remote server")
	public String printFromServer(
			@ShellOption(help = "ticker name") String n, 
			@ShellOption(help = "source name") String s,
			@ShellOption(help = "from date") String fd, 
			@ShellOption(help = "to date") String td) {
		try {
			String[] tradingSessions = tradingService
					.getDataFromRemoteService(n, fd, td, s).stream()
					.map(t-> t.toStringForTable())
					.toArray(String[]::new);
			String[] headers = Arrays.stream(TradingTableHeaders.values())
					.map(h->h.toString())
					.toArray(String[]::new);			
			Utils.printTable(tradingSessions, headers);		
			return "Printed " + tradingSessions.length + " trading sessions of " + n + " stock";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}
	
	@ShellMethod(key = "delete_tickers_by_name", value = "delete tickers by name from db")
	public String deleteByTicker(
			@ShellOption(help = "ticker name") String n) {
		try {
			tradingService.removeByTickerName(n);
				
			return "Success!";	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}
	
	@ShellMethod(key = "delete_all_data", value = "delete all data ")
	public String deleteAllData() {
		try {
			tradingService.removeAll();			
			return "Success!";	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fault";
		}
	}

	@ShellMethodAvailability({ "add_user", "update_user", "remove_role", "add_role", "delete_user", "print_users", "find_user"})
	public Availability adminAvailability() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Availability.unavailable("Must by authorized");
		}
		Set<String> set = AuthorityUtils.authorityListToSet(authentication.getAuthorities());// TODO not work without
																								// set?
		if (!set.contains("ROLE_" + Role.ADMIN.name())) {
			return Availability.unavailable("User role must by " + Role.ADMIN.name());
		}
		return Availability.available();
	}
	
	@ShellMethodAvailability({ "ls", "print_from_csv" //TODO availability only for data-admin
		, "upload_from_csv", "print_from_server"
		, "upload_from_server", "logout" 
		, "delete_tickers_by_name", "delete_all_data"})
	public Availability userAvailability() {
		return isAuthenticated ? Availability.available() : Availability.unavailable("Must by authorized");
	}
	
	@ShellMethodAvailability({ "login" })
	public Availability loginAvailability() {
		return isAuthenticated ? Availability.unavailable("You are already logged in") : Availability.available();
	}

}
