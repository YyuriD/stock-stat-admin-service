package telran.java51.access.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.access.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}
