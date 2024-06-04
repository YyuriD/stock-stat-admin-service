package telran.java51.admin.service;

import telran.java51.admin.model.Admin;
import telran.java51.admin.model.AdminRole;

public interface AdminService {

	Admin findByName(String login);

	Admin addAdmin(String login, String password);

	Admin updateAdmin(String login, String password, AdminRole role);

	Admin deleteAdmin(String login);
	
	Iterable<Admin> getAllAdmins();

}
