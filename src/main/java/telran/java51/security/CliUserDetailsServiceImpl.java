package telran.java51.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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
		//TODO check access level and add to authorities
		Collection<String> authorities = Arrays.asList("10");// TODO Set 
					
		return  new User(login, user.getPassword(), AuthorityUtils.createAuthorityList(authorities));
	}

}
