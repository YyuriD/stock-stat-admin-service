package telran.java51.service;

import java.io.BufferedReader;
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
	private Admin currentUser = null;

	@Override
	public String login(String login, String password) {
		currentUser = adminRepository.findById(login).orElse(null);
		if (currentUser == null) {
			return "user not found";
		}
		if (!passwordEncoder.matches(password, currentUser.getPassword())) {
			return "wrong password";
		}
		return "Welcome to admin service";
	}

	@Override
	public String addUser(String login, String password, String accessLevel) {
		if (currentUser.getLogin() == null) {
			return "please login";
		}
		if (currentUser.getAccessLevel() < Admin.MAX_ACCESS_LEVEL) {
			return "access denied";
		}
		if (adminRepository.existsById(login)) {
			return "User already exist";
		}
		password = passwordEncoder.encode(password);
		Admin admin = new Admin(login, password, Integer.parseInt(accessLevel));
		if (adminRepository.save(admin) == null) {
			return "fail";
		}
		return "success!";
	}

	@Override
	public String updateUser(String login, String password, String accessLevel) {
		if (currentUser.getLogin() == null) {
			return "please login";
		}
		if (currentUser.getAccessLevel() < Admin.MAX_ACCESS_LEVEL) {
			return "access denied";
		}
		if (!adminRepository.existsById(login)) {
			return "User not found";
		}
		Admin admin = new Admin(login, password, Integer.parseInt(accessLevel));
		if (adminRepository.save(admin) == null) {
			return "fail";
		}
		return "success!";
	}

	@Override
	public String deleteUser(String login) {
		if (currentUser.getLogin() == null) {
			return "please login";
		}
		if (currentUser.getAccessLevel() < Admin.MAX_ACCESS_LEVEL) {
			return "access denied";
		}
		Admin user = adminRepository.findById(login).orElse(null);
		if (user == null) {
			return "user not found";
		}
		adminRepository.delete(user);
		return "success!";
	}

	@Override
	public String uploadCsvToDb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadCsvfromDb() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String logout() {
		currentUser = null;
		return "Goodbye";
	}

}
