package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NamedNativeQueries({
		@NamedNativeQuery(name = "AgentEntity.findSalesManager", query = "SELECT a.* FROM AGENTS a INNER JOIN FUNDS f ON a.AGT_ID = f.SALES_REP WHERE f.FDS_ID = ?1", resultClass = AgentEntity.class)
})
@Entity
@Table(name = "AGENTS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "createdProcess", "modifyProcess", "createdBy", "createdDate", "createdTime", "modifyDate", "modifyTime", "modifyBy" })
public class AgentEntity implements Serializable {
	private static final long serialVersionUID = -8447529042271680492L;

	@Transient
	public static final int AGTID_LENGTH = 8;
	@Transient
	public static final int NAME_LENGTH = 40;

	private String agtId;
	private AgentCategoryEntity agentCategory;
	private String agentCategoryId;
	private AgentContractEntity agentContract;
	private String name;
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
	private Integer paymentMethod;
	private String paymentCcy;
	private CountryEntity country;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String town;
	private String county;
	private String postcode;
	private String fax;
	private String telephone;
	private String email;
	private String channel;
	private Date effectiveDate;
	private String location;
	@JsonIgnore
	private String createdProcess;
	@JsonIgnore
	private String modifyProcess;
	private int status;
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
	private String servicingAgent;
	private Date dateOfTermination;
	private Integer terminationBasis;
	private String terminationReason;
	private String banding;
	private String nextCategory;
	private Date nextCategoryDate;
	private Date lastCategoryChange;
	private String mobile;
	private Integer documentationLanguage;
	private String desiredPaymentCcy;
	private Integer paymentCommission;
	private Integer vat;
	private Integer finFeesStatement;
	private String crmRefererence;
	private String crmStatus;
	private Integer paymentSuperiorAgent;
	private String title;
	private String firstname;
	private String paymentAccountBic;
	private Integer desiredReport;
	private Boolean centralizedCommunication;
	private Boolean statementByEmail;

	@JsonIgnore
	private Set<AssetManagerStrategyEntity> assetManagerStrategies = new HashSet<>(0);
	@JsonIgnore
	private Set<AgentHistoryDetailEntity> agentHistoryDetails = new HashSet<>(0);
	@JsonIgnore
	private List<AgentHierarchyEntity> agentHierarchies = new ArrayList<>(0);
	@JsonIgnore
	private Set<AgentEventEntity> agentEvents = new HashSet<>(0);
	private Set<PolicyEntity> policies = new HashSet<>(0);
	private Set<GeneralNoteEntity> generalNotes = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyAgentShareEntity> policyAgentShares = new HashSet<>(0);
	private Set<AgentBankAccountEntity> bankAccounts = new HashSet<>(0);
	private Set<AgentContactEntity> agentContacts = new HashSet<>(0);

	/**
	 * @return the statementByEmail
	 */
	@Column(name = "STATEMENT_BY_EMAIL", nullable = true)
	public Boolean getStatementByEmail() {
		return statementByEmail;
	}

	/**
	 * @param statementByEmail the statementByEmail to set
	 */
	public void setStatementByEmail(Boolean statementByEmail) {
		this.statementByEmail = statementByEmail;
	}

	@Column(name = "DESIRED_REPORT", nullable = false)
	public Integer getDesiredReport() {
		return desiredReport;
	}

	public void setDesiredReport(Integer desiredReport) {
		this.desiredReport = desiredReport;
	}

	@Id
	@Column(name = "AGT_ID", unique = true, nullable = false, length = 8)
	public String getAgtId() {
		return this.agtId;
	}

	public void setAgtId(String agtId) {
		this.agtId = agtId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public AgentCategoryEntity getAgentCategory() {
		return this.agentCategory;
	}

	public void setAgentCategory(AgentCategoryEntity agentCategory) {
		this.agentCategory = agentCategory;
	}

	@Column(name = "CATEGORY")
	public String getAgentCategoryId() {
		return this.agentCategoryId;
	}

	public void setAgentCategoryId(String agentCategoryId) {
		this.agentCategoryId = agentCategoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_NO")
	@NotFound(action = NotFoundAction.IGNORE)
	public AgentContractEntity getAgentContract() {
		return this.agentContract;
	}

	public void setAgentContract(AgentContractEntity agentContract) {
		this.agentContract = agentContract;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "BRANCH", length = 10)
	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Column(name = "TYPE", nullable = false)
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "LIMIT_CODE")
	public Integer getLimitCode() {
		return this.limitCode;
	}

	public void setLimitCode(Integer limitCode) {
		this.limitCode = limitCode;
	}

	@Column(name = "LIMIT_CURRENCY", length = 4)
	public String getLimitCurrency() {
		return this.limitCurrency;
	}

	public void setLimitCurrency(String limitCurrency) {
		this.limitCurrency = limitCurrency;
	}

	@Column(name = "LIMIT_AMOUNT", precision = 15)
	public BigDecimal getLimitAmount() {
		return this.limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPOINTMENT_DATE", length = 23)
	public Date getAppointmentDate() {
		return this.appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	@Column(name = "LAST_STATEMENT_NO")
	public Integer getLastStatementNo() {
		return this.lastStatementNo;
	}

	public void setLastStatementNo(Integer lastStatementNo) {
		this.lastStatementNo = lastStatementNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SDATE_LAST_STMT_RUN", length = 23)
	public Date getSdateLastStmtRun() {
		return this.sdateLastStmtRun;
	}

	public void setSdateLastStmtRun(Date sdateLastStmtRun) {
		this.sdateLastStmtRun = sdateLastStmtRun;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_LAST_STMT_RUN_TO", length = 23)
	public Date getDateLastStmtRunTo() {
		return this.dateLastStmtRunTo;
	}

	public void setDateLastStmtRunTo(Date dateLastStmtRunTo) {
		this.dateLastStmtRunTo = dateLastStmtRunTo;
	}

	@Column(name = "PAYMENT_CODE")
	public Integer getPaymentCode() {
		return this.paymentCode;
	}

	public void setPaymentCode(Integer paymentCode) {
		this.paymentCode = paymentCode;
	}

	@Column(name = "PAYMENT_FREQUENCY")
	public Integer getPaymentFrequency() {
		return this.paymentFrequency;
	}

	public void setPaymentFrequency(Integer paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	@Column(name = "PAYMENT_METHOD")
	public Integer getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "PAYMENT_CCY", length = 4)
	public String getPaymentCcy() {
		return this.paymentCcy;
	}

	public void setPaymentCcy(String paymentCcy) {
		this.paymentCcy = paymentCcy;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY")
	@NotFound(action = NotFoundAction.IGNORE)
	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	@Column(name = "ADDRESS_LINE_1", length = 50)
	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@Column(name = "ADDRESS_LINE_2", length = 50)
	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@Column(name = "ADDRESS_LINE_3", length = 50)
	public String getAddressLine3() {
		return this.addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	@Column(name = "ADDRESS_LINE_4", length = 50)
	public String getAddressLine4() {
		return this.addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	@Column(name = "TOWN", length = 50)
	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Column(name = "COUNTY", length = 30)
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "POSTCODE", length = 12)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "FAX", length = 25)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "TELEPHONE", length = 25)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CHANNEL", length = 8)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DATE", length = 23)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name = "LOCATION", length = 8)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "CREATED_PROCESS", length = 12)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "MODIFY_PROCESS", length = 15)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "MODIFY_BY", length = 5)
	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "SERVICING_AGENT", length = 10)
	public String getServicingAgent() {
		return this.servicingAgent;
	}

	public void setServicingAgent(String servicingAgent) {
		this.servicingAgent = servicingAgent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_TERMINATION", length = 23)
	public Date getDateOfTermination() {
		return this.dateOfTermination;
	}

	public void setDateOfTermination(Date dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}

	@Column(name = "TERMINATION_BASIS")
	public Integer getTerminationBasis() {
		return this.terminationBasis;
	}

	public void setTerminationBasis(Integer terminationBasis) {
		this.terminationBasis = terminationBasis;
	}

	@Column(name = "TERMINATION_REASON", length = 8)
	public String getTerminationReason() {
		return this.terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	@Column(name = "MOBILE", length = 25)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "BANDING", length = 8)
	public String getBanding() {
		return this.banding;
	}

	public void setBanding(String banding) {
		this.banding = banding;
	}

	@Column(name = "NEXT_CATEGORY", length = 10)
	public String getNextCategory() {
		return this.nextCategory;
	}

	public void setNextCategory(String nextCategory) {
		this.nextCategory = nextCategory;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NEXT_CATEGORY_DATE", length = 23)
	public Date getNextCategoryDate() {
		return this.nextCategoryDate;
	}

	public void setNextCategoryDate(Date nextCategoryDate) {
		this.nextCategoryDate = nextCategoryDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CATEGORY_CHANGE", length = 23)
	public Date getLastCategoryChange() {
		return this.lastCategoryChange;
	}

	public void setLastCategoryChange(Date lastCategoryChange) {
		this.lastCategoryChange = lastCategoryChange;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<AssetManagerStrategyEntity> getAssetManagerStrategies() {
		return assetManagerStrategies;
	}

	public void setAssetManagerStrategies(Set<AssetManagerStrategyEntity> assetManagerStrategies) {
		this.assetManagerStrategies = assetManagerStrategies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<AgentHistoryDetailEntity> getAgentHistoryDetails() {
		return this.agentHistoryDetails;
	}

	public void setAgentHistoryDetails(Set<AgentHistoryDetailEntity> agentHistoryDetails) {
		this.agentHistoryDetails = agentHistoryDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterBroker")
	public List<AgentHierarchyEntity> getAgentHierarchies() {
		return this.agentHierarchies;
	}

	public void setAgentHierarchies(List<AgentHierarchyEntity> agentHierarchies) {
		this.agentHierarchies = agentHierarchies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<AgentEventEntity> getAgentEvents() {
		return this.agentEvents;
	}

	public void setAgentEvents(Set<AgentEventEntity> agentEvents) {
		this.agentEvents = agentEvents;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "HAS_BEEN_SOLD_BY", joinColumns = { @JoinColumn(name = "FK_AGENTSAGT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "FK_POLICIESPOL_ID", nullable = false, updatable = false) })
	public Set<PolicyEntity> getPolicies() {
		return this.policies;
	}

	public void setPolicies(Set<PolicyEntity> policys) {
		this.policies = policys;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<GeneralNoteEntity> getGeneralNotes() {
		return this.generalNotes;
	}

	public void setGeneralNotes(Set<GeneralNoteEntity> generalNotes) {
		this.generalNotes = generalNotes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<PolicyAgentShareEntity> getPolicyAgentShares() {
		return this.policyAgentShares;
	}

	public void setPolicyAgentShares(Set<PolicyAgentShareEntity> policyAgentShares) {
		this.policyAgentShares = policyAgentShares;
	}

	@Column(name = "DOCUMENTATION_LANGUAGE")
	public Integer getDocumentationLanguage() {
		return documentationLanguage;
	}

	public void setDocumentationLanguage(Integer documentationLanguage) {
		this.documentationLanguage = documentationLanguage;
	}

	@Column(name = "DESIRED_PAYMENT_CCY")
	public String getDesiredPaymentCcy() {
		return desiredPaymentCcy;
	}

	public void setDesiredPaymentCcy(String desiredPaymentCcy) {
		this.desiredPaymentCcy = desiredPaymentCcy;
	}

	@Column(name = "PAYMENT_COMMISSION")
	public Integer getPaymentCommission() {
		return paymentCommission;
	}

	public void setPaymentCommission(Integer paymentCommission) {
		this.paymentCommission = paymentCommission;
	}

	@Column(name = "VAT")
	public Integer getVat() {
		return vat;
	}

	public void setVat(Integer vat) {
		this.vat = vat;
	}

	@Column(name = "FIN_FEES_STATEMENT")
	public Integer getFinFeesStatement() {
		return finFeesStatement;
	}

	public void setFinFeesStatement(Integer finFeesStatement) {
		this.finFeesStatement = finFeesStatement;
	}

	@Column(name = "CRM_REFERERENCE")
	public String getCrmRefererence() {
		return crmRefererence;
	}

	public void setCrmRefererence(String crmRefererence) {
		this.crmRefererence = crmRefererence;
	}

	@Column(name = "CRM_STATUS")
	public String getCrmStatus() {
		return crmStatus;
	}

	public void setCrmStatus(String crmStatus) {
		this.crmStatus = crmStatus;
	}

	@Column(name = "PAYMENT_SUPERIOR_AGENT")
	public Integer getPaymentSuperiorAgent() {
		return paymentSuperiorAgent;
	}

	public void setPaymentSuperiorAgent(Integer paymentSuperiorAgent) {
		this.paymentSuperiorAgent = paymentSuperiorAgent;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the paymentAccountBic
	 */
	@Column(name = "PAYMENT_ACCOUNT_BIC")
	public String getPaymentAccountBic() {
		return paymentAccountBic;
	}

	/**
	 * @param paymentAccountBic the paymentAccountBic to set
	 */
	public void setPaymentAccountBic(String paymentAccountBic) {
		this.paymentAccountBic = paymentAccountBic;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	@OrderBy("BIC, ACCOUNT_CURRENCY")
	public Set<AgentBankAccountEntity> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Set<AgentBankAccountEntity> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<AgentContactEntity> getAgentContacts() {
		return agentContacts;
	}

	public void setAgentContacts(Set<AgentContactEntity> agentContacts) {
		this.agentContacts = agentContacts;
	}

	@Column(name = "CENTRALIZED_COMMUNICATION")
	public Boolean getCentralizedCommunication() {
		return centralizedCommunication;
	}

	public void setCentralizedCommunication(Boolean centralizedCommunication) {
		this.centralizedCommunication = centralizedCommunication;
	}

}
