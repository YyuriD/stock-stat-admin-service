package telran.java51.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.dao.AdminRepository;
import telran.java51.exceptions.UserExistsException;
import telran.java51.exceptions.UserNotFoundException;
import telran.java51.model.Admin;

@Service
@AllArgsConstructor
@Configuration
public class AdminServiceImpl implements AdminService {
	final AdminRepository adminRepository;
	final PasswordEncoder passwordEncoder;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public String login(String login, String password) {
//		try {
//			Authentication result = authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(login, password));
//			SecurityContextHolder.getContext().setAuthentication(result);
//			return "Welcome to admin service";
//		} catch (AuthenticationException e) {
//			System.out.println(e.getMessage());
//			return "Authentication failed";
//		}
		return null;
	}

	@Override
	public Admin findByName(String login) {
		return adminRepository.findById(login).orElseThrow(UserNotFoundException::new);
	}

	@Override
	public Admin addUser(String login, String password, String accessLevel) {
		if (adminRepository.existsById(login)) {
			throw new UserExistsException();
		}
		// TODO CheckCredentials
		// TODO WrongCredentialsException
		password = passwordEncoder.encode(password);
		Admin user = new Admin(login, password, Integer.parseInt(accessLevel));
		return adminRepository.save(user);
	}

	@Override
	public Admin updateUser(String login, String password, String accessLevel) {
		Admin user = adminRepository.findById(login).orElseThrow(UserNotFoundException::new);
		// TODO CheckCredentials
		// TODO WrongCredentialsException
		password = passwordEncoder.encode(password);
		user.setPassword(password);
		user.setAccessLevel(Integer.parseInt(accessLevel));
		return adminRepository.save(user);
	}

	@Override
	public Admin deleteUser(String login) {
		Admin user = adminRepository.findById(login).orElseThrow(UserNotFoundException::new);
		adminRepository.delete(user);
		return user;
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

}
