package telran.java51.utils;

import static telran.java51.utils.Headers.ADJ_CLOSE;
import static telran.java51.utils.Headers.CLOSE;
import static telran.java51.utils.Headers.DATE;
import static telran.java51.utils.Headers.HIGH;
import static telran.java51.utils.Headers.LOW;
import static telran.java51.utils.Headers.OPEN;
import static telran.java51.utils.Headers.VOLUME;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public final class CsvUtils {
	
	public static void parseCsvTradings(String filePath) throws IOException {
		
		Reader inReader = new FileReader(filePath);
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(Headers.class).setSkipHeaderRecord(true).build();
		Iterable<CSVRecord> records = csvFormat.parse(inReader);
		
	}
	
	public static void printCsv(String filePath) throws IOException {
		
		Reader inReader = new FileReader(filePath);
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(Headers.class).setSkipHeaderRecord(true).build();
		Iterable<CSVRecord> records = csvFormat.parse(inReader);
		Arrays.stream(csvFormat.getHeader()).forEach(h->System.out.print(h.toString() + "\t\t"));
		System.out.println("");
		for (CSVRecord record : records) {
	        System.out.print(record.get(DATE.name()) + "\t");
	        System.out.print(record.get(OPEN.name()) + "\t");
	        System.out.print(record.get(HIGH.name()) + "\t");
	        System.out.print(record.get(LOW.name()) + "\t");
	        System.out.print(record.get(CLOSE.name()) + "\t");
	        System.out.print(record.get(ADJ_CLOSE.name()) + "\t");
	        System.out.println(record.get(VOLUME.name()));      
	    }
	}
}
