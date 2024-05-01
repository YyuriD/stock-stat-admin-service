package telran.java51.admin.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import telran.java51.admin.model.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {

}
