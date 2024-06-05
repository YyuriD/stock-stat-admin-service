package telran.java51.admin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.admin.exceptions.UserExistsException;
import telran.java51.admin.exceptions.UserNotFoundException;
import telran.java51.admin.model.AdminAccount;
import telran.java51.admin.model.AdminRole;
import telran.java51.user.dao.UserRepository;

@Service
@Configuration
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	final UserRepository userRepository;
	final PasswordEncoder passwordEncoder;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	@Autowired
	AuthenticationManager authenticationManager;

	@Transactional(readOnly = true)
	@Override
	public AdminAccount findByName(String login) {
		return  userRepository.findAdminByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Transactional
	@Override
	public AdminAccount addAdmin(String login, String password) {
		if (userRepository.existsById(login)) {
			throw new UserExistsException("User already exist");
		}
		password = passwordEncoder.encode(password);
		AdminAccount user = new AdminAccount(login, password);
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public AdminAccount updateAdmin(String login, String password, AdminRole role) {
		AdminAccount user = userRepository.findAdminByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found"));
		password = passwordEncoder.encode(password);
		user.setPassword(password);
		user.addRole(role);
		return userRepository.save(user);
	}
	
	@Transactional
	@Override
	public boolean changeRolesList(String login, String role, boolean isAddRole) {
		AdminAccount user = userRepository.findAdminByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found"));;
		boolean res;
		if(isAddRole) {
			res = user.addRole(AdminRole.valueOf(role.toUpperCase()));
		}
		else {
			res = user.removeRole(role);
		}
		if(res) {
			userRepository.save(user);
		}
		return res;
	}

	
	@Transactional
	@Override
	public AdminAccount deleteAdmin(String login) {
		AdminAccount user = (AdminAccount) userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userRepository.delete(user);
		return user;
	}

	@Transactional(readOnly = true)
	@Override
	public List<AdminAccount> getAllAdmins() {
		List<AdminAccount> users = new ArrayList<AdminAccount>();
		userRepository.findAllAdminsBy().forEach(a-> users.add(a));
		return users;
	}
	
}
