package telran.java51.trading.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import telran.java51.trading.model.Index;


public interface IndexRepository extends CrudRepository<Index, String> {

	Index findBySource(String source);
	List<Index> findAllBySourceIn(Set<String> sources);
}
