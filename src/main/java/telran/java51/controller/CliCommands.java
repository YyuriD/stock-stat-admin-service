package telran.java51.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import telran.java51.exceptions.UserExistsException;
import telran.java51.exceptions.UserNotFoundException;
import telran.java51.model.Admin;
import telran.java51.service.AdminServiceImpl;

@Configuration
@ShellComponent
public class CliCommands {

	private static final int MIN_LOGIN_LENGTH = 4;
	private static final int MAX_LOGIN_LENGTH = 30;
	private static final int MIN_PASS_LENGTH = 4;
	private static final int MAX_PASS_LENGTH = 20;
	private static final int MIN_ACCESS_LEVEL = 1;
	private static final int MAX_ACCESS_LEVEL = 10;
	private boolean isAuthorized = false;

	@Autowired
	AdminServiceImpl adminServiceImpl;
	@Autowired
	AuthenticationManager authenticationManager;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	@Bean
	@ShellMethod(value = "greeting") // TODO hide from help
	String greeting() {
		String message = "\nType 'help' for help.\n";
		System.out.println(message);
		return message;
	}

	@ShellMethod(key = "login", value = "login to admin service")
	String login() {
		String login = "";
		String password = "";
		String message = "";

		do {
			try {
				System.out.print("Enter login: ");
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
				isAuthorized = true;
				message = "Welcome to admin service!";
			} catch (AuthenticationException e) {
				System.out.println("Authentication failed");
			}
		} while (!isAuthorized);
		return message;
	}

	@ShellMethod(key = "add_user", value = "add user to admin service( --u username --p password --l access level)")
	public String addUser(
			@ShellOption(value = "u") @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH) @NotEmpty String login,
			@ShellOption(value = "p") @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH) @NotEmpty String password,
			@ShellOption(value = "l") @Size(min = MIN_ACCESS_LEVEL, max = MAX_ACCESS_LEVEL) @NotEmpty String level) {
		try {
			adminServiceImpl.addUser(login, password, level);
			return "Success!";
		} catch (UserExistsException e) {
			return "User already exist";
		}
	}

	@ShellMethod(key = "update_user", value = "update exist user(--u username --p password --l access level)")
	public String updateUser(
			@ShellOption(value = "u") @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH) @NotEmpty String login,
			@ShellOption(value = "p") @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH) @NotEmpty String password,
			@ShellOption(value = "l") @Size(min = MIN_ACCESS_LEVEL, max = MAX_ACCESS_LEVEL) @NotEmpty String level) {
		try {
			adminServiceImpl.updateUser(login, password, level);
			return "Success!";
		} catch (UserNotFoundException e) {
			return "User not found";
		}
	}

	@ShellMethod(key = "delete_user", value = "delete user")
	public String deleteUser(
			@ShellOption(value = "u") @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH) @NotEmpty String login) {
		try {
			adminServiceImpl.deleteUser(login);
			return "Success!";
		} catch (UserNotFoundException e) {
			return "User not found";
		}

	}

	@ShellMethod(key = "logout", value = "logout from admin service")
	public String logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "Goodbye!";
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
