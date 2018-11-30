/**
 * 
 */
package lu.wealins.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xqv66
 *
 */
public class CsvFileWriter {

	private static final String COMMA_DELIMITER = ",";

	private String path;
	private FileWriter fileWriter;
	private Log logger = LogFactory.getLog(CsvFileWriter.class);

	public CsvFileWriter(String path) {
		this.path = path;
	}

	public void append(String... elements) {
		String line = String.join(COMMA_DELIMITER, elements);

		try {
			if (fileWriter == null) {
				File temp = new File(path);
				Files.createDirectories(Paths.get(temp.getParent()));
				fileWriter = new FileWriter(path);
			}
			fileWriter.append(line);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void appendWithDelimiter(String delimiter, String... elements) {
		String line = String.join(delimiter, elements);

		try {
			if (fileWriter == null) {
				File temp = new File(path);
				Files.createDirectories(Paths.get(temp.getParent()));
				fileWriter = new FileWriter(path);
			}
			fileWriter.append(line);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void close() throws IOException {
		if (fileWriter != null) {
			fileWriter.close();
		}
	}

	/**
	 * Get the path.
	 * 
	 * @return the path.
	 */
	public String getPath() {
		return path;
	}
}
