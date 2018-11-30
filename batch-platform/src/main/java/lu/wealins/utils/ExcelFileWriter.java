/**
 * 
 */
package lu.wealins.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility class for easily writing basic Excel files.
 *
 */
public class ExcelFileWriter {

	private final String path;
	private final String sheetName;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private int rowLineCursor = 0;

	private Log logger = LogFactory.getLog(ExcelFileWriter.class);

	public ExcelFileWriter(String path, String sheetName) {
		this.path = path;
		this.sheetName = sheetName;

		// initialize workbook and sheet
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(this.sheetName);
	}

	public void appendLine(String... elements) {

		XSSFRow row = sheet.createRow(rowLineCursor++);
		for (int i = 0; i < elements.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(elements[i]);
		}
	}

	public void close() throws IOException {
		FileOutputStream fileOut = new FileOutputStream(path);

		logger.info("writing " + path);
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		logger.info("File " + path + " successfully created");
	}
}
