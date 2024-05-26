package telran.java51.trading.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import telran.java51.trading.model.TradingSession;
import telran.java51.trading.model.TradingSessionId;

@Repository
public interface TradingRepository extends CrudRepository<TradingSession, TradingSessionId> {
	
	@Transactional
	void deleteByTickerName(String name);
	
}
