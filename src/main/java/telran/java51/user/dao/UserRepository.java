package telran.java51.user.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import telran.java51.user.model.UserAccount;

@Repository
public interface UserRepository extends CrudRepository<UserAccount, String> {
	
}
