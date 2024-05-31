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

import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.model.Admin;
import telran.java51.admin.service.AdminServiceImpl;

public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminService;
	@Mock
	AdminRepository adminRepository;
	@Mock
	PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByName() {
		Admin admin = new Admin("admin", "1234", 10);
		when(adminRepository.findById("admin")).thenReturn(Optional.of(admin));

		Admin actualAdmin = adminService.findByName("admin");

		assertThat(actualAdmin).isNotNull();
		Assertions.assertEquals("admin", actualAdmin.getLogin());
		verify(adminRepository, times(1)).findById("admin");
	}
	
	@Test
	public void testAddAdmin() {
		Admin admin = new Admin("admin", "1234", 10);
		when(adminRepository.save(admin)).thenReturn(admin);

		Admin actualAdmin = adminService.addAdmin("admin", "1234", "10"); 

		assertThat(actualAdmin).isNotNull();
		Assertions.assertEquals(admin.getLogin(), actualAdmin.getLogin());
		Assertions.assertEquals(admin.getPassword(), actualAdmin.getPassword());
		Assertions.assertEquals(admin.getAccessLevel(), actualAdmin.getAccessLevel());
		verify(adminRepository, times(1)).save(admin);		
	}

}
