package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentDTO extends AgentLightDTO {

	private String crmRefererence;
	private Integer paymentMethod;
	private String crmStatus;
	private String branch;
	private int type;
	private Integer limitCode;
	private String limitCurrency;
	private BigDecimal limitAmount;
	private Date appointmentDate;
	private Integer lastStatementNo;
	private Date sdateLastStmtRun;
	private Date dateLastStmtRunTo;
	private Integer paymentCode;
	private Integer paymentFrequency;
	private String paymentCcy;
	private String channel;
	private Date effectiveDate;
	private String location;
	private String servicingAgent;
	private Date dateOfTermination;
	private Integer terminationBasis;
	private String terminationReason;
	private String banding;
	private String nextCategory;
	private Date nextCategoryDate;
	private Date lastCategoryChange;
	private String desiredPaymentCcy;
	private Integer paymentCommission;
	private Integer vat;
	private Integer finFeesStatement;
	private Integer paymentSuperiorAgent;
	private String paymentAccountBic;
	private Integer desiredReport;
	private Boolean centralizedCommunication;
	private Boolean statementByEmail;

	@JsonIgnore
	private String createdProcess;
	@JsonIgnore
	private String modifyProcess;
	@JsonIgnore
	private String createdBy;
	@JsonIgnore
	private Date createdDate;
	@JsonIgnore
	private Date createdTime;
	@JsonIgnore
	private String modifyBy;
	@JsonIgnore
	private Date modifyDate;
	@JsonIgnore
	private Date modifyTime;

	private AgentHierarchyDTO masterBroker;

	private Collection<AssetManagerStrategyDTO> assetManagerStrategies = new HashSet<>(0);
	private Collection<AgentBankAccountDTO> bankAccounts = new HashSet<>(0);
	private Collection<AgentContactLiteDTO> agentContacts = new HashSet<>(0);
	private Collection<AgentHierarchyDTO> agentHierarchies = new HashSet<>(0);
	public String getCrmRefererence() {
		return crmRefererence;
	}
	public void setCrmRefererence(String crmRefererence) {
		this.crmRefererence = crmRefererence;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCrmStatus() {
		return crmStatus;
	}
	public void setCrmStatus(String crmStatus) {
		this.crmStatus = crmStatus;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getLimitCode() {
		return limitCode;
	}
	public void setLimitCode(Integer limitCode) {
		this.limitCode = limitCode;
	}
	public String getLimitCurrency() {
		return limitCurrency;
	}
	public void setLimitCurrency(String limitCurrency) {
		this.limitCurrency = limitCurrency;
	}
	public BigDecimal getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public Integer getLastStatementNo() {
		return lastStatementNo;
	}
	public void setLastStatementNo(Integer lastStatementNo) {
		this.lastStatementNo = lastStatementNo;
	}
	public Date getSdateLastStmtRun() {
		return sdateLastStmtRun;
	}
	public void setSdateLastStmtRun(Date sdateLastStmtRun) {
		this.sdateLastStmtRun = sdateLastStmtRun;
	}
	public Date getDateLastStmtRunTo() {
		return dateLastStmtRunTo;
	}
	public void setDateLastStmtRunTo(Date dateLastStmtRunTo) {
		this.dateLastStmtRunTo = dateLastStmtRunTo;
	}
	public Integer getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(Integer paymentCode) {
		this.paymentCode = paymentCode;
	}
	public Integer getPaymentFrequency() {
		return paymentFrequency;
	}
	public void setPaymentFrequency(Integer paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}
	public String getPaymentCcy() {
		return paymentCcy;
	}
	public void setPaymentCcy(String paymentCcy) {
		this.paymentCcy = paymentCcy;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getServicingAgent() {
		return servicingAgent;
	}
	public void setServicingAgent(String servicingAgent) {
		this.servicingAgent = servicingAgent;
	}
	public Date getDateOfTermination() {
		return dateOfTermination;
	}
	public void setDateOfTermination(Date dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}
	public Integer getTerminationBasis() {
		return terminationBasis;
	}
	public void setTerminationBasis(Integer terminationBasis) {
		this.terminationBasis = terminationBasis;
	}
	public String getTerminationReason() {
		return terminationReason;
	}
	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}
	public String getBanding() {
		return banding;
	}
	public void setBanding(String banding) {
		this.banding = banding;
	}
	public String getNextCategory() {
		return nextCategory;
	}
	public void setNextCategory(String nextCategory) {
		this.nextCategory = nextCategory;
	}
	public Date getNextCategoryDate() {
		return nextCategoryDate;
	}
	public void setNextCategoryDate(Date nextCategoryDate) {
		this.nextCategoryDate = nextCategoryDate;
	}
	public Date getLastCategoryChange() {
		return lastCategoryChange;
	}
	public void setLastCategoryChange(Date lastCategoryChange) {
		this.lastCategoryChange = lastCategoryChange;
	}
	public String getDesiredPaymentCcy() {
		return desiredPaymentCcy;
	}
	public void setDesiredPaymentCcy(String desiredPaymentCcy) {
		this.desiredPaymentCcy = desiredPaymentCcy;
	}
	public Integer getPaymentCommission() {
		return paymentCommission;
	}
	public void setPaymentCommission(Integer paymentCommission) {
		this.paymentCommission = paymentCommission;
	}
	public Integer getVat() {
		return vat;
	}
	public void setVat(Integer vat) {
		this.vat = vat;
	}
	public Integer getFinFeesStatement() {
		return finFeesStatement;
	}
	public void setFinFeesStatement(Integer finFeesStatement) {
		this.finFeesStatement = finFeesStatement;
	}
	public Integer getPaymentSuperiorAgent() {
		return paymentSuperiorAgent;
	}
	public void setPaymentSuperiorAgent(Integer paymentSuperiorAgent) {
		this.paymentSuperiorAgent = paymentSuperiorAgent;
	}
	public String getPaymentAccountBic() {
		return paymentAccountBic;
	}
	public void setPaymentAccountBic(String paymentAccountBic) {
		this.paymentAccountBic = paymentAccountBic;
	}

	public Integer getDesiredReport() {
		return desiredReport;
	}

	public void setDesiredReport(Integer desiredReport) {
		this.desiredReport = desiredReport;
	}
	public Boolean getCentralizedCommunication() {
		return centralizedCommunication;
	}
	public void setCentralizedCommunication(Boolean centralizedCommunication) {
		this.centralizedCommunication = centralizedCommunication;
	}

	public Boolean getStatementByEmail() {
		return statementByEmail;
	}

	public void setStatementByEmail(Boolean statementByEmail) {
		this.statementByEmail = statementByEmail;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public AgentHierarchyDTO getMasterBroker() {
		return masterBroker;
	}

	public void setMasterBroker(AgentHierarchyDTO masterBroker) {
		this.masterBroker = masterBroker;
	}

	public Collection<AssetManagerStrategyDTO> getAssetManagerStrategies() {
		return assetManagerStrategies;
	}

	public void setAssetManagerStrategies(Collection<AssetManagerStrategyDTO> assetManagerStrategies) {
		this.assetManagerStrategies = assetManagerStrategies;
	}

	public Collection<AgentBankAccountDTO> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Collection<AgentBankAccountDTO> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public Collection<AgentContactLiteDTO> getAgentContacts() {
		return agentContacts;
	}

	public void setAgentContacts(Collection<AgentContactLiteDTO> agentContacts) {
		this.agentContacts = agentContacts;
	}

	public Collection<AgentHierarchyDTO> getAgentHierarchies() {
		return agentHierarchies;
	}

	public void setAgentHierarchies(Collection<AgentHierarchyDTO> agentHierarchies) {
		this.agentHierarchies = agentHierarchies;
	}


}
