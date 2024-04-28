package telran.java51.ticker.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.ticker.model.Ticker;

public interface TickerRepository extends CrudRepository<Ticker, String>{

}
