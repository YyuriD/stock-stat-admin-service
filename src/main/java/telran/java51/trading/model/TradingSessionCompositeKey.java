package telran.java51.trading.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import telran.java51.ticker.model.Ticker;

@AllArgsConstructor
@NoArgsConstructor
@Component
@IdClass(TradingSessionCompositeKey.class)
public class TradingSessionCompositeKey implements Serializable{

	private static final long serialVersionUID = 4319092983862862264L;
	
	private Ticker ticker;
	private LocalDate date;

}
