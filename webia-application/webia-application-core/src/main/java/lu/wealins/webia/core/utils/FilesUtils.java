/**
 * 
 */
package lu.wealins.webia.core.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author oro
 *
 */
public class FilesUtils {

	/**
	 * 
	 * @param directory
	 * @param filenamePattern
	 * @param dirPattern
	 * @return
	 */
	public static Collection<File> listFiles(File directory, String filenamePattern, String dirPattern) {
		return FileUtils.listFiles(directory, new RegexFileFilter(filenamePattern), new RegexFileFilter(dirPattern));
	}

	/**
	 * Find a file in the specified path.
	 * 
	 * @param filename the file name.
	 * @param targetPath the directory where to look the file.
	 * @return Null if the file is not found.
	 */
	public static File getFile(String filename, String targetPath) {
		File directory = new File(targetPath);
		return FileUtils.listFiles(directory, FileFilterUtils.nameFileFilter(filename), TrueFileFilter.TRUE)
				.stream().findFirst().orElse(null);
	}

	/**
	 * Create a CSV file with the given format. The consumer method is called during the file creation. It aims to contain the logic of the file creation.
	 * 
	 * @param filename the CSV filename
	 * @param csvFormat the CSV format
	 * @param consumer the CSV file creation logic
	 * @param headers the CSV headers
	 * @throws IOException if an error is occurred during the creation the file
	 */
	public static void createCsvFile(String filename, CSVFormat csvFormat, Consumer<CSVPrinter> csvPrinter) throws IOException {
		Assert.notNull(StringUtils.stripToNull(filename), "Csv file name must not be null");
		Assert.notNull(csvFormat, "Csv format must not be null");
		Assert.notNull(csvPrinter, "Csv consumer must not be null");

		String ext = ".csv";

		if (!filename.endsWith(ext)) {
			filename += ext;
		}

		FileWriter out = new FileWriter(filename);
		CSVPrinter printer = new CSVPrinter(out, csvFormat);
		try {
			csvPrinter.accept(printer);
		} finally {
			printer.close();
		}

	}

	/**
	 * Default constructor.
	 */
	private FilesUtils() {
		throw new UnsupportedOperationException("Could not invoke the constructor of this class");
	}
}
