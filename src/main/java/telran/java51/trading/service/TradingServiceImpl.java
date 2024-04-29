package telran.java51.trading.service;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.ticker.service.TickerServiceImpl;
import telran.java51.trading.dao.TradingRepository;
import telran.java51.trading.exceptions.TradingNotFoundException;
import telran.java51.trading.model.TradingSession;

@Service
@AllArgsConstructor
@Configuration
public class TradingServiceImpl implements TradingService {
	final TradingRepository tradingRepository;
	final TickerServiceImpl tickerService;

	@Override
	public boolean addTradings(Set<TradingSession> tradingSessions) {
		//TODO parse csv, get tickerName from file name and get dateFrom and dateTo,
		
		TradingSession trading = tradingSessions.stream().findAny().orElseThrow(TradingNotFoundException::new);		
		tickerService.addTicker(trading.getTicker());	//TODO check adding of ticker
		tradingRepository.saveAll(tradingSessions);
		return true;
	}

}
