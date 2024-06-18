package telran.java51.trading.service;

import java.util.Set;

import telran.java51.trading.model.TradingSession;

public interface TradingService {
	
	long addData(Set<TradingSession> tradingSessions);
	
	Set<TradingSession> getDataFromRemoteService(String TickerName, String fromDate, String toDate, String source);

	public void removeByTickerName(String TickerName);
	
	public void removeAll();
	
	public long getTradingsQuantity();
	
	
}
