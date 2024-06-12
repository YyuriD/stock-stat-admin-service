package telran.java51.user.service;

import telran.java51.user.model.UserAccount;

public interface UserAccountService {

	UserAccount findByName(String login);

	UserAccount addUser(String login, String password);

	UserAccount updateUser(String login, String password, String role);
	
	boolean changeRolesList(String login, String role, boolean isAddRole);

	UserAccount removeUser(String login);
	
	Iterable<UserAccount> getAllUsers(); //TODO getAllAdmins

}
