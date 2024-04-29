package telran.java51.ticker.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.ticker.dao.TickerRepository;
import telran.java51.ticker.exceptions.TickerNotFoundException;
import telran.java51.ticker.model.Ticker;

@Service
@AllArgsConstructor
@Configuration
public class TickerServiceImpl implements TickerService {

	final TickerRepository tickerRepository;

	@Override
	public boolean addTicker(Ticker ticker) {//TODO change return to Optional
		if(ticker == null) {
			return false;
		}
		tickerRepository.save(ticker);
		return true;
	}

	@Override 
	public Ticker removeTicker(String tickerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
