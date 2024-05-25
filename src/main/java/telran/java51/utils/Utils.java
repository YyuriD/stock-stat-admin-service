package telran.java51.utils;

import static telran.java51.utils.TradingTableHeaders.ADJ_CLOSE;
import static telran.java51.utils.TradingTableHeaders.CLOSE;
import static telran.java51.utils.TradingTableHeaders.DATE;
import static telran.java51.utils.TradingTableHeaders.HIGH;
import static telran.java51.utils.TradingTableHeaders.LOW;
import static telran.java51.utils.TradingTableHeaders.OPEN;
import static telran.java51.utils.TradingTableHeaders.VOLUME;

import java.io.BufferedReader;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import telran.java51.trading.model.TradingSession;

public final class Utils {

	public static Set<TradingSession> parseTradingSessions(String text, String tickerName) {
		return parseCsv(new StringReader(text), tickerName);
	}

	public static Set<TradingSession> parseTradingSessions(String filePath) throws FileNotFoundException {
		Path path = Paths.get(filePath);
		String tickerName = path.getFileName().toString().split("\\.")[0];
		return parseCsv(new FileReader(filePath), tickerName);
	}

	public static long printCsv(String filePath) {
		long count = 0;
		try {
			count = Files.lines(Paths.get(filePath)).count();
			FileReader fl = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fl);
			String[] headers = br.readLine().split(",");	
			String[] rows = br.lines().toArray(String[] :: new);
			br.close();
			printTable(rows, headers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		return  count;
	}

	public static Set<TradingSession> parseCsv(Reader csvReader, String tickerName) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(TradingTableHeaders.class).setSkipHeaderRecord(true)
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
		return Paths.get("").toAbsolutePath().toString();
	}

	public void saveTradingSessionsToCsv(String filePath) {
		// TODO Auto-generated method stub
	}

	public static void printTable(String[] rows, String[] headers) {
		Object[][] data = new String[rows.length + 1][headers.length];
		data[0] = headers;
		for (int i = 0; i < rows.length; i++) {
			data[i+1] = rows[i].split(",");
		}
		TableModel model = new ArrayTableModel(data);
		TableBuilder tableBuilder = new TableBuilder(model);
		tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.oldschool);
		System.out.println(tableBuilder.build().render(80));
	}
	
	public static long getTimestampFromString(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]" + "[yyyy-MM-dd]" + "[MM/dd/yyyy]");
		LocalDate date = LocalDate.parse(dateString, formatter);	
		return Timestamp.valueOf(date.atTime(9, 0)).getTime()/1000;// /1000 - without milliseconds
	}
	
}
