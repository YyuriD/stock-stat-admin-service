package telran.java51.user.service;

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
import telran.java51.user.dao.UserRepository;
import telran.java51.user.exceptions.UserExistsException;
import telran.java51.user.exceptions.UserNotFoundException;
import telran.java51.user.model.UserAccount;

@Service
@Configuration
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
	final UserRepository userRepository;
	final PasswordEncoder passwordEncoder;
	final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	@Autowired
	AuthenticationManager authenticationManager;

	@Transactional(readOnly = true)
	@Override
	public UserAccount findByName(String login) {
		return  userRepository.findById(login).orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Transactional
	@Override
	public UserAccount addUser(String login, String password) {
		if (userRepository.existsById(login)) {
			throw new UserExistsException("User already exist");
		}
		password = passwordEncoder.encode(password);
		UserAccount user = new UserAccount(login, password,"","");
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public UserAccount updateUser(String login, String password, String role) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException("User not found"));
		password = passwordEncoder.encode(password);
		user.setPassword(password);
		user.addRole(role);
		return userRepository.save(user);
	}
	
	@Transactional
	@Override
	public boolean changeRolesList(String login, String role, boolean isAddRole) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException("User not found"));;
		boolean res;
		if(isAddRole) {
			res = user.addRole(role);
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
	public UserAccount removeUser(String login) {
		UserAccount user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userRepository.delete(user);
		return user;
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserAccount> getAllUsers() {
		List<UserAccount> users = new ArrayList<UserAccount>();
		userRepository.findAll().forEach(u -> users.add(u));
		return users;
	}
	
}
