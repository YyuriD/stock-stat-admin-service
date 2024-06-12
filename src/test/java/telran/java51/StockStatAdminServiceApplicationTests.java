package telran.java51;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.java51.user.dao.UserRepository;
import telran.java51.user.service.UserAccountServiceImpl;

//TODO is not working
@SpringBootTest
class StockStatAdminServiceApplicationTests {

	@Autowired
	UserAccountServiceImpl adminService;

	@Autowired
	UserRepository adminRepository;

//	@Test
	void contextLoads() throws Exception{
		System.out.println("test");
		assertThat(adminService).isNotNull();
		Assertions.assertEquals(adminRepository.count(),1);
	}

}
