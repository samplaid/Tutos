package lu.wealins.batch.injection.acl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import lu.wealins.rest.model.ACLInjectionControlRequest;
import lu.wealins.rest.model.acl.ACLsLoadRequest;
import lu.wealins.rest.model.acl.ACLsLoadResponse;
import lu.wealins.rest.model.acl.AssignACLsToGroupRequest;
import lu.wealins.rest.model.acl.AssignACLsToGroupResponse;
import lu.wealins.rest.model.acl.AssignACLsToUserRequest;
import lu.wealins.rest.model.acl.AssignACLsToUserResponse;
import lu.wealins.rest.model.acl.CreateACLRequest;
import lu.wealins.rest.model.acl.CreateACLResponse;
import lu.wealins.rest.model.acl.DeleteACLRequest;
import lu.wealins.rest.model.acl.DeleteACLResponse;
import lu.wealins.rest.model.acl.LoadGroupRequest;
import lu.wealins.rest.model.acl.LoadGroupResponse;
import lu.wealins.rest.model.acl.SearchUserRequest;
import lu.wealins.rest.model.acl.SearchUserResponse;
import lu.wealins.rest.model.acl.UpdateACLRequest;
import lu.wealins.rest.model.acl.UpdateACLResponse;
import lu.wealins.rest.model.acl.common.ACL;
import lu.wealins.rest.model.acl.common.enums.DataType;
import lu.wealins.rest.model.acl.common.enums.KeyType;
import lu.wealins.rest.model.acl.common.enums.Orientation;
import lu.wealins.rest.model.ods.BrokerSearchResponse;
import lu.wealins.rest.model.ods.IdRequest;
import lu.wealins.rest.model.ods.PolicySearchRequest;
import lu.wealins.rest.model.ods.PolicySearchResponse;
import lu.wealins.rest.model.ods.common.Broker;
import lu.wealins.rest.model.ods.common.Policy;
import lu.wealins.utils.CsvFileWriter;
import lu.wealins.utils.KeycloakUtils;

/**
 * @author xqt5q
 *
 */
@Component
public class ACLInjector {

	@Value("${piaRootContextURL}")
	private String piaRootContextURL;

	@Value("${aclInjectionSuccessFile}")
	private String aclInjectionSuccessFile;

	@Value("${aclInjectionErrorFile}")
	private String aclInjectionErrorFile;

	@Value("${pNoirGroupId}")
	private Long pNoirGroupId;

	@Autowired
	KeycloakUtils keycloackUtils;

	private static final String POLICY_SEARCH = "searchPolicy";
	private static final String BROKER_SEARCH = "searchBroker";

	private static final String ACL_USER_SEARCH = "userSearch";
	private static final String ACLS_FOR_USER_SEARCH = "aclsByLogin";

	private static final String CREATE_ACL = "createACL";
	private static final String ASSIGN_USER = "assignUserToACL";
	private static final String ASSIGN_GROUP = "assignGroupToACL";
	private static final String LOAD_GROUP = "loadGroup";
	private static final String DELETE_ACL = "deleteACL";
	private static final String UPDATE_ACL = "updateACL";

	private static final String BIL_IDENTIFIER = "E";

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String COMMA_DELIMITER = ";";
	private static String OUTPUT_FILE_NAME = "acl_injection";
	private static String OUTPUT_FILE_EXTENSION = ".csv";

	private CsvFileWriter successFileWriter;
	private CsvFileWriter failureFileWriter;
	private String successFilePath;
	private String failureFilePath;

	private HashMap<String, List<String>> loginPoliciesAssociationMap = new HashMap<String, List<String>>();
	private List<Policy> filePolicies;
	private List<Long> aclsPNoir;

	private Log logger = LogFactory.getLog(ACLInjector.class);

