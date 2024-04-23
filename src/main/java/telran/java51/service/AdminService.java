package telran.java51.service;

import telran.java51.model.Admin;

public interface AdminService {
	String login(String login, String password);

	Admin findByName(String login);
	
	Admin addUser(String login, String password,String accessLevel);

	Admin updateUser(String login, String password, String accessLevel);

	Admin deleteUser(String login);

	String uploadCsvToDb();
	
	String downloadCsvfromDb();
	
	public void logout();

}
