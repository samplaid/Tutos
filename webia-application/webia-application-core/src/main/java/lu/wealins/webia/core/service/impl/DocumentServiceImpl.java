package lu.wealins.webia.core.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.PolicyAgentShareDTO;
import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.UserDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.AlternativeFundType;
import lu.wealins.editing.common.webia.ContractType;
import lu.wealins.editing.common.webia.CoverLetter;
import lu.wealins.editing.common.webia.Document;
import lu.wealins.editing.common.webia.Documents;
import lu.wealins.editing.common.webia.Header;
import lu.wealins.editing.common.webia.MailingAddress;
import lu.wealins.editing.common.webia.User;
import lu.wealins.webia.core.mapper.UserMapper;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityAgentService;
import lu.wealins.webia.core.service.TranscoServices;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.request.DocumentGenerationResponse;
import lu.wealins.webia.ws.rest.request.EditingRequest;
import lu.wealins.webia.ws.rest.request.EditingUser;
import lu.wealins.webia.ws.rest.request.FileGenerationRequest;
import lu.wealins.webia.ws.rest.request.FileGenerationResponse;
import lu.wealins.webia.ws.rest.request.SearchTranscoRequest;
import lu.wealins.webia.ws.rest.request.SearchTranscoResponse;
import lu.wealins.webia.ws.rest.request.TranscoType;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestRequest;
import lu.wealins.webia.ws.rest.request.UpdateEditingRequestResponse;

