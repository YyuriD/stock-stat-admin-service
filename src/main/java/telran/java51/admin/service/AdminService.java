package telran.java51.admin.service;

import telran.java51.admin.model.Admin;

public interface AdminService {

	Admin findByName(String login);

	Admin addAdmin(String login, String password, String accessLevel);//TODO change to addAdmin

	Admin updateAdmin(String login, String password, String accessLevel);

	Admin deleteAdmin(String login);
	
	Iterable<Admin> getAllAdmins();

}
