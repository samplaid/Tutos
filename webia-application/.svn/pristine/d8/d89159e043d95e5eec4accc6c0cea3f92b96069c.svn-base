package lu.wealins.webia.core.service;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.SecurityContext;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.editing.common.webia.ContractType;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.MailingAddress;
import lu.wealins.editing.common.webia.User;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;
import lu.wealins.webia.ws.rest.request.TranscoType;

public interface DocumentService {

	DocumentGenerationResponse generateFile(SecurityContext context, EditingRequest request);

	Document generateDocument(Header header, CoverLetter coverLetter, List<MailingAddress> mailingAddresses);

	Header generateHeader(EditingUser creationUser, DocumentType documentType, String language, String county, UUID docUid);

	Header generateHeader(String userMail, DocumentType documentType, String language, String county, UUID docUid);

	Header generateHeader(User user, DocumentType documentType, String language, String transcodedProductCountry, UUID docUid);

	ContractType getContractType(ProductDTO productDTO);

	String getTransco(TranscoType type, String odsCode);

	EditingRequest updateEditingRequest(EditingRequest editingRequest);

	String saveXmlDocuments(Documents document, String path);
	
	String saveCsvDocument(String path, List<?>datas) throws Exception;

	String saveXmlDocument(lu.wealins.editing.common.webia.sepa.Document document, String path);

	boolean isSpecificRiskActive(String alternativeFunds);

	AgentContactLiteDTO getAgentContact(AgentDTO agent);

	String getLanguage(AgentContactLiteDTO agentContact);

	AgentContactLiteDTO getAgentContact(FundDTO fund, PolicyDTO policyDTO, AgentDTO mailToAgent);

	String getMailFromTrigram(String trigram);

	/**
	 * Build an {@link User} corresponding to the trigram provided.
	 * 
	 * @param trigram the trigram
	 * @return the {@link User} corresponding to the trigram provided
	 */
	User getUserFromTrigram(String trigram);


}
