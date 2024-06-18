package telran.java51.trading.service;

import java.net.URI;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import telran.java51.trading.dao.TradingRepository;
import telran.java51.trading.model.TradingSession;
import telran.java51.utils.Utils;

@Service
@AllArgsConstructor
@Configuration
public class TradingServiceImpl implements TradingService {
	final String baseUrl = "https://query1.finance.yahoo.com/v7/finance/download/";
	final TradingRepository tradingRepository;

	@Override
	public long addData(Set<TradingSession> tradingSessions) {
		long prevQuantity = tradingRepository.count();
		tradingRepository.saveAll(tradingSessions);
		return tradingRepository.count() - prevQuantity;
	}

	@Override
	public Set<TradingSession> getDataFromRemoteService(String tickerName, String fromDate, String toDate, String source) {				
		String fromTimestamp = Long.toString(Utils.getTimestampFromDateString(fromDate));
		String toTimestamp = Long.toString(Utils.getTimestampFromDateString(toDate));
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + tickerName)
				.queryParam("interval", "1d")
				.queryParam("period1", fromTimestamp)
				.queryParam("period2", toTimestamp);
		URI url = builder.build().toUri();
		RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url);
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		System.out.println(response.getStatusCode());
		MediaType contentType = MediaType.parseMediaType("text/csv; charset=UTF-8");
		if(!response.getHeaders().getContentType().equalsTypeAndSubtype(contentType)) {
			throw new UnsupportedMediaTypeStatusException(contentType.toString());
		}		
		return Utils.parseTradingSessions(response.getBody(), tickerName, source);			
	}

	public long getTradingsQuantity() {
		return tradingRepository.count();
	}

	@Override
	public void removeByTickerName(String name) {
		tradingRepository.deleteByTickerName(name);
	}

	public void removeAll() {
		tradingRepository.deleteAll();		
	}
	
}
