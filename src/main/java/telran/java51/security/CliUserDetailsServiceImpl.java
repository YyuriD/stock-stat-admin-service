package telran.java51.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.dao.AdminRepository;
import telran.java51.model.Admin;

@Service
@RequiredArgsConstructor
public class CliUserDetailsServiceImpl implements UserDetailsService {

	final AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Admin user = adminRepository.findById(login)
				.orElseThrow(()-> new UsernameNotFoundException(login));
		return  new Admin(login, user.getPassword(), user.getAccessLevel());
	}

}
