package lu.wealins.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * Helper to read a csv file
 * 
 * @author TEY
 *
 */
public class CsvFileReader {

	public static final String COMMA_DELIMITER = ",";

	/**
	 * The buffer reader
	 */
	private BufferedReader br;

	/**
	 * Constructor with parameter
	 * 
	 * @param file the file to read
	 * @throws FileNotFoundException
	 */
	public CsvFileReader(File file) throws FileNotFoundException {
		InputStream inputFS = new FileInputStream(file);
		br = new BufferedReader(new InputStreamReader(inputFS));

	}

	/**
	 * Get lines of the current file
	 * 
	 * @return the stream of line
	 */
	public Stream<String> getLines() {
		return br.lines();
	}

	/**
	 * Close the current Buffer
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		br.close();
	}

}
