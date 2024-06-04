package telran.java51;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import telran.java51.admin.service.AdminServiceImpl;
import telran.java51.console.ConsoleCommands;

public class ConsoleCommandsTest {

	@InjectMocks 
	ConsoleCommands consoleCommands;
	
	@Mock
	AdminServiceImpl adminService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testAddUser() {
		String result = consoleCommands.addUser("admin", "admin");	
		Assertions.assertEquals("Success!",result);
	}
}
