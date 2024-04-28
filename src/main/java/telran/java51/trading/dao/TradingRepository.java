package telran.java51.trading.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.trading.model.TradingSession;
import telran.java51.trading.model.TradingSessionCompositeKey;

public interface TradingRepository extends CrudRepository<TradingSession, TradingSessionCompositeKey> {

}
