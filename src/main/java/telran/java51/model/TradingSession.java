package telran.java51.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "date")
@Entity
@Table(name = "trading_sessions")
public class TradingSession  implements Serializable{

	private static final long serialVersionUID = -179664029256824275L;
	
	@Id
	LocalDate date;
	
	BigDecimal open;
	
	BigDecimal high;
	
	BigDecimal low;
	
	BigDecimal close;
	
	BigDecimal adjClose;
	
	BigInteger volume;
	
	
	
	
	
}
