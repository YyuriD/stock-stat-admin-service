package telran.java51;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.java51.dao.AdminRepository;
import telran.java51.model.Admin;
import telran.java51.service.AdminServiceImpl;

@SpringBootApplication
public class StockStatDataUploaderApplication implements CommandLineRunner {

	@Autowired
	AdminServiceImpl adminServiceImpl;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(StockStatDataUploaderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final Integer MAX_ACCESS_LEVEL = 10;
		
		if (!adminRepository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			Admin admin = new Admin("admin", password, MAX_ACCESS_LEVEL);
			adminRepository.save(admin);
		}
		
		Menu menuStep = Menu.START;
		Admin currentUser = null;
		List<String> answerVariants = new ArrayList<String>(Arrays.asList("Y","N","y","n"));
		List<String> actionItems = new ArrayList<String>(Arrays.asList(
				"1.Add admin",
				"2.Update admin",
				"3.Delete admin",
				"4.Upload CSV to database",
				"5.Exit"
				));

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String str = "";
			while (true) {
				switch (menuStep) {
				case START:
					System.out.println("<<< Welcome to admin service >>>");
					
				case CHECK_ACCESS:
					currentUser = adminServiceImpl.checkAccess();
					if (currentUser == null) {
						System.out.println("Try again? Y/N");
						str = br.readLine();
						while (!answerVariants.contains(str)) {
							System.out.println("please enter Y/N");
							str = br.readLine();
						}
						if ("Y".equalsIgnoreCase(str)) {
							menuStep = Menu.CHECK_ACCESS;
						} else {
							menuStep = Menu.EXIT;
						}
					}else {
						menuStep = Menu.CHOICE_ACTION;
					}
					break;
					
				case CHOICE_ACTION:
					System.out.println("\n Choose action");
					actionItems.forEach(a-> System.out.println(a));
					str = br.readLine();
					List<String> actionNumbers = actionItems.stream().map(a->a.split("\\.")[0]).toList(); 
					while (!actionNumbers.contains(str)) {
						System.out.println("Please enter from 1 to " + actionNumbers.size());
						str = br.readLine();
					}
					menuStep = Menu.values()[Integer.parseInt(str)+2];
					System.out.println("Chosen action -> " + menuStep);
					break;
					
				case ADD_ADMIN:
					if (currentUser.getAccessLevel() != MAX_ACCESS_LEVEL) {
						System.out.println("Access denied!");
						menuStep = Menu.CHOICE_ACTION;
					}else {
						if(adminServiceImpl.registerAdmin()) {
							System.out.println("Success!");	
						}
						else {
							System.err.println("Fail");
						}
						menuStep = Menu.CHOICE_ACTION;
					}
					break;
					
				case UPDATE_ADMIN:
					if (currentUser.getAccessLevel() != MAX_ACCESS_LEVEL) {
						System.out.println("Access denied!");
						menuStep = Menu.CHOICE_ACTION;
					}else {
						if(adminServiceImpl.updateAdmin()) {
							System.out.println("Success!");	
						}
						else {
							System.err.println("Fail");
						}
						menuStep = Menu.CHOICE_ACTION;
					}
					break;
					
				case DELETE_ADMIN:
					if (currentUser.getAccessLevel() != MAX_ACCESS_LEVEL) {
						System.out.println("Access denied!");
						menuStep = Menu.CHOICE_ACTION;
					}else {
						if(adminServiceImpl.deleteAdmin()) {
							System.out.println("Success!");	
						}
						else {
							System.err.println("Fail");
						}
						menuStep = Menu.CHOICE_ACTION;
					}
					break;
					
				case UPLOAD_CSV:
					//TODO
					break;
				case EXIT:
					currentUser=null;
					System.out.println("<<< Good by >>>");
					return;
				default:
					menuStep = Menu.EXIT;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
