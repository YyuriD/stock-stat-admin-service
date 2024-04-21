package telran.java51.service;

public interface AdminService {
	String login(String login, String password);

	String addUser(String login, String password,String accessLevel);

	String updateUser(String login, String password, String accessLevel);

	String deleteUser(String login);

	String uploadCsvToDb();
	
	String downloadCsvfromDb();
	
	public String logout();

}
