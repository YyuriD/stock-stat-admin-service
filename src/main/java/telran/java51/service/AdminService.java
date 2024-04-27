package telran.java51.service;

import telran.java51.model.Admin;

public interface AdminService {
	String login(String login, String password);

	Admin findByName(String login);
	
	Admin addUser(String login, String password,String accessLevel);

	Admin updateUser(String login, String password, String accessLevel);

	Admin deleteUser(String login);

	void uploadDataFromCsv(String filePath);
	
	void uploadDataFromService(String filePath);
	
	void downloadDataToCsv(String filePath);
	
}
