package telran.java51;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.java51.user.dao.UserRepository;
import telran.java51.user.model.UserAccount;
import telran.java51.user.service.UserAccountServiceImpl;

public class UserServiceTest {

	@InjectMocks
	UserAccountServiceImpl userAccountServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByName() {
		UserAccount user = new UserAccount("admin", "1234", "", "");
		when(userRepository.findById("admin")).thenReturn(Optional.of(user));

		UserAccount actualAdmin = userAccountServiceImpl.findByName("admin");

		assertThat(actualAdmin).isNotNull();
		Assertions.assertEquals("admin", actualAdmin.getLogin());
		verify(userRepository, times(1)).findById("admin");
	}
	
	@Test
	public void testAddAdmin() {
		UserAccount user = new UserAccount("admin", "1234", "", "");
		when(userRepository.save(user)).thenReturn(user);

		UserAccount actualAdmin = userAccountServiceImpl.addUser("admin", "1234"); 

		assertThat(actualAdmin).isNotNull();
		Assertions.assertEquals(user.getLogin(), actualAdmin.getLogin());
		Assertions.assertEquals(user.getPassword(), actualAdmin.getPassword());
		Assertions.assertEquals(user.getRoles(), actualAdmin.getRoles());
		verify(userRepository, times(1)).save(user);		
	}

}
