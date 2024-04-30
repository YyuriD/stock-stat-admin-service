package telran.java51.trading.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.security.TradingSessionId;
import telran.java51.trading.model.TradingSession;

public interface TradingRepository extends CrudRepository<TradingSession, TradingSessionId> {

}
