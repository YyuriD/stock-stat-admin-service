package telran.java51;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.service.AdminServiceImpl;

//TODO is not working
@SpringBootTest
class StockStatAdminServiceApplicationTests {

	@Autowired
	AdminServiceImpl adminService;

	@Autowired
	AdminRepository adminRepository;

	@Test
	void contextLoads() throws Exception{
		System.out.println("test");
		assertThat(adminService).isNotNull();
		Assertions.assertEquals(adminRepository.count(),1);
	}

}
