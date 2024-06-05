package telran.java51.admin.service;

import telran.java51.user.model.AdminAccount;
import telran.java51.user.model.AdminRole;

public interface AdminService {

	AdminAccount findByName(String login);

	AdminAccount addAdmin(String login, String password);

	AdminAccount updateAdmin(String login, String password, AdminRole role);

	AdminAccount deleteAdmin(String login);
	
	Iterable<AdminAccount> getAllAdmins(); 

}
