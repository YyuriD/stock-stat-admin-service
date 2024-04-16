package telran.java51.access.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.access.dao.UserRepository;
import telran.java51.access.model.User;

@Service
@AllArgsConstructor
@Configuration

public class AccessServiceImpl implements AccessService {

	final UserRepository userRepository;

	@Override
	public Boolean checkAccess(String email, String password) {
		User user = userRepository.findById(email).orElse(null);
		if (user == null) {
			System.out.println("User is not exist");
			return false;
		}
		if (!password.equals(user.getPassword())) {
			System.out.println("Wrong password");
			return false;
		}
		// TODO check roles
		return true;
	}

}
