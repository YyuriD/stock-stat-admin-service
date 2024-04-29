package telran.java51.trading.service;

import java.util.Set;

import telran.java51.trading.model.TradingSession;

public interface TradingService {
	boolean addTradings(Set<TradingSession> tradingSessions);
	
}
