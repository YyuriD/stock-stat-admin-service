package telran.java51.trading.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import telran.java51.security.TradingSessionId;
import telran.java51.trading.model.TradingSession;

@Repository
public interface TradingRepository extends CrudRepository<TradingSession, TradingSessionId> {

}
