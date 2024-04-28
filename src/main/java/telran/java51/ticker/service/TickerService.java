package telran.java51.ticker.service;

import java.time.LocalDate;

import telran.java51.ticker.model.Ticker;

public interface TickerService {
	
	Ticker addTicker(String tickerName, LocalDate dateFrom, LocalDate dateTo);
	Ticker removeTicker(String tickerName);
}
