package telran.java51.trading.service;

import telran.java51.trading.model.TradingSession;

public interface TradingService {
	TradingSession addTrading(String tickerName);
}
