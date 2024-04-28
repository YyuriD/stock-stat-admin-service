package telran.java51.ticker.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.ticker.dao.TickerRepository;
import telran.java51.ticker.exceptions.TickerExistException;
import telran.java51.ticker.model.Ticker;
import telran.java51.trading.model.TradingSession;

@Service
@AllArgsConstructor
@Configuration
public class TickerServiceImpl implements TickerService {

	final TickerRepository tickerRepository;

	@Override
	public Ticker addTicker(String tickerName, LocalDate dateFrom, LocalDate dateTo) {
		if (tickerRepository.existsById(tickerName)) {
			throw new TickerExistException();
		}
		Set<TradingSession> tradings = null;//TODO 
		Ticker ticker = new Ticker(tickerName, dateFrom, dateTo, tradings);
		return tickerRepository.save(ticker);
	}

	@Override
	public Ticker removeTicker(String tickerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
