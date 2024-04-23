package telran.java51.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.model.Admin;
import telran.java51.service.AdminServiceImpl;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Lazy
	@Autowired
	AdminServiceImpl adminService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Admin user = adminService.findByName(login);
		if (user == null) {
			throw new UsernameNotFoundException("User not found.");
		}
//		List<String> authorities = Arrays.asList(user.getAccessLevel().toString()); //variant 1
//		authorities.toString();
//		return new User(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList(authorities));

		User.UserBuilder builder = User.withUsername(login); //variant 2
		builder.password(user.getPassword());
		builder.roles(user.getAccessLevel().toString());		
		return builder.build();
	}
}
