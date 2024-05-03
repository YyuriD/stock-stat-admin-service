package telran.java51;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.model.Admin;

@SpringBootApplication
public class StockStatAdminServiceApplication  {

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		try {
			SpringApplication.run(StockStatAdminServiceApplication.class, args);
		} catch (Exception e) {
			System.out.println("fail to start application");
		}
		
	}
	
	@PostConstruct
	public void init() {
		final Integer MAX_ACCESS_LEVEL = 10;
		System.out.println("Admin repository initialization...");
		if (!adminRepository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			Admin admin = new Admin("admin", password, MAX_ACCESS_LEVEL);
			adminRepository.save(admin);
		}
	}

}
