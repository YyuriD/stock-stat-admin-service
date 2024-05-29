package telran.java51;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.java51.admin.dao.AdminRepository;
import telran.java51.admin.service.AdminServiceImpl;


@SpringBootTest
class StockStatAdminServiceApplicationTests {

	@Autowired
	AdminServiceImpl adminServiceImpl;

	@Autowired
	AdminRepository adminRepository;

	@Test
	void contextLoads() {
		System.out.println("test");
		Assertions.assertEquals(adminRepository.count(),1);
	}

}
