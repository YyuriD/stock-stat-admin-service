package telran.java51.trading.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.ticker.dao.TickerRepository;
import telran.java51.ticker.model.Ticker;
import telran.java51.trading.dao.TradingRepository;
import telran.java51.trading.model.TradingSession;

@Service
@AllArgsConstructor
@Configuration
public class TradingServiceImpl implements TradingService {
	final TradingRepository tradingRepository;
	final TickerRepository tickerRepository;

	@Override
	public TradingSession addTrading(String tickerName) {
		Set<TradingSession> tradings = null;//TODO 
		Ticker ticker = tickerRepository.findById(tickerName).
				orElse(tickerRepository.save(new Ticker(tickerName, LocalDate.now(), LocalDate.now(), tradings)));
		
		TradingSession trading = new TradingSession(ticker, LocalDate.now().minusDays(1),
				BigDecimal.valueOf(100),BigDecimal.valueOf(95), 
				BigDecimal.valueOf(110), BigDecimal.valueOf(110), 
				BigDecimal.valueOf(108),BigInteger.valueOf(50000));
		return tradingRepository.save(trading);
	}

}
