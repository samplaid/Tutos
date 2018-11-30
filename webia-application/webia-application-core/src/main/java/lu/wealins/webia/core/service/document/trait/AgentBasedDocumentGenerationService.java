package lu.wealins.webia.core.service.document.trait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import lu.wealins.webia.ws.rest.request.EditingRequest;

/**
 * Trait that build the xml path based on the policy ID and the agent.
 */
public interface AgentBasedDocumentGenerationService {

	/**
	 * Build the path for the generated file, based on the policy id, the agent and the timestamp.
	 * 
	 * @param editingRequest the {@link EditingRequest} that provide the agent
	 * @param policyId the policy id
	 * @param filePath the file path retrieved from the configuration
	 * @return the path of the xml file
	 */
	public default String buildXmlPath(EditingRequest editingRequest, String policyId, String filePath) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formatedDate = date.format(formatter);
		String policyId1 = StringUtils.replaceChars(policyId, "/", "_");
		String path = filePath + "/" + policyId1 + "_" + editingRequest.getAgent() + "_" + formatedDate + ".xml";
		return path;
	}
}
