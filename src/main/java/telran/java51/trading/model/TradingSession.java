package telran.java51.trading.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.java51.security.TradingSessionId;
import telran.java51.ticker.model.Ticker;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"ticker","date"})
@Entity
@Table(name = "trading_sessions")
@ToString
@IdClass(TradingSessionId.class)
public class TradingSession  implements Serializable{

	private static final long serialVersionUID = -179664029256824275L;
		
	@Id
	@ManyToOne
	@JoinColumn(name = "ticker")
	Ticker ticker;
		
	@Id
	@Temporal(TemporalType.DATE)
	LocalDate date;
	
	BigDecimal open;
	
	BigDecimal high;
	
	BigDecimal low;
	
	BigDecimal close;
	
	BigDecimal adjClose;
	
	BigInteger volume;
	
}
