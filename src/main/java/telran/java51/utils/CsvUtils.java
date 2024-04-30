package telran.java51.utils;

import static telran.java51.utils.CsvHeaders.ADJ_CLOSE;
import static telran.java51.utils.CsvHeaders.CLOSE;
import static telran.java51.utils.CsvHeaders.DATE;
import static telran.java51.utils.CsvHeaders.HIGH;
import static telran.java51.utils.CsvHeaders.LOW;
import static telran.java51.utils.CsvHeaders.OPEN;
import static telran.java51.utils.CsvHeaders.VOLUME;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import telran.java51.trading.model.TradingSession;

public final class CsvUtils {

	public static Set<TradingSession> parseCsvToTradingSessions(String filePath) throws IOException {

		Reader csvReader = new FileReader(filePath);
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(CsvHeaders.class).setSkipHeaderRecord(true)
				.setIgnoreHeaderCase(true).build();
		Iterable<CSVRecord> csvRecords = csvFormat.parse(csvReader);
		Path path = Paths.get(filePath);
		String tickerName = path.getFileName().toString().split("\\.")[0];
		ArrayList<LocalDate> dateList = new ArrayList<LocalDate>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for (CSVRecord record : csvRecords) {
			LocalDate date = LocalDate.parse(record.get(DATE), formatter);
			dateList.add(date);
		}
		
		Set<TradingSession> tradingSessions = new HashSet<TradingSession>();

		csvReader = new FileReader(filePath);
		csvFormat = CSVFormat.DEFAULT.builder().setHeader(CsvHeaders.class).setSkipHeaderRecord(true)
				.setIgnoreHeaderCase(true).build();
		csvRecords = csvFormat.parse(csvReader);
		for (CSVRecord record : csvRecords) {
			LocalDate date = LocalDate.parse(record.get(DATE.name()), formatter);
			BigDecimal open = BigDecimal.valueOf(Double.parseDouble(record.get(OPEN.name())));
			BigDecimal high = BigDecimal.valueOf(Double.parseDouble(record.get(HIGH.name())));
			BigDecimal low = BigDecimal.valueOf(Double.parseDouble(record.get(LOW.name())));
			BigDecimal close = BigDecimal.valueOf(Double.parseDouble(record.get(CLOSE.name())));
			BigDecimal adjClose = BigDecimal.valueOf(Double.parseDouble(record.get(ADJ_CLOSE.name())));
			BigInteger volume = BigInteger.valueOf(Long.parseLong(record.get(VOLUME.name())));
			TradingSession trading = new TradingSession(tickerName, date, open, high, low, close, adjClose, volume);
			tradingSessions.add(trading);
		}
		System.out.println("tradingSessions size = " + tradingSessions.size());
		return tradingSessions;
	}

	public static void printCsv(String filePath) throws IOException {
		Reader csvReader = new FileReader(filePath);
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(CsvHeaders.class).setSkipHeaderRecord(true).build();
		Iterable<CSVRecord> csvRecords = csvFormat.parse(csvReader);
		Arrays.stream(csvFormat.getHeader()).forEach(h -> System.out.print(h.toString() + "\t\t"));
		System.out.println("");
		for (CSVRecord record : csvRecords) {
			System.out.print(record.get(DATE.name()) + "\t");
			System.out.print(record.get(OPEN.name()) + "\t");
			System.out.print(record.get(HIGH.name()) + "\t");
			System.out.print(record.get(LOW.name()) + "\t");
			System.out.print(record.get(CLOSE.name()) + "\t");
			System.out.print(record.get(ADJ_CLOSE.name()) + "\t");
			System.out.println(record.get(VOLUME.name()));
		}
	}
	
	public void saveTradingSessionsToCsv(String filePath) {
		// TODO Auto-generated method stub		
	}
}
