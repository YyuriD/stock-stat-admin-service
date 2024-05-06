package telran.java51.trading.service;

import java.util.Set;

import telran.java51.trading.model.TradingSession;

public interface TradingService {
	
	long addData(Set<TradingSession> tradingSessions);
	
	Set<TradingSession> getDataFromRemoteService(String TickerName);

	void downloadData(String filePath);
	
}
