package lu.wealins.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manage a basic FileWriter stream
 * 
 * Delimiter is configurable. End of line is configurable
 * 
 * @author xqv60
 *
 */
public final class BasicFileWriter {

	private String path;
	private FileWriter fileWriter;
	private Log logger = LogFactory.getLog(BasicFileWriter.class);

	/**
	 * Constructor with parameter
	 * 
	 * @param path the path of the ouput file
	 */
	public BasicFileWriter(String path) {
		this.path = path;
	}

	public void append(String endOfLine, String delimiter, String... elements) {
		String line = String.join(delimiter, elements);

		try {
			if (fileWriter == null) {
				File temp = new File(path);
				Files.createDirectories(Paths.get(temp.getParent()));
				fileWriter = new FileWriter(path);
			}
			fileWriter.append(line + endOfLine);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void close() throws IOException {
		if (fileWriter != null) {
			fileWriter.close();
		}
	}
}
