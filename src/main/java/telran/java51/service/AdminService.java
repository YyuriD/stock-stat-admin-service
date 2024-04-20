package telran.java51.service;

import java.io.IOException;

import telran.java51.model.Admin;

public interface AdminService {
	Admin checkAccess() throws IOException;

	Boolean registerAdmin() throws IOException;

	Boolean updateAdmin() throws IOException;

	Boolean deleteAdmin() throws IOException;

	Boolean uploadCsv() throws IOException;

}
