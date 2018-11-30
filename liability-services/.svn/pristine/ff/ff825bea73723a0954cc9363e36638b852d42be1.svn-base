package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.liability.services.core.utils.Historizable;

@Entity
@Table(name = "POLICIES")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "createdProcess", "modifyProcess", "createdBy", "createdDate", "createdTime", "modifyDate", "modifyTime", "modifyBy" })
@Historizable(id="polId")
@EntityListeners(AuditingEntityListener.class)
public class PolicyEntity
		implements Serializable {
	@Transient
	public static final int POLID_LENGTH = 14;
	private String polId;
	private ProductEntity product;
	private int status;
	private Integer subStatus;
	private String currency;
	private Date dateOfApplication;
	private Date dateOfCommencement;
	private Integer unitHoldings;
	private Integer type;
	private String accountingBranch;
	private BigDecimal planDebt;
	private Integer segmentsNo;
	private Integer segments;
	private String additionalId;
	private String userStatus;
	private String classification;
	private Integer previousStatus;
	private Integer statusChangeNo;
	// private String category;
	private UoptDetailEntity category;
	private Date lockTime;
	private String lockProcess;
	private String lockUser;
	private Date lockDate;

	@CreatedBy
	private String createdBy;
	@CreatedDate
	private Date createdDate;
	@CreatedDate
	private Date createdTime;
	private String createdProcess;
	
	@LastModifiedBy
	private String modifyBy;
	@LastModifiedDate	
	private Date modifyDate;
	@LastModifiedDate	
	private Date modifyTime;
	private String modifyProcess;
	
	private Integer suspendEftCollection;
	private Integer suspendPayments;
	private Integer endorsements;
	private String fkPoliciespolId;
	private Long ptdNotified;
	private String customerSegment;
	private String sourceChannel;
	private String referralSource;
	private String referrerId;
	private String bankAcc;
	private String issueCountryOfResidence;
	private String riskProfile;
	private String riskCurrency;
	private String investCat;
	private String investObjective;
	private String alternativeFunds;
	private Date riskProfileDate;
	private Boolean orderByFax;
	private Date nonSurrenderClauseDate;
	private Integer cessionCoSubscriptionRights;
	private String mailToAgent;
	private BigDecimal expectedPremium;
	private Integer scoreNewBusiness;
	private Integer scoreLastTrans;
	private Integer privateEquity;
	private String privateEquityDetails;
	private Boolean assignmentPledge;
	private String assignmentPledgeDetails;
	private Integer dap;
	private Integer pep;
	private Integer paymentThirdPerson;
	private String brokerRefContract;
	private Boolean scudo;
	private Boolean mandatoAllIncasso;
	private Boolean noCooloff;
	private Boolean exPaymentTransfer;
	private Boolean exMandate;

	@JsonIgnore
	private Set<PolicyRegularPaymentEntity> policyRegularPayments = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyFundDirectionEntity> policyFundDirections = new HashSet<>(0);
	private Set<AgentEntity> agents = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyFundHoldingEntity> policyFundHoldings = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyCoverageBillEntity> policyCoverageBills = new HashSet<>(0);
	@JsonIgnore
	private Set<BillsHistoryEntity> billsHistorys = new HashSet<>(0);
	private Set<PolicyCoverageEntity> policyCoverages = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyAgentHierarchyEntity> policyAgentHierarchys = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyFundInstructionEntity> policyFundInstructions = new HashSet<>(0);
	@JsonIgnore
	private Set<TransactionEntity> transactions = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyCommissionEntity> policyCommissions = new HashSet<>(0);
	@JsonIgnore
	private Set<CollectionReferenceEntity> collectionReferences = new HashSet<>(0);
	@JsonIgnore
	private Set<GeneralNoteEntity> generalNotes = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyEventEntity> policyEvents = new HashSet<>(0);
	@JsonIgnore
	private Set<DocumentRequestEntity> documentRequests = new HashSet<>(0);
	@JsonIgnore
	private Set<MemberDetailEntity> memberDetails = new HashSet<>(0);
	@JsonIgnore
	private Set<FundTransactionEntity> fundTransactions = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyAgentShareEntity> policyAgentShares = new HashSet<>(0);
	@JsonIgnore
	private Set<ClaimEntity> claims = new HashSet<>(0);
	@JsonIgnore
	private Set<PolicyRequirementEntity> policyRequirements = new HashSet<>(0);
	@JsonIgnore
	private Set<ReinsurancePolicyDetailEntity> reinsurancePolicyDetails = new HashSet<>(0);
	@JsonIgnore
	private Set<BillEntity> bills = new HashSet<>(0);
	@JsonIgnore
	private Set<CliPolRelationshipEntity> cliPolRelationships = new HashSet<>(0);
	private Set<PolicyPremiumEntity> policyPremiums = new HashSet<>(0);
	private Set<PolicyTransferEntity> policyTransfers = new HashSet<>(0);

	@Id
	@Column(name = "POL_ID", unique = true, nullable = false, length = 14)
	public String getPolId() {
		return this.polId;
	}

	public void setPolId(String polId) {
		this.polId = polId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT")
	public ProductEntity getProduct() {
		return this.product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	@Column(name = "STATUS", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "SUB_STATUS")
	public Integer getSubStatus() {
		return this.subStatus;
	}

	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}

	@Column(name = "CURRENCY", length = 5)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_APPLICATION", length = 23)
	public Date getDateOfApplication() {
		return this.dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_COMMENCEMENT", length = 23)
	public Date getDateOfCommencement() {
		return this.dateOfCommencement;
	}

	public void setDateOfCommencement(Date dateOfCommencement) {
		this.dateOfCommencement = dateOfCommencement;
	}

	@Column(name = "UNIT_HOLDINGS")
	public Integer getUnitHoldings() {
		return this.unitHoldings;
	}

	public void setUnitHoldings(Integer unitHoldings) {
		this.unitHoldings = unitHoldings;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "ACCOUNTING_BRANCH", length = 10)
	public String getAccountingBranch() {
		return this.accountingBranch;
	}

	public void setAccountingBranch(String accountingBranch) {
		this.accountingBranch = accountingBranch;
	}

	@Column(name = "PLAN_DEBT", precision = 12)
	public BigDecimal getPlanDebt() {
		return this.planDebt;
	}

	public void setPlanDebt(BigDecimal planDebt) {
		this.planDebt = planDebt;
	}

	@Column(name = "SEGMENTS_NO")
	public Integer getSegmentsNo() {
		return this.segmentsNo;
	}

	public void setSegmentsNo(Integer segmentsNo) {
		this.segmentsNo = segmentsNo;
	}

	@Column(name = "SEGMENTS")
	public Integer getSegments() {
		return this.segments;
	}

	public void setSegments(Integer segments) {
		this.segments = segments;
	}

	@Column(name = "ADDITIONAL_ID", length = 20)
	public String getAdditionalId() {
		return this.additionalId;
	}

	public void setAdditionalId(String additionalId) {
		this.additionalId = additionalId;
	}

	@Column(name = "USER_STATUS", length = 2)
	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "CLASSIFICATION", length = 8)
	public String getClassification() {
		return this.classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	@Column(name = "PREVIOUS_STATUS")
	public Integer getPreviousStatus() {
		return this.previousStatus;
	}

	public void setPreviousStatus(Integer previousStatus) {
		this.previousStatus = previousStatus;
	}

	@Column(name = "STATUS_CHANGE_NO")
	public Integer getStatusChangeNo() {
		return this.statusChangeNo;
	}

	public void setStatusChangeNo(Integer statusChangeNo) {
		this.statusChangeNo = statusChangeNo;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY", insertable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public UoptDetailEntity getCategory() {
		return category;
	}

	public void setCategory(UoptDetailEntity category) {
		this.category = category;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCK_TIME", length = 23)
	public Date getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	@Column(name = "LOCK_PROCESS", length = 12)
	public String getLockProcess() {
		return this.lockProcess;
	}

	public void setLockProcess(String lockProcess) {
		this.lockProcess = lockProcess;
	}

	@Column(name = "LOCK_USER", length = 5)
	public String getLockUser() {
		return this.lockUser;
	}

	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCK_DATE", length = 23)
	public Date getLockDate() {
		return this.lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	@Column(name = "CREATED_PROCESS", nullable = false, length = 15)
	public String getCreatedProcess() {
		return this.createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	@Column(name = "CREATED_BY", length = 5)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME", nullable = false, length = 23)
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

	@Column(name = "MODIFY_PROCESS", length = 15)
	public String getModifyProcess() {
		return this.modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 23)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "SUSPEND_EFT_COLLECTION")
	public Integer getSuspendEftCollection() {
		return this.suspendEftCollection;
	}

	public void setSuspendEftCollection(Integer suspendEftCollection) {
		this.suspendEftCollection = suspendEftCollection;
	}

	@Column(name = "SUSPEND_PAYMENTS")
	public Integer getSuspendPayments() {
		return this.suspendPayments;
	}

	public void setSuspendPayments(Integer suspendPayments) {
		this.suspendPayments = suspendPayments;
	}

	@Column(name = "ENDORSEMENTS")
	public Integer getEndorsements() {
		return this.endorsements;
	}

	public void setEndorsements(Integer endorsements) {
		this.endorsements = endorsements;
	}

	@Column(name = "FK_POLICIESPOL_ID", length = 14)
	public String getFkPoliciespolId() {
		return this.fkPoliciespolId;
	}

	public void setFkPoliciespolId(String fkPoliciespolId) {
		this.fkPoliciespolId = fkPoliciespolId;
	}

	@Column(name = "PTD_NOTIFIED", precision = 10, scale = 0)
	public Long getPtdNotified() {
		return this.ptdNotified;
	}

	public void setPtdNotified(Long ptdNotified) {
		this.ptdNotified = ptdNotified;
	}

	@Column(name = "CUSTOMER_SEGMENT", length = 8)
	public String getCustomerSegment() {
		return this.customerSegment;
	}

	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}

	@Column(name = "SOURCE_CHANNEL", length = 8)
	public String getSourceChannel() {
		return this.sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	@Column(name = "REFERRAL_SOURCE", length = 8)
	public String getReferralSource() {
		return this.referralSource;
	}

	public void setReferralSource(String referralSource) {
		this.referralSource = referralSource;
	}

	@Column(name = "REFERRER_ID", length = 8)
	public String getReferrerId() {
		return this.referrerId;
	}

	public void setReferrerId(String referrerId) {
		this.referrerId = referrerId;
	}

	@Column(name = "BANK_ACC", length = 15)
	public String getBankAcc() {
		return this.bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	@Column(name = "ISSUE_COUNTRY_OF_RESIDENCE", length = 3)
	public String getIssueCountryOfResidence() {
		return this.issueCountryOfResidence;
	}

	public void setIssueCountryOfResidence(String issueCountryOfResidence) {
		this.issueCountryOfResidence = issueCountryOfResidence;
	}

	@Column(name = "RISK_PROFILE", length = 8)
	public String getRiskProfile() {
		return this.riskProfile;
	}

	public void setRiskProfile(String riskProfile) {
		this.riskProfile = riskProfile;
	}

	@Column(name = "RISK_CURRENCY", length = 8)
	public String getRiskCurrency() {
		return this.riskCurrency;
	}

	public void setRiskCurrency(String riskCurrency) {
		this.riskCurrency = riskCurrency;
	}

	@Column(name = "INVEST_CAT", length = 8)
	public String getInvestCat() {
		return this.investCat;
	}

	public void setInvestCat(String investCat) {
		this.investCat = investCat;
	}

	@Column(name = "INVEST_OBJECTIVE", length = 8)
	public String getInvestObjective() {
		return this.investObjective;
	}

	public void setInvestObjective(String investObjective) {
		this.investObjective = investObjective;
	}

	@Column(name = "ALTERNATIVE_FUNDS", length = 8)
	public String getAlternativeFunds() {
		return this.alternativeFunds;
	}

	public void setAlternativeFunds(String alternativeFunds) {
		this.alternativeFunds = alternativeFunds;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RISK_PROFILE_DATE", length = 23)
	public Date getRiskProfileDate() {
		return this.riskProfileDate;
	}

	public void setRiskProfileDate(Date riskProfileDate) {
		this.riskProfileDate = riskProfileDate;
	}

	@Column(name = "ORDER_BY_FAX")
	public Boolean getOrderByFax() {
		return this.orderByFax;
	}

	public void setOrderByFax(Boolean orderByFax) {
		this.orderByFax = orderByFax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NON_SURRENDER_CLAUSE_DATE", length = 23)
	public Date getNonSurrenderClauseDate() {
		return this.nonSurrenderClauseDate;
	}

	public void setNonSurrenderClauseDate(Date nonSurrenderClauseDate) {
		this.nonSurrenderClauseDate = nonSurrenderClauseDate;
	}

	@Column(name = "CESSION_CO_SUBSCRIPTION_RIGHTS")
	public Integer getCessionCoSubscriptionRights() {
		return this.cessionCoSubscriptionRights;
	}

	public void setCessionCoSubscriptionRights(Integer cessionCoSubscriptionRights) {
		this.cessionCoSubscriptionRights = cessionCoSubscriptionRights;
	}

	@Column(name = "MAIL_TO_AGENT", length = 8)
	public String getMailToAgent() {
		return this.mailToAgent;
	}

	public void setMailToAgent(String mailToAgent) {
		this.mailToAgent = mailToAgent;
	}

	@Column(name = "EXPECTED_PREMIUM", precision = 15)
	public BigDecimal getExpectedPremium() {
		return this.expectedPremium;
	}

	public void setExpectedPremium(BigDecimal expectedPremium) {
		this.expectedPremium = expectedPremium;
	}

	@Column(name = "SCORE_NEW_BUSINESS")
	public Integer getScoreNewBusiness() {
		return this.scoreNewBusiness;
	}

	public void setScoreNewBusiness(Integer scoreNewBusiness) {
		this.scoreNewBusiness = scoreNewBusiness;
	}

	@Column(name = "SCORE_LAST_TRANS")
	public Integer getScoreLastTrans() {
		return this.scoreLastTrans;
	}

	public void setScoreLastTrans(Integer scoreLastTrans) {
		this.scoreLastTrans = scoreLastTrans;
	}

	@Column(name = "PRIVATE_EQUITY")
	public Integer getPrivateEquity() {
		return this.privateEquity;
	}

	public void setPrivateEquity(Integer privateEquity) {
		this.privateEquity = privateEquity;
	}

	@Column(name = "PRIVATE_EQUITY_DETAILS", length = 50)
	public String getPrivateEquityDetails() {
		return this.privateEquityDetails;
	}

	public void setPrivateEquityDetails(String privateEquityDetails) {
		this.privateEquityDetails = privateEquityDetails;
	}

	@Column(name = "ASSIGNMENT_PLEDGE")
	public Boolean getAssignmentPledge() {
		return this.assignmentPledge;
	}

	public void setAssignmentPledge(Boolean assignmentPledge) {
		this.assignmentPledge = assignmentPledge;
	}

	@Column(name = "ASSIGNMENT_PLEDGE_DETAILS", length = 50)
	public String getAssignmentPledgeDetails() {
		return this.assignmentPledgeDetails;
	}

	public void setAssignmentPledgeDetails(String assignmentPledgeDetails) {
		this.assignmentPledgeDetails = assignmentPledgeDetails;
	}

	@Column(name = "DAP")
	public Integer getDap() {
		return this.dap;
	}

	public void setDap(Integer dap) {
		this.dap = dap;
	}

	@Column(name = "PEP")
	public Integer getPep() {
		return this.pep;
	}

	public void setPep(Integer pep) {
		this.pep = pep;
	}

	@Column(name = "PAYMENT_THIRD_PERSON")
	public Integer getPaymentThirdPerson() {
		return this.paymentThirdPerson;
	}

	public void setPaymentThirdPerson(Integer paymentThirdPerson) {
		this.paymentThirdPerson = paymentThirdPerson;
	}

	@Column(name = "BROKER_REF_CONTRACT")
	public String getBrokerRefContract() {
		return this.brokerRefContract;
	}

	public void setScudo(Boolean scudo) {
		this.scudo = scudo;
	}

	@Column(name = "SCUDO")
	public Boolean getScudo() {
		return this.scudo;
	}

	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}

	@Column(name = "MANDATO_ALL_INCASSO")
	public Boolean getMandatoAllIncasso() {
		return this.mandatoAllIncasso;
	}

	public void setMandatoAllIncasso(Boolean mandatoAllIncasso) {
		this.mandatoAllIncasso = mandatoAllIncasso;
	}

	@Column(name = "NO_COOL_OFF")
	public Boolean getNoCooloff() {
		return this.noCooloff;
	}

	public void setNoCooloff(Boolean noCooloff) {
		this.noCooloff = noCooloff;
	}

	/**
	 * @return the exPaymentTransfer
	 */
	@Column(name = "EX_PAYMENT_TRANSFER")
	public Boolean getExPaymentTransfer() {
		return exPaymentTransfer;
	}

	/**
	 * @param exPaymentTransfer the exPaymentTransfer to set
	 */
	public void setExPaymentTransfer(Boolean exPaymentTransfer) {
		this.exPaymentTransfer = exPaymentTransfer;
	}

	/**
	 * @return the exMandate
	 */
	@Column(name = "EX_MANDATE")
	public Boolean getExMandate() {
		return exMandate;
	}

	/**
	 * @param exMandate the exMandate to set
	 */
	public void setExMandate(Boolean exMandate) {
		this.exMandate = exMandate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyRegularPaymentEntity> getPolicyRegularPayments() {
		return this.policyRegularPayments;
	}

	public void setPolicyRegularPayments(Set<PolicyRegularPaymentEntity> policyRegularPayments) {
		this.policyRegularPayments = policyRegularPayments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyFundDirectionEntity> getPolicyFundDirections() {
		return this.policyFundDirections;
	}

	public void setPolicyFundDirections(Set<PolicyFundDirectionEntity> policyFundDirections) {
		this.policyFundDirections = policyFundDirections;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "HAS_BEEN_SOLD_BY", joinColumns = { @JoinColumn(name = "FK_POLICIESPOL_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "FK_AGENTSAGT_ID", nullable = false, updatable = false) })
	public Set<AgentEntity> getAgents() {
		return this.agents;
	}

	public void setAgents(Set<AgentEntity> agents) {
		this.agents = agents;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyFundHoldingEntity> getPolicyFundHoldings() {
		return this.policyFundHoldings;
	}

	public void setPolicyFundHoldings(Set<PolicyFundHoldingEntity> policyFundHoldings) {
		this.policyFundHoldings = policyFundHoldings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyCoverageBillEntity> getPolicyCoverageBills() {
		return this.policyCoverageBills;
	}

	public void setPolicyCoverageBills(Set<PolicyCoverageBillEntity> policyCoverageBills) {
		this.policyCoverageBills = policyCoverageBills;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<BillsHistoryEntity> getBillsHistorys() {
		return this.billsHistorys;
	}

	public void setBillsHistorys(Set<BillsHistoryEntity> billsHistorys) {
		this.billsHistorys = billsHistorys;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyCoverageEntity> getPolicyCoverages() {
		return this.policyCoverages;
	}

	public void setPolicyCoverages(Set<PolicyCoverageEntity> policyCoverages) {
		this.policyCoverages = policyCoverages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyAgentHierarchyEntity> getPolicyAgentHierarchys() {
		return this.policyAgentHierarchys;
	}

	public void setPolicyAgentHierarchys(Set<PolicyAgentHierarchyEntity> policyAgentHierarchys) {
		this.policyAgentHierarchys = policyAgentHierarchys;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyFundInstructionEntity> getPolicyFundInstructions() {
		return this.policyFundInstructions;
	}

	public void setPolicyFundInstructions(Set<PolicyFundInstructionEntity> policyFundInstructions) {
		this.policyFundInstructions = policyFundInstructions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<TransactionEntity> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(Set<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyCommissionEntity> getPolicyCommissions() {
		return this.policyCommissions;
	}

	public void setPolicyCommissions(Set<PolicyCommissionEntity> policyCommissions) {
		this.policyCommissions = policyCommissions;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DEFINES_REFERENCES", joinColumns = { @JoinColumn(name = "FK_POLICIESPOL_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "FK_COLLECTION_RCRF_ID", nullable = false, updatable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	public Set<CollectionReferenceEntity> getCollectionReferences() {
		return this.collectionReferences;
	}

	public void setCollectionReferences(Set<CollectionReferenceEntity> collectionReferences) {
		this.collectionReferences = collectionReferences;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<GeneralNoteEntity> getGeneralNotes() {
		return this.generalNotes;
	}

	public void setGeneralNotes(Set<GeneralNoteEntity> generalNotes) {
		this.generalNotes = generalNotes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyEventEntity> getPolicyEvents() {
		return this.policyEvents;
	}

	public void setPolicyEvents(Set<PolicyEventEntity> policyEvents) {
		this.policyEvents = policyEvents;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<DocumentRequestEntity> getDocumentRequests() {
		return this.documentRequests;
	}

	public void setDocumentRequests(Set<DocumentRequestEntity> documentRequests) {
		this.documentRequests = documentRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<MemberDetailEntity> getMemberDetails() {
		return this.memberDetails;
	}

	public void setMemberDetails(Set<MemberDetailEntity> memberDetails) {
		this.memberDetails = memberDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<FundTransactionEntity> getFundTransactions() {
		return this.fundTransactions;
	}

	public void setFundTransactions(Set<FundTransactionEntity> fundTransactions) {
		this.fundTransactions = fundTransactions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "polId")
	public Set<PolicyAgentShareEntity> getPolicyAgentShares() {
		return this.policyAgentShares;
	}

	public void setPolicyAgentShares(Set<PolicyAgentShareEntity> policyAgentShares) {
		this.policyAgentShares = policyAgentShares;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<ClaimEntity> getClaims() {
		return this.claims;
	}

	public void setClaims(Set<ClaimEntity> claims) {
		this.claims = claims;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyRequirementEntity> getPolicyRequirements() {
		return this.policyRequirements;
	}

	public void setPolicyRequirements(Set<PolicyRequirementEntity> policyRequirements) {
		this.policyRequirements = policyRequirements;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<ReinsurancePolicyDetailEntity> getReinsurancePolicyDetails() {
		return this.reinsurancePolicyDetails;
	}

	public void setReinsurancePolicyDetails(Set<ReinsurancePolicyDetailEntity> reinsurancePolicyDetails) {
		this.reinsurancePolicyDetails = reinsurancePolicyDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<BillEntity> getBills() {
		return this.bills;
	}

	public void setBills(Set<BillEntity> bills) {
		this.bills = bills;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	@NotFound(action = NotFoundAction.IGNORE)
	public Set<CliPolRelationshipEntity> getCliPolRelationships() {
		return this.cliPolRelationships;
	}

	public void setCliPolRelationships(Set<CliPolRelationshipEntity> cliPolRelationships) {
		this.cliPolRelationships = cliPolRelationships;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
	public Set<PolicyPremiumEntity> getPolicyPremiums() {
		return this.policyPremiums;
	}

	public void setPolicyPremiums(Set<PolicyPremiumEntity> policyPremiums) {
		this.policyPremiums = policyPremiums;
	}
	
	@OneToMany(mappedBy = "fkPoliciespolId")
	@OrderBy("policyTransferId")
	public Set<PolicyTransferEntity> getPolicyTransfers() {
		return policyTransfers;
	}
	
	public void setPolicyTransfers(Set<PolicyTransferEntity> policyTransfers) {
		this.policyTransfers = policyTransfers;
	}
}
