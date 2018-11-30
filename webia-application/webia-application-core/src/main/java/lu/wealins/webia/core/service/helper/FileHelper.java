/**
 * 
 */
package lu.wealins.webia.core.service.helper;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author oro
 *
 */
@Service
public class FileHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

	@Value("${editiqueMachineAlias:}")
	private String editiqueMachineAlias;

	public File toFile(String path) {
		LOGGER.info("EditiqueMachineAlias: {} and path={}", editiqueMachineAlias, path);
		String aliasTruncated = path;

		if (!SystemUtils.IS_OS_WINDOWS) {
			aliasTruncated = path.replaceFirst(editiqueMachineAlias, "");
		}

		String fileSystemPath = FilenameUtils.separatorsToSystem(aliasTruncated);
		return new File(fileSystemPath);
	}

}
