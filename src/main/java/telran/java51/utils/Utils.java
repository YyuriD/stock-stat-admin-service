package telran.java51.utils;

import static telran.java51.utils.CsvHeaders.ADJ_CLOSE;
import static telran.java51.utils.CsvHeaders.CLOSE;
import static telran.java51.utils.CsvHeaders.DATE;
import static telran.java51.utils.CsvHeaders.HIGH;
import static telran.java51.utils.CsvHeaders.LOW;
import static telran.java51.utils.CsvHeaders.OPEN;
import static telran.java51.utils.CsvHeaders.VOLUME;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import telran.java51.trading.model.TradingSession;

public final class Utils {

	public static Set<TradingSession> getTradingSessions(String text, String tickerName) {
		return parseCsv(new StringReader(text), tickerName);
	}

	public static Set<TradingSession> getTradingSessions(String filePath) throws FileNotFoundException {
		Path path = Paths.get(filePath);
		String tickerName = path.getFileName().toString().split("\\.")[0];
		return parseCsv(new FileReader(filePath), tickerName);
	}

	public static void printCsv(String filePath) throws IOException {
		Set<TradingSession> tradingSessions = getTradingSessions(filePath);
		System.out.println();
		for (TradingSession tradingSession : tradingSessions) {
			System.out.print(tradingSession.toString() + "\t");
			System.out.print(tradingSession.toString() + "\t");
			System.out.print(tradingSession.toString() + "\t");
			System.out.print(tradingSession.toString() + "\t");
			System.out.print(tradingSession.toString() + "\t");
			System.out.print(tradingSession.toString() + "\t");
			System.out.println(tradingSession.toString());
		}
	}

	public static Set<TradingSession> parseCsv(Reader csvReader, String tickerName) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(CsvHeaders.class).setSkipHeaderRecord(true)
				.setIgnoreHeaderCase(true).build();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]" + "[yyyy-MM-dd]" + "[MM/dd/yyyy]");
		Set<TradingSession> tradingSessions = new HashSet<TradingSession>();
		Iterable<CSVRecord> csvRecords = null;
		try {
			csvRecords = csvFormat.parse(csvReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		System.out.println("Read " + tradingSessions.size() + " trading sessions.");
		return tradingSessions;
	}

	public static String getFullPath(String fileName) {		
		String fileSeparator = FileSystems.getDefault().getSeparator();		
		return Utils.getCurrentDirectory() + fileSeparator + fileName;
	}
	
	public static Set<String> getFilesList(String directory) throws IOException {
		try (Stream<Path> stream = Files.list(Paths.get(directory))) {
			return stream.filter(file -> !Files.isDirectory(file)).map(Path::getFileName).map(Path::toString)
					.collect(Collectors.toSet());
		}
	}
	
	public static String getCurrentDirectory() {
		String currentDirectory = Paths.get("").toAbsolutePath().toString();
		return currentDirectory;
	}
	
	
	public void saveTradingSessionsToCsv(String filePath) {
		// TODO Auto-generated method stub
	}
}
