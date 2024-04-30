package telran.java51.security;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java51.ticker.model.Ticker;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "ticker", "date" })
public class TradingSessionId implements Serializable {

	private static final long serialVersionUID = 6449136850011727098L;
	private Ticker ticker;
	private LocalDate date;
}
