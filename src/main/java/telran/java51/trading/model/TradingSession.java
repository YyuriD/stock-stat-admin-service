package telran.java51.trading.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.java51.ticker.model.Ticker;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"ticker","date"})
@Entity
@Table(name = "trading_sessions")
@ToString
public class TradingSession  implements Serializable{

	private static final long serialVersionUID = -179664029256824275L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	public TradingSession(Ticker ticker, LocalDate date, BigDecimal open, BigDecimal high, BigDecimal low,
			BigDecimal close, BigDecimal adjClose, BigInteger volume) {
		this.ticker = ticker;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adjClose = adjClose;
		this.volume = volume;
	}

	@ManyToOne	
	@JoinColumn(name = "ticker")
	Ticker ticker;
		
	@Temporal(TemporalType.DATE)
	LocalDate date;
	
	BigDecimal open;
	
	BigDecimal high;
	
	BigDecimal low;
	
	BigDecimal close;
	
	BigDecimal adjClose;
	
	BigInteger volume;
	
}
