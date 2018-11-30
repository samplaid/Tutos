package lu.wealins.rest.model.editing;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.editing.services.enums.DocumentType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EditingRequest {

	private Long id;
	private String product;
	private String policy;
	private String fund;
	private String client;
	private String agent;
	private String transaction;
	private String statement;
	private String transfer;
	private Date eventDate;
	private DocumentType documentType;
	private EditingUser creationUser;
	private EditingRequestStatus status;
	private String inputStreamPath;
	private Date creationDate;
	private Boolean signNeeded;
	private String outputChoice;
	private String outputStreamPath;
	private String gedClass;
	private String gedDocumentId;
	private String gedVersion;
	private Long transactionTax;
	private Long workflowItemId;
	private Boolean simulation;
	private Boolean synchronous;
	private String transferIds;
	private String fundTransactionFormIds;
	private Boolean frenchTaxable;

	public Boolean getFrenchTaxable() {
		return frenchTaxable;
	}

	public void setFrenchTaxable(Boolean frenchTaxable) {
		this.frenchTaxable = frenchTaxable;
	}

	public String getFundTransactionFormIds() {
		return fundTransactionFormIds;
	}

	public void setFundTransactionFormIds(String fundTransactionFormIds) {
		this.fundTransactionFormIds = fundTransactionFormIds;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public EditingUser getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(EditingUser creationUser) {
		this.creationUser = creationUser;
	}

	public EditingRequestStatus getStatus() {
		return status;
	}

	public void setStatus(EditingRequestStatus status) {
		this.status = status;
	}

	public String getInputStreamPath() {
		return inputStreamPath;
	}

	public void setInputStreamPath(String inputStreamPath) {
		this.inputStreamPath = inputStreamPath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getSignNeeded() {
		return signNeeded;
	}

	public void setSignNeeded(Boolean signNeeded) {
		this.signNeeded = signNeeded;
	}

	public String getOutputChoice() {
		return outputChoice;
	}

	public void setOutputChoice(String outputChoice) {
		this.outputChoice = outputChoice;
	}

	public String getOutputStreamPath() {
		return outputStreamPath;
	}

	public void setOutputStreamPath(String outputStreamPath) {
		this.outputStreamPath = outputStreamPath;
	}

	public String getGedClass() {
		return gedClass;
	}

	public void setGedClass(String gedClass) {
		this.gedClass = gedClass;
	}

	public String getGedDocumentId() {
		return gedDocumentId;
	}

	public void setGedDocumentId(String gedDocumentId) {
		this.gedDocumentId = gedDocumentId;
	}

	public String getGedVersion() {
		return gedVersion;
	}

	public void setGedVersion(String gedVersion) {
		this.gedVersion = gedVersion;
	}

	public Long getTransactionTax() {
		return transactionTax;
	}

	public void setTransactionTax(Long transactionTax) {
		this.transactionTax = transactionTax;
	}

	public Long getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Long workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public Boolean getSimulation() {
		return simulation;
	}

	public void setSimulation(Boolean simulation) {
		this.simulation = simulation;
	}

	/**
	 * @return the synchronous
	 */
	public Boolean getSynchronous() {
		return synchronous;
	}

	/**
	 * @param synchronous the synchronous to set
	 */
	public void setSynchronous(Boolean synchronous) {
		this.synchronous = synchronous;
	}

	/**
	 * @return the transferIds
	 */
	public String getTransferIds() {
		return transferIds;
	}

	/**
	 * @param transferIds the transferIds to set
	 */
	public void setTransferIds(String transferIds) {
		this.transferIds = transferIds;
	}
}
