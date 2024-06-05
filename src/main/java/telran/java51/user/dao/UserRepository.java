package telran.java51.user.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import telran.java51.admin.model.AdminAccount;
import telran.java51.user.model.UserAccount;

@Repository
public interface UserRepository extends CrudRepository<UserAccount, String> {
	Optional<AdminAccount> findAdminByLogin(String login);	
	Stream<AdminAccount> findAllAdminsBy();
}
