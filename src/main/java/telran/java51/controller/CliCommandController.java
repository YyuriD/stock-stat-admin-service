package telran.java51.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import telran.java51.service.AdminServiceImpl;

@ShellComponent
public class CliCommandController {
	
	@Autowired
	AdminServiceImpl adminServiceImpl;
	
	@ShellMethod(key = "login", value = "login to admin service")
	public String login(@ShellOption(value = "u") String login,
			@ShellOption(value = "p") String password) {
		//TODO try catch 	
		return adminServiceImpl.login(login, password);
	}

	@ShellMethod(key = "add_user", value = "add user to admin service")
	public String addUser(@ShellOption(value = "u")String login,
			@ShellOption(value = "p") String password, @ShellOption(value = "l") String level) {
		//TODO try catch 
		adminServiceImpl.addUser(login, password, level);
		 return "Success!";
	}
	
	@ShellMethod(key = "update_user", value = "update exist user")
	public String updateUser(@ShellOption(value = "u")String login,
			@ShellOption(value = "p") String password, @ShellOption(value = "l") String level) {
		//TODO try catch 
		adminServiceImpl.updateUser(login, password, level);
		return "Success!";
	}
	
	@ShellMethod(key = "delete_user", value = "delete user")
	public String deleteUser(@ShellOption(value = "u")String login) {
		//TODO try catch
		adminServiceImpl.deleteUser(login);
		return "Success!";
	}
	
	@ShellMethod(key = "logout", value = "logout from admin service")
	public String logout() {
		adminServiceImpl.logout();
		return "Goodbye";
	}
}
