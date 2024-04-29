package telran.java51.admin.service;

import telran.java51.admin.model.Admin;

public interface AdminService {

	Admin findByName(String login);

	Admin addUser(String login, String password, String accessLevel);

	Admin updateUser(String login, String password, String accessLevel);

	Admin deleteUser(String login);

	boolean uploadDataFromCsv(String filePath);

	void uploadDataFromService(String filePath);

	void downloadDataToCsv(String filePath);

}
