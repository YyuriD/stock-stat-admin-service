package telran.java51;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.model.Admin;

@SpringBootApplication
public class StockStatAdminServiceApplication implements CommandLineRunner {

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(StockStatAdminServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final Integer MAX_ACCESS_LEVEL = 10;

		System.out.println("Init...");
		if (!adminRepository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			Admin admin = new Admin("admin", password, MAX_ACCESS_LEVEL);
			adminRepository.save(admin);
		}
	}

}
