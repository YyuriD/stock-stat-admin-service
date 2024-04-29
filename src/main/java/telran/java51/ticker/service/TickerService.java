package telran.java51.ticker.service;

import telran.java51.ticker.model.Ticker;

public interface TickerService {
	
	boolean addTicker(Ticker ticker);
	
	Ticker removeTicker(String tickerName);
}