@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

	/**
	 * Utils
	 */
	@Autowired
	private RestClientUtils restClientUtils;

	/**
	 * Mappers
	 */
	@Autowired
	private UserMapper userMapper;

	/**
	 * Constants
	 */
	private static final String GENERATE_FILE = "generateFile";

	private static final String PRODUCT_CAPI = "PRODUCT_CAPI";

	private static final String UPDATE_EDITING_REQUEST = "updateEditingRequest";

	private static final String AGENT_CONTACT_FUNCTION = "CPS";

	private static final String EMAIL_SUFFIX = "@wealins.com";

	private static final String ACTIVE_1 = "1";

	/**
	 * Services
	 */
	@Autowired
	private WebiaApplicationParameterService webiaApplicationParameterService;

	@Autowired
	private TranscoServices transcoServices;

	@Autowired
	private LiabilityAgentService agentService;

	/**
	 * Env. variable
	 */
	@Value("${editique.xml.path}")
	private String filePath;

	@Override
	public DocumentGenerationResponse generateFile(SecurityContext context, EditingRequest request) {
		DocumentGenerationResponse documentGenerationResponse = new DocumentGenerationResponse();

		FileGenerationRequest fileGenerationRequest = new FileGenerationRequest();
		fileGenerationRequest.setEditingRequest(request);
		FileGenerationResponse fileGenerationResponse = restClientUtils.post(GENERATE_FILE, fileGenerationRequest, FileGenerationResponse.class);

		documentGenerationResponse.setRequest(fileGenerationResponse.getEditingRequest());
		return documentGenerationResponse;
	}

	@Override
	public Document generateDocument(Header header, CoverLetter coverLetter, List<MailingAddress> mailingAddresses) {
		Document document = new Document();
		document.setCoverLetter(coverLetter);
		document.setDocumentType(header.getDocumentType());
		document.setHeader(header);
		document.setMailingAddresses(mailingAddresses);
		return document;
	}

	@Override
	public Header generateHeader(EditingUser creationUser, DocumentType documentType, String language, String county, UUID docUid) {
		Header header = createBasicHeader(documentType, language, county, docUid);
		header.setUser(userMapper.asUser(creationUser));
		return header;
	}

	@Override
	public Header generateHeader(String userMail, DocumentType documentType, String language, String county, UUID docUid) {
		Header header = createBasicHeader(documentType, language, county, docUid);
		header.setUser(new User());
		header.getUser().setEmail(userMail);
		return header;
	}

	@Override
	public Header generateHeader(User user, DocumentType documentType, String language, String country, UUID docUid) {
		Header header = createBasicHeader(documentType, language, country, docUid);
		header.setUser(user);
		return header;
	}

	private Header createBasicHeader(DocumentType documentType, String language, String county, UUID docUid) {
		Header header = new Header();
		header.setDocumentType(documentType.name());
		header.setGenerationDate(new Date());
		// header.setLanguage("FR");
		header.setLanguage(StringUtils.isNotBlank(language) ? language : "FR");
		header.setRegion(county);
		header.setUniqueDocumentID(docUid == null ? "TST" : docUid.toString()); // TODO
		return header;
	}

	@Override
	public ContractType getContractType(ProductDTO productDTO) {
		Collection<String> capiProducts = webiaApplicationParameterService.getApplicationParameters(PRODUCT_CAPI);
		return capiProducts.contains(productDTO.getPrdId()) ? ContractType.CAPITALIZATION : ContractType.LIFE;
	}

	@Override
	public String getTransco(TranscoType type, String odsCode) {
		SearchTranscoRequest transcoRequest = new SearchTranscoRequest();
		transcoRequest.setType(type.name());
		transcoRequest.setOdsCode(odsCode);
		SearchTranscoResponse response = transcoServices.searchTransco(transcoRequest);
		if (response.getTransco() != null)
			return response.getTransco().getScripturaCode();
		else
			return "";
	}

	@Override
	public EditingRequest updateEditingRequest(EditingRequest editingRequest) {
		UpdateEditingRequestRequest updateEditingRequestRequest = new UpdateEditingRequestRequest();
		updateEditingRequestRequest.setRequest(editingRequest);
		UpdateEditingRequestResponse updateEditingRequestResponse = restClientUtils.post(UPDATE_EDITING_REQUEST, updateEditingRequestRequest, UpdateEditingRequestResponse.class);
		return updateEditingRequestResponse.getRequest();
	}

	@Override
	public String saveXmlDocuments(Documents mandate, String path) {
		try {
			JAXBContext jc = JAXBContext.newInstance("lu.wealins.editing.common.webia");
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File(path);
			marshaller.marshal(mandate, file);

			return file.getAbsolutePath();

		} catch (JAXBException e) {
			throw new IllegalStateException("Cannot save " + path + ".", e);
		}
	}

	@Override
	public String saveXmlDocument(lu.wealins.editing.common.webia.sepa.Document mandate, String path) {
		try {
			JAXBContext jc = JAXBContext.newInstance("lu.wealins.editing.common.webia.sepa");
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File(path);
			marshaller.marshal(mandate, file);

			return file.getAbsolutePath();

		} catch (JAXBException e) {
			throw new IllegalStateException("Cannot save " + path + ".", e);
		}
	}

	@Override
	public String saveCsvDocument(String path, List<?>datas) throws Exception {
		Writer writer  = new FileWriter(path.toString());
	    StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
	       .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
	       .build();
	    sbc.write(datas);
	    writer.close();
	    return path;
	}
	
	@Override
	public boolean isSpecificRiskActive(String alternativeFunds) {
		return AlternativeFundType.valueOf(alternativeFunds).hasSpecificRisk();
	}

	@Override
	public AgentContactLiteDTO getAgentContact(AgentDTO agent) {
		for (AgentContactLiteDTO agentContact : agent.getAgentContacts()) {
			if (AGENT_CONTACT_FUNCTION.equals(agentContact.getContactFunction())) {
				if (agentContact.getStatus() != null && ACTIVE_1.equals(agentContact.getStatus().trim())) {
					return agentContact;
				}
			}
		}
		return null;
	}

	@Override
	public String getLanguage(AgentContactLiteDTO agentContact) {

		if (agentContact != null && agentContact.getContact() != null && agentContact.getContact().getTranscodedDocumentationLanguage() != null) {
			return agentContact.getContact().getTranscodedDocumentationLanguage();
		}

		return "FR";
	}

	@Override
	public AgentContactLiteDTO getAgentContact(FundDTO fund, PolicyDTO policyDTO, AgentDTO mailToAgent) {
		AgentContactLiteDTO contactPerson = new AgentContactLiteDTO();

		if (mailToAgent != null) {

			Boolean centralizedCommunication = mailToAgent.getCentralizedCommunication();

			AgentLightDTO contactAgent = null;

			AgentContactLiteDTO agentContact = this.getAgentContact(mailToAgent);
			if (Boolean.TRUE.equals(centralizedCommunication)) {
				if (agentContact != null) {
					contactAgent = agentContact.getContact();
				}
			} else {
				if (AgentCategory.BROKER.getCategory().equals(mailToAgent.getCategory())) {
					PolicyAgentShareDTO brokerContact = policyDTO.getBrokerContact();
					if (brokerContact != null) {
						contactAgent = brokerContact.getAgent();
					}
				} else {
					if (fund != null) {
						String salesRep = fund.getSalesRep();
						contactAgent = agentService.getAgent(salesRep);
					}
				}

			}

			if (contactAgent == null) {
				if (agentContact != null) {
					contactAgent = agentContact.getContact();
				}
			}
			if (contactAgent == null) {
				contactAgent = mailToAgent;
			}

			if (contactAgent.getBlockAddress() == null) {
				contactAgent.setBlockAddress(mailToAgent.getBlockAddress());
			}
			if (contactAgent.getEmail() == null) {
				contactAgent.setEmail(mailToAgent.getEmail());
			}

			String language = null;
			if (contactAgent.getDocumentationLanguage() != null) {
				language = this.getTransco(TranscoType.LANGUAGE, contactAgent.getDocumentationLanguage().toString());
			}
			if (language == null && mailToAgent.getDocumentationLanguage() != null) {
				language = this.getTransco(TranscoType.LANGUAGE, mailToAgent.getDocumentationLanguage().toString());
			}
			if (language == null) {
				language = "FR";
			}
			contactAgent.setTranscodedDocumentationLanguage(language);

			contactPerson.setContact(contactAgent);
			contactPerson.setAgentId(mailToAgent.getAgtId());
		}
		return contactPerson;
	}

	@Override
	public String getMailFromTrigram(String trigram) {
		return new StringBuilder()
				.append(trigram.toLowerCase())
				.append(EMAIL_SUFFIX)
				.toString();
	}

	@Override
	public User getUserFromTrigram(String trigram) {
		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
		queryParams.putSingle("trigram", trigram);
		UserDTO userDTO = restClientUtils.get("liability/users/", "getByTrigram", queryParams, UserDTO.class);

		User user = new User();
		user.setEmail(getMailFromTrigram(trigram));
		if (userDTO != null) {
			// try to extract first name and last name ( in the Lissia DB, it's in the same field)
			Collection<String> nameParts = Arrays.asList(userDTO.getName().split("\\s+"));
			user.setFirstname(nameParts.stream().findFirst().get());
			user.setName(nameParts.stream().skip(1).collect(Collectors.joining(" ")));
			user.setFax(userDTO.getFaxExtn());
			user.setUserTrigram(trigram);
		}

		return user;
	}

}
