package telran.java51;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import telran.java51.admin.model.AdminAccount;
import telran.java51.admin.model.AdminRole;
import telran.java51.user.dao.UserRepository;

@SpringBootApplication
public class StockStatAdminServiceApplication  {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		try {
			SpringApplication.run(StockStatAdminServiceApplication.class, args);
		}  		
		catch (Exception e) {			
		System.out.println("fail to start application");
		System.err.println(e.getCause().getLocalizedMessage());
		}
		
	}
	
	@PostConstruct
	public void init() {
		System.out.println("Admin repository initialization...");
		if (!userRepository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			AdminAccount admin = new AdminAccount("admin", password);
			admin.addRole(AdminRole.SUPER_ADMIN);
			userRepository.save(admin);
		}
	}

}
