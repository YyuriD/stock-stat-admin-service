package telran.java51.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.model.Admin;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Admin user = adminRepository.findById(login)
				.orElseThrow(() -> new UsernameNotFoundException("User not found."));
	
		List<String> authorities = user.getRoles().stream()
				.map(r -> "ROLE_" + r.name()).collect(Collectors.toList());//variant 1
		return new User(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList(authorities));

//		User.UserBuilder builder = User.withUsername(login); //variant 2
//		builder.password(user.getPassword());
//		builder.roles(user.getRoles());		
//		return builder.build();
	}
}
