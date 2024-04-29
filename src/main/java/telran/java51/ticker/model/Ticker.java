package telran.java51.ticker.model;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java51.trading.model.TradingSession;

@NoArgsConstructor
@Entity
@Getter
@EqualsAndHashCode(of = "name")
@Table(name = "tickers")

public class Ticker {

	public Ticker(String name, LocalDate fromDate, LocalDate toDate) {
		this.name = name;
		this.dateFrom = fromDate;
		this.dateTo = toDate;
	}

	@Id
	String name;

	@Setter
	@Temporal(TemporalType.DATE)
	LocalDate dateFrom;

	@Setter
	@Temporal(TemporalType.DATE)
	LocalDate dateTo;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ticker") // cascade = CascadeType.REMOVE does not work
	@OnDelete(action = OnDeleteAction.CASCADE)
	Set<TradingSession> tradingSessions;

	@Override
	public String toString() {
		return "Ticker [name=" + name + ", fromDate=" + dateFrom + ", toDate=" + dateTo + "]";
	}

}
