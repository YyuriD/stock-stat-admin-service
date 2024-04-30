package telran.java51.trading.service;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java51.trading.dao.TradingRepository;
import telran.java51.trading.model.TradingSession;

@Service
@AllArgsConstructor
@Configuration
public class TradingServiceImpl implements TradingService {
	final TradingRepository tradingRepository;

	@Override
	public boolean addData(Set<TradingSession> tradingSessions) {	
		tradingRepository.saveAll(tradingSessions);
		return true;
	}

	@Override
	public void uploadDataFromRemoteService(String filePath) {
		// TODO Auto-generated method stub
		
	}

	public void downloadData(String filePath) {
		// TODO Auto-generated method stub
		
	}
	

}
