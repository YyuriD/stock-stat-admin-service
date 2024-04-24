package telran.java51.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import telran.java51.service.AdminServiceImpl;

@Configuration
@ShellComponent
public class CliCommandController {

	@Autowired
	AdminServiceImpl adminServiceImpl;
	@Autowired
	AuthenticationManager authenticationManager;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Bean
    @ShellMethod(value = "greeting") //TODO hide from help
    String greeting() {
    	String message = "\nType 'help' for help.\n";
    	System.out.println(message);
		return message;
	}
	
	@ShellMethod(key = "login",value = "login to admin service")
	String  login() {
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
				message = "Success!";
			} catch (AuthenticationException e) {
				//
				System.out.println("Authentication failed");
			}
		} while (!"Success!".equals(message));
		return message;
	}

	@ShellMethod(key = "add_user", value = "add user to admin service( --u username --p password --l access level)")
	public String addUser(@ShellOption(value = "u") String login, @ShellOption(value = "p") String password,
			@ShellOption(value = "l") String level) {
		// TODO try catch
		adminServiceImpl.addUser(login, password, level);
		return "Success!";
	}

	@ShellMethod(key = "update_user", value = "update exist user(--u username --p password --l access level)")
	public String updateUser(@ShellOption(value = "u") String login, @ShellOption(value = "p") String password,
			@ShellOption(value = "l") String level) {
		// TODO try catch
		adminServiceImpl.updateUser(login, password, level);
		return "Success!";
	}

	@ShellMethod(key = "delete_user", value = "delete user")
	public String deleteUser(@ShellOption(value = "u") String login) {
		// TODO try catch
		adminServiceImpl.deleteUser(login);
		return "Success!";
	}

	@ShellMethod(key = "logout", value = "logout from admin service")
	public String logout() {
		SecurityContextHolder.getContext().setAuthentication(null);	
		return "Goodbye!";
	}
}
