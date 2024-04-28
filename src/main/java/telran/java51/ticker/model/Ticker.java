package telran.java51.ticker.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java51.trading.model.TradingSession;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@EqualsAndHashCode(of = "name")
@Table(name = "tickers")
public class Ticker {
	
	@Id
	String name;
	
	@Setter
	@Temporal(TemporalType.DATE)
	LocalDate fromDate;
	
	@Setter
	@Temporal(TemporalType.DATE)
	LocalDate toDate;
	
	@OneToMany(mappedBy = "ticker")
	Set<TradingSession> tradingSessions;
	
}
