package telran.java51;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.java51.access.service.AccessServiceImpl;

@SpringBootApplication
public class StockStatDataUploaderApplication implements CommandLineRunner {

	@Autowired
	AccessServiceImpl accessServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(StockStatDataUploaderApplication.class, args);
	}

	@Override

	public void run(String... args) throws Exception {
		
		try (BufferedReader consolReader = new BufferedReader(new InputStreamReader(System.in))) {
			
			while (true) {
				System.out.println("Enter email");	
				String email = consolReader.readLine();
				System.out.println("Enter password");		
				String password = consolReader.readLine();
				if (accessServiceImpl.checkAccess(email, password)) {
					System.out.println("enter file name ");
					String fileName = consolReader.readLine();
					//TODO
				} else {
					System.out.println(" Try again? Y/N");
					if("N".equals(consolReader.readLine())) {
						break;
					}
				}
				
			}
		} catch (Exception e) {
			//
		}

	}

}
