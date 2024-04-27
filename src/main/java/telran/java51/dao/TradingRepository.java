package telran.java51.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import telran.java51.model.TradingSession;

public interface TradingRepository extends CrudRepository<TradingSession, LocalDate>{

}
