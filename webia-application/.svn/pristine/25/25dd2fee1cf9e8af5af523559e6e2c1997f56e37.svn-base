/**
 * 
 */
package lu.wealins.webia.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oro
 *
 */
public class FilesUtilsTest {

	private static final String TARGET_PATH = "src/test/resources/mock/files";

	@Test
	public void testListFiles() {
		File file = FileUtils.getFile(TARGET_PATH);
		Collection<File> files = FilesUtils.listFiles(file, "[0-9]+_[0-9]+.pdf", "[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Assert.assertTrue(files.size() == 2);
	}

	@Test
	public void testGetFile() {
		String filename = "173_20180712093000659.xml";
		File files = FileUtils.getFile(TARGET_PATH);
		File file = FilesUtils.getFile(filename, files.getPath());
		Assert.assertNotNull("File cannot be found", file);
	}

	@Test
	public void testCreateCsvFile() throws IOException {
		List<String[]> data = new ArrayList<>();
		String[] strings = new String[] { "sdfsdfl", "élksdléfké" };
		data.add(strings);
		strings = new String[] { "dfgfdg", "fghfgh" };
		data.add(strings);
		Consumer<CSVPrinter> csvPrinter = (printer) -> {
			try {
				printer.printRecords(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		
		String path = "src/test/resources/mock/files/generated/commissions/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/";
		String filename = path + "test.csv";

		File directory = new File(path);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		FilesUtils.createCsvFile(filename, CSVFormat.EXCEL.withDelimiter(';'), csvPrinter);

		File file = FilesUtils.getFile("test.csv", path);
		Assert.assertNotNull(file);

		// delete directory;
		file.delete();
		directory.delete();

	}

}
