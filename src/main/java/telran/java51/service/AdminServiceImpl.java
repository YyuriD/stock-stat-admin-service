package telran.java51.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.dao.AdminRepository;
import telran.java51.model.Admin;

@Service
@AllArgsConstructor
@Configuration
public class AdminServiceImpl implements AdminService {
	final AdminRepository adminRepository;
	final PasswordEncoder passwordEncoder;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public Admin checkAccess() throws IOException {
		Admin user = null;
		System.out.println("Enter login");
		String login = br.readLine();
		System.out.println("Enter password");
		String password = br.readLine();
		user = adminRepository.findById(login).orElse(null);
		if (user == null) {
			System.out.println("User does not exist");
			return null;
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			System.out.println("Wrong password");
			return null;
		}
		return user;
	}

	@Override
	public Boolean registerAdmin() throws IOException {
		System.out.println("Enter login");
		String login = br.readLine();
		if (adminRepository.existsById(login)) {
			System.out.println("User already exist");
			return false;
		}
		System.out.println("Enter password");
		String password = passwordEncoder.encode(br.readLine());
		System.out.println("Enter access Level");
		Integer accessLevel = Integer.parseInt(br.readLine());
		Admin admin = new Admin(login, password, accessLevel);
		if (adminRepository.save(admin) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateAdmin() throws IOException {
		System.out.println("Enter login");
		String login = br.readLine();
		if (!adminRepository.existsById(login)) {
			System.out.println("User does not exist");
			return false;
		}
		System.out.println("Enter password");
		String password = br.readLine();
		System.out.println("Enter access Level");
		Integer accessLevel = Integer.parseInt(br.readLine());
		Admin admin = new Admin(login, password, accessLevel);
		if (adminRepository.save(admin) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean deleteAdmin() throws IOException {
		System.out.println("Enter login");
		String login = br.readLine();
		Admin admin = adminRepository.findById(login).orElse(null);
		if (admin == null) {
			System.out.println("User does not exist");
			return false;
		}
		adminRepository.delete(admin);
		return true;
	}

	@Override
	public Boolean uploadCsv() {
		// TODO Auto-generated method stub
		return null;
	}

}