	/**
	 * scan each line of the file and execute the treatement
	 * 
	 * @param input
	 */
	public File inject(File input) {
		logger.info("Parsing : " + input.getAbsolutePath());

		aclsPNoir = new ArrayList<>();
		filePolicies = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(input.getAbsolutePath()))) {

			String fileName = OUTPUT_FILE_NAME + getCurrentDate() + OUTPUT_FILE_EXTENSION;
			initFiles(fileName);

			stream.forEach((line) -> {
				try {
					buildAssociationsMap(line);
				} catch (Exception e) {
					failureFileWriter.append("Scanning" + COMMA_DELIMITER + COMMA_DELIMITER + COMMA_DELIMITER + "Error when scanning line " + line + NEW_LINE_SEPARATOR);
					logger.error("Erreur parsing line " + line + ". Exception: " + e);
				}
			});

			// Delete the ACLs of the "P Noir" group
			cleanPNoirGroup();

			for (Entry<String, List<String>> entry : loginPoliciesAssociationMap.entrySet()) {
				if (checkIfUserExistsInODS(entry.getKey())) {
					if (checkIfUserExistsInACL(entry.getKey())) {
						List<ACL> aclsList = getPolicyACLsForLogin(entry.getKey());
						List<ACL> policiesDenied = extractPoliciesFromACL(aclsList, Orientation.DENY);

						for (ACL acl : aclsList) {
							boolean isInFile = false;
							for (String policy : entry.getValue()) {
								if (acl.getKey().equals(policy)) {
									isInFile = true;
									if (policiesDenied.contains(acl)) {
										acl.setOrientation(lu.wealins.rest.model.acl.common.enums.Orientation.ALLOW);
										updateACL(acl);
										successFileWriter.append("UPDATE ACL" + COMMA_DELIMITER + acl.getKey() + COMMA_DELIMITER + entry.getKey() + COMMA_DELIMITER + NEW_LINE_SEPARATOR);
										logger.info("Update of ACL " + acl.getDataType() + " : " + acl.getKey() + " for User : " + entry.getKey());
									}
								}
							}
							if (!isInFile) {
								deleteACL(acl);
								successFileWriter.append("DELETE ACL" + COMMA_DELIMITER + acl.getKey() + COMMA_DELIMITER + entry.getKey() + COMMA_DELIMITER + NEW_LINE_SEPARATOR);
								logger.warn("Delete of ACL " + acl.getDataType() + " : " + acl.getKey() + " for User : " + entry.getKey());
							}

						}

						for (String policy : entry.getValue()) {
							boolean existsInACL = false;
							for (ACL acl : aclsList) {
								if (acl.getKey().equals(policy)) {
									existsInACL = true;
								}
							}

							if (!existsInACL) {
								ACL aclCreated = createACL(policy);
								assignUserToACL(entry.getKey(), aclCreated.getId());
								successFileWriter.append("CREATE ACL" + COMMA_DELIMITER + aclCreated.getKey() + COMMA_DELIMITER + entry.getKey() + COMMA_DELIMITER + NEW_LINE_SEPARATOR);

							}

						}

					} else {
						// User does not exist in useraccess database
						logger.warn("The user " + entry.getKey() + " does not exist in ACL");
						failureFileWriter.append("FIND USER IN ACL" + COMMA_DELIMITER + COMMA_DELIMITER + entry.getKey() + COMMA_DELIMITER + "The user does not exist in ACL" + NEW_LINE_SEPARATOR);

						// Create the PNoir ACL
						String reason = "The user " + entry.getKey() + " does not exist as broker in useraccess database";
						for (String policy : entry.getValue()) {
							aclsPNoir.add(createACLPNoir(policy, reason).getId());
						}
					}
				} else {
					// User does not exist in ODS database
					logger.warn("The user " + entry.getKey() + " does not exist in ODS");
					failureFileWriter.append("FIND USER IN ODS" + COMMA_DELIMITER + COMMA_DELIMITER + entry.getKey() + COMMA_DELIMITER + "The user does not exist in ODS" + NEW_LINE_SEPARATOR);

					// Create the PNoir ACL
					String reason = "The user " + entry.getKey() + " does not exist as broker in ODS database";
					for (String policy : entry.getValue()) {
						aclsPNoir.add(createACLPNoir(policy, reason).getId());
					}
				}
			}

			// Policy on ODS with an external reference but not on the file
			createPNoirForPoliciesNotOnTheFile();

			// Assign the "P Noir" acls to the "P Noir" group
			if (!CollectionUtils.isEmpty(aclsPNoir)) {
				assignACLToGroup(pNoirGroupId, aclsPNoir);
			}

		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				successFileWriter.close();
				failureFileWriter.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

		logger.info("End of Parsing");

		return input;
	}

	/**
	 * Create the ACL to assign to PNoir for policies with an external reference on the ODS database but not present on the injected file
	 */
	private void createPNoirForPoliciesNotOnTheFile() {
		// Search all the ODS policy with an initial contract (external reference)
		PolicySearchRequest policySearchRequest = new PolicySearchRequest();
		policySearchRequest.setInitialContract("%");
		policySearchRequest.setWithClosed(true);
		PolicySearchResponse policySearchResponse = post(piaRootContextURL + POLICY_SEARCH, policySearchRequest, PolicySearchResponse.class);
		List<Policy> policiesWithInitialContract = policySearchResponse.getPolicies();

		// Remove the policies treated on the batch
		policiesWithInitialContract.removeAll(filePolicies);

		// Check if the broker is from the BIL
		boolean isBilBroker = false;
		for (Policy policyNotInFile : policiesWithInitialContract) {
			isBilBroker = false;
			for (Broker broker : policyNotInFile.getBrokers()) {
				if (broker.getCode().startsWith(BIL_IDENTIFIER)) {
					isBilBroker = true;
				}
			}
			if (isBilBroker) {
				// Create the PNoir ACL
				aclsPNoir.add(createACLPNoir(policyNotInFile.getCode(), "The policy " + policyNotInFile.getCode() + " is on the ODS database but not on the file").getId());
			}
		}
	}

	/**
	 * Clear all the ACL of the PNoir group
	 * 
	 */
	private DeleteACLResponse cleanPNoirGroup() {
		LoadGroupRequest loadGroupRequest = new LoadGroupRequest();
		loadGroupRequest.setId(pNoirGroupId);
		LoadGroupResponse loadGroupResponse = post(piaRootContextURL + LOAD_GROUP, loadGroupRequest, LoadGroupResponse.class);

		DeleteACLRequest deleteACLRequest = new DeleteACLRequest();
		deleteACLRequest.setAcls(loadGroupResponse.getAcls());
		return post(piaRootContextURL + DELETE_ACL, deleteACLRequest, DeleteACLResponse.class);
	}

	/**
	 * build association map between users and all concerned policies
	 * 
	 * @param line
	 */
	public void buildAssociationsMap(String line) {
		ACLInjectionControlRequest aclInjectionRequest = buildRequestWithLine(line);

		PolicySearchRequest policySearchRequest = new PolicySearchRequest();
		String contractId = aclInjectionRequest.getContractId();
		policySearchRequest.setInitialContract(contractId);
		PolicySearchResponse policySearchResponse = post(piaRootContextURL + POLICY_SEARCH, policySearchRequest, PolicySearchResponse.class);
		if (policySearchResponse != null && !policySearchResponse.getPolicies().isEmpty()) {
			addPoliciesForLogin(aclInjectionRequest.getLogin(), policySearchResponse.getPolicies());
		} else {
			// Policy not found in ODS
			logger.warn("No policy found with ContractID : " + contractId + " in ODS");
			failureFileWriter.append("FIND POLICY IN ODS" + COMMA_DELIMITER + contractId + COMMA_DELIMITER + COMMA_DELIMITER + "No policy found with this contractID in ODS"
					+ NEW_LINE_SEPARATOR);

			// Create the PNoir ACL
			String reason = "The policy with the external reference " + contractId + " does not exist in ODS database";
			aclsPNoir.add(createACLPNoir("Ext. Ref. : " + contractId, reason).getId());
		}
	}

	/**
	 * Build injection request function of a line of the file
	 * 
	 * @param line
	 */
	public ACLInjectionControlRequest buildRequestWithLine(String line) {
		ACLInjectionControlRequest request = new ACLInjectionControlRequest();

		String contractId = line.substring(0, 8);
		String login = line.substring(8, 12);
		request.setContractId(contractId);
		request.setLogin(BIL_IDENTIFIER + login);
		return request;
	}

	/**
	 * Associate policies for a login
	 * 
	 * @param login
	 * @param policiesList
	 */
	public void addPoliciesForLogin(String login, List<Policy> policiesList) {
		List<String> policies = loginPoliciesAssociationMap.get(login);
		filePolicies.addAll(policiesList);
		if (policies != null) {
			for (Policy policy : policiesList) {
				if (!policies.contains(policy.getCode()))
					loginPoliciesAssociationMap.get(login).add(policy.getCode());
			}
		} else {
			loginPoliciesAssociationMap.put(login, new ArrayList<String>());
			for (Policy policy : policiesList)
				loginPoliciesAssociationMap.get(login).add(policy.getCode());
		}
	}

	/**
	 * check if the user exists in ODS
	 * 
	 * @param login
	 */
	public boolean checkIfUserExistsInODS(String login) {
		IdRequest request = new IdRequest();
		request.setId(login);
		BrokerSearchResponse response = post(piaRootContextURL + BROKER_SEARCH, request, BrokerSearchResponse.class);

		return (response.getBrokers() != null && response.getBrokers().size() > 0);
	}

	/**
	 * check if the user exists in ACL
	 * 
	 * @param login
	 */
	public boolean checkIfUserExistsInACL(String login) {
		SearchUserRequest request = new SearchUserRequest();
		request.setUser(login);
		SearchUserResponse response = post(piaRootContextURL + ACL_USER_SEARCH, request, SearchUserResponse.class);

		return (response.getUsers() != null && response.getUsers().size() > 0);
	}

	/**
	 * get all the policy ACL for a login
	 * 
	 * @param login
	 */
	public List<ACL> getPolicyACLsForLogin(String login) {
		ACLsLoadRequest request = new ACLsLoadRequest();
		request.setLogin(login);
		request.setUserId(1L);
		ACLsLoadResponse response = post(piaRootContextURL + ACLS_FOR_USER_SEARCH, request, ACLsLoadResponse.class);
		return new ArrayList<>(response.getAcls());
	}

	/**
	 * extract policies from a list of ACLs
	 * 
	 * @param acls
	 * @param orientation
	 */
	public List<ACL> extractPoliciesFromACL(List<ACL> acls, Orientation orientation) {
		List<ACL> policyACL = new ArrayList<ACL>();
		for (ACL acl : acls) {
			if (acl.getDataType().equals(DataType.POLICY) && acl.getOperator().equals("EQUAL") && acl.getOrientation().equals(orientation)) {
				policyACL.add(acl);
			}
		}

		return policyACL;
	}

	/**
	 * create ACL
	 * 
	 * @param policy
	 */
	public ACL createACL(String policy) {
		CreateACLRequest request = new CreateACLRequest();
		request.setDataType(DataType.POLICY);
		request.setKeyType(KeyType.POLICY);
		request.setKeyValue(policy);
		request.setOperator("EQUAL");
		request.setOrientation(lu.wealins.rest.model.acl.common.enums.Orientation.ALLOW);
		CreateACLResponse response = post(piaRootContextURL + CREATE_ACL, request, CreateACLResponse.class);
		return response.getAcl();
	}

	/**
	 * create ACL
	 * 
	 * @param policy
	 */
	public ACL createACLPNoir(String policy, String reason) {
		CreateACLRequest request = new CreateACLRequest();
		request.setDataType(DataType.POLICY);
		request.setKeyType(KeyType.POLICY);
		request.setKeyValue(policy);
		request.setOperator("EQUAL");
		request.setReason(reason);
		request.setCreationUser("SYSTEM");
		request.setOrientation(lu.wealins.rest.model.acl.common.enums.Orientation.DENY);
		CreateACLResponse response = post(piaRootContextURL + CREATE_ACL, request, CreateACLResponse.class);
		return response.getAcl();
	}

	/**
	 * Delete ACL
	 * 
	 * @param acl
	 */
	public DeleteACLResponse deleteACL(ACL acl) {
		DeleteACLRequest request = new DeleteACLRequest();
		List<ACL> acls = new ArrayList<>();
		acls.add(acl);
		request.setAcls(acls);
		DeleteACLResponse response = post(piaRootContextURL + DELETE_ACL, request, DeleteACLResponse.class);
		return response;
	}

	/**
	 * Update ACL
	 * 
	 * @param acl
	 */
	public ACL updateACL(ACL acl) {
		UpdateACLRequest request = new UpdateACLRequest();
		request.setAcl(acl);
		UpdateACLResponse response = post(piaRootContextURL + UPDATE_ACL, request, UpdateACLResponse.class);
		return response.getAcl();
	}

	/**
	 * assign ACL to user
	 * 
	 * @param login
	 * @param aclID
	 */
	public void assignUserToACL(String login, Long aclID) {
		AssignACLsToUserRequest request = new AssignACLsToUserRequest();
		request.setUserLogin(login);
		List<Long> list = new ArrayList<Long>();
		list.add(aclID);
		request.setAclIds(list);
		post(piaRootContextURL + ASSIGN_USER, request, AssignACLsToUserResponse.class);
	}

	/**
	 * assign ACL to group
	 * 
	 * @param groupId
	 * @param aclIDs
	 */
	public void assignACLToGroup(Long groupId, List<Long> aclIDs) {
		AssignACLsToGroupRequest request = new AssignACLsToGroupRequest();
		request.setGroupId(groupId);
		request.setAclIds(aclIDs);
		post(piaRootContextURL + ASSIGN_GROUP, request, AssignACLsToGroupResponse.class);
	}

	/**
	 * post method
	 * 
	 * @param url
	 * @param request
	 * @param responseType
	 */
	public <Response, Request> Response post(String url, Request request, Class<Response> responseType) {

		AccessTokenResponse tokenResponse = keycloackUtils.getAccessToken();
		String token = tokenResponse.getToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Request> entity = new HttpEntity<Request>(request, headers);
		RestTemplate template = new RestTemplate();

		return template.postForObject(url, entity, responseType);
	}

	/**
	 * init success and error writer
	 * 
	 * @param fileName
	 */
	private void initFiles(String fileName) {
		File successFile = new File(aclInjectionSuccessFile, fileName);
		successFilePath = successFile.getAbsolutePath();
		successFileWriter = new CsvFileWriter(successFilePath);
		File failureFile = new File(aclInjectionErrorFile, fileName);
		failureFilePath = failureFile.getAbsolutePath();
		failureFileWriter = new CsvFileWriter(failureFilePath);

		successFileWriter.append("Operation" + COMMA_DELIMITER + "Policy" + COMMA_DELIMITER + "User" + COMMA_DELIMITER + "Reason" + NEW_LINE_SEPARATOR);
		failureFileWriter.append("Operation" + COMMA_DELIMITER + "Policy" + COMMA_DELIMITER + "User" + COMMA_DELIMITER + "Reason" + NEW_LINE_SEPARATOR);
	}

	/**
	 * get the current date
	 * 
	 */
	private String getCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return sdf.format(date);
	}

}
