package lu.wealins.webia.core.service.document.trait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

/**
 * Trait that build the xml path based on the policy.
 */
public interface PolicyBasedDocumentGenerationService {

	/**
	 * Build the path for the generated file, based on the policy id and the timestamp.
	 * 
	 * @param policyId the policy id
	 * @param filePath the filePath
	 * @return the path of the xml file
	 */
	public default String buildXmlPath(String policyId, String filePath) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formatedDate = date.format(formatter);
		String policyId1 = StringUtils.replaceChars(policyId, "/", "_");
		String path = filePath + "/" + policyId1 + "_" + formatedDate + ".xml";
		return path;
	}
}
