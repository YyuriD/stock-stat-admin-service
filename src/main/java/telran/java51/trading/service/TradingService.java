package telran.java51.trading.service;

import java.util.Set;

import telran.java51.trading.model.TradingSession;

public interface TradingService {
	
	boolean addData(Set<TradingSession> tradingSessions);
	
	void uploadDataFromRemoteService(String filePath);

	void downloadData(String filePath);
	
}
