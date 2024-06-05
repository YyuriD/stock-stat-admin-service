package telran.java51.admin.service;

import telran.java51.admin.model.AdminAccount;
import telran.java51.admin.model.AdminRole;

public interface AdminService {

	AdminAccount findByName(String login);

	AdminAccount addAdmin(String login, String password);

	AdminAccount updateAdmin(String login, String password, AdminRole role);
	
	boolean changeRolesList(String login, String role, boolean isAddRole);

	AdminAccount deleteAdmin(String login);
	
	Iterable<AdminAccount> getAllAdmins(); 

}
