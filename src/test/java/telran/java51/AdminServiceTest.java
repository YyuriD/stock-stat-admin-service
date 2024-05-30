package telran.java51;

import static org.assertj.core.api.Assertions.assertThat;
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
import telran.java51.admin.exceptions.UserExistsException;
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

		Admin foundAdmin = adminService.findByName("admin");

		assertThat(foundAdmin).isNotNull();
		Assertions.assertEquals("admin", foundAdmin.getLogin());
	}
	
	@Test
	public void testAddAdmin() {
		Admin admin = new Admin("admin", "1234", 10);
		when(adminRepository.existsById("admin")).thenReturn(false);
		when(adminRepository.save(admin)).thenReturn(admin);

		Admin addedAdmin = adminService.addUser("admin", "1234", "10"); 

		assertThat(addedAdmin).isNotNull();
		Assertions.assertEquals(admin.getLogin(), addedAdmin.getLogin());
		Assertions.assertEquals(admin.getPassword(), addedAdmin.getPassword());
		Assertions.assertEquals(admin.getAccessLevel(), addedAdmin.getAccessLevel());
		
	}

}
