package telran.java51.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CliUtils {

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
}
