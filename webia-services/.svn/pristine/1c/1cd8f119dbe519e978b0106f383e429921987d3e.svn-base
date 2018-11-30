package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "APP_FORM")
public class AppFormEntity extends WorkflowFormEntity {

	private static final long serialVersionUID = 6258806649633341457L;

	@Column(name = "PRODUCT_CD")
	private String productCd;

	@Column(name = "APPLICATION_FORM")
	private String applicationForm;

	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "COUNTRY_CD")
	private String countryCd;

	@Column(name = "EXPECTED_PREMIUM")
	private BigDecimal expectedPremium;

	@Column(name = "CONTRACT_CURRENCY")
	private String contractCurrency;

	@Column(name = "ENTRY_FEES_PCT")
	private BigDecimal entryFeesPct;

	@Column(name = "ENTRY_FEES_AMT")
	private BigDecimal entryFeesAmt;

	@Column(name = "COMPANY_ENTRY_FEES_PCT")
	private BigDecimal companyEntryFeesPct;

	@Column(name = "COMPANY_ENTRY_FEES_AMT")
	private BigDecimal companyEntryFeesAmt;

	@Column(name = "MNGT_FEES_PCT")
	private BigDecimal mngtFeesPct;

	@Column(name = "COMPANY_MNGT_FEES_PCT")
	private BigDecimal companyMngtFeesPct;

	@Column(name = "SURRENDER_FEES")
	private BigDecimal surrenderFees;

	@Column(name = "SENDING_RULES")
	private String sendingRules;

	@Column(name = "MAIL_TO_AGENT")
	private String mailToAgent;

	@Column(name = "ORDER_BY_FAX")
	private Boolean orderByFax;

	@Column(name = "MANDATE")
	private Boolean mandate;

	@Column(name = "SCUDO")
	private Boolean scudo;

	@Column(name = "TAX")
	private Boolean tax;

	@Column(name = "TAX_RATE")
	private BigDecimal taxRate;

	@Column(name = "NON_SURRENDER_CLAUSE_DT")
	private Date nonSurrenderClauseDt;

	@Column(name = "MANDATO_ALL_INCASSO")
	private Boolean mandatoAllincasso;

	@Column(name = "NO_COOL_OFF")
	private Boolean noCoolOff;

	@Column(name = "BROKER_REF_CONTRACT")
	private String brokerRefContract;

	@Column(name = "PAYMENT_AMT")
	private BigDecimal paymentAmt;

	@Column(name = "PAYMENT_DT")
	private Date paymentDt;

	@Column(name = "PAYMENT_TRANSFER")
	private Boolean paymentTransfer;

	@Column(name = "TERM")
	private BigDecimal term;

	@Column(name = "DEATH_COVERAGE_TP")
	private Integer deathCoverageTp;

	@Column(name = "DEATH_COVERAGE_STD")
	private Boolean deathCoverageStd;

	@Column(name = "DEATH_COVERAGE_PCT")
	private BigDecimal deathCoveragePct;

	@Column(name = "DEATH_COVERAGE_AMT")
	private BigDecimal deathCoverageAmt;

	@Column(name = "LIVES")
	private Integer lives;

	@Column(name = "MULTIPLIER")
	private BigDecimal multiplier;

	@Column(name = "SCORE")
	private Integer score;

	@Column(name = "FIRST_CPS_USER")
	private String firstCpsUser;

	@Column(name = "POLICY_TRANSFER")
	private Boolean policyTransfer;

	@OneToMany(mappedBy = "formId")
	@OrderBy("partnerFormId")
	private Collection<PartnerFormEntity> partnerForms = new ArrayList<>();

	@OneToMany(mappedBy = "formId")
	@OrderBy("fundFormId")
	private Collection<FundFormEntity> fundForms = new ArrayList<>();

	@OneToMany(mappedBy = "formId")
	@OrderBy("benefClauseFormId")
	@Where(clause = "CLAUSE_FORM_TP = 'L'")
	private Collection<BenefClauseFormEntity> lifeBenefClauseForms = new ArrayList<>();

	@OneToMany(mappedBy = "formId")
	@OrderBy("benefClauseFormId")
	@Where(clause = "CLAUSE_FORM_TP='D'")
	private Collection<BenefClauseFormEntity> deathBenefClauseForms = new ArrayList<>();

	@OneToMany(mappedBy = "formId")
	@OrderBy("clientFormId")
	private Collection<ClientFormEntity> clientForms = new ArrayList<>();
	
	@OneToMany(mappedBy = "formId")
	@OrderBy("fromPolicyEffectDt, fromPolicy")
	private Collection<PolicyTransferFormEntity> policyTransferForms = new ArrayList<>();

	@Column(name = "EXISTED_FID", length = 1)
	private Boolean existedFid;

	@Column(name = "EXISTED_FAS", length = 1)
	private Boolean existedFas;

	@Column(name = "EXISTED_FE", length = 1)
	private Boolean existedFe;

	@Column(name = "EXISTED_FIC", length = 1)
	private Boolean existedFic;

	@Column(name = "PREMIUM_COUNTRY_CD")
	private String premiumCountryCd;

	@Column(name = "COVERAGE")
	private Integer coverage;

	public Boolean getExistedFid() {
		return existedFid;
	}

	public void setExistedFid(Boolean existedFid) {
		this.existedFid = existedFid;
	}

	public Boolean getExistedFas() {
		return existedFas;
	}

	public void setExistedFas(Boolean existedFas) {
		this.existedFas = existedFas;
	}

	public Boolean getExistedFe() {
		return existedFe;
	}

	public void setExistedFe(Boolean existedFe) {
		this.existedFe = existedFe;
	}

	public Boolean getExistedFic() {
		return existedFic;
	}

	public void setExistedFic(Boolean existedFic) {
		this.existedFic = existedFic;
	}

	public String getPremiumCountryCd() {
		return premiumCountryCd;
	}

	public void setPremiumCountryCd(String premiumCountryCd) {
		this.premiumCountryCd = premiumCountryCd;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getApplicationForm() {
		return applicationForm;
	}

	public void setApplicationForm(String applicationForm) {
		this.applicationForm = applicationForm;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCountryCd() {
		return countryCd;
	}

	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	public BigDecimal getExpectedPremium() {
		return expectedPremium;
	}

	public void setExpectedPremium(BigDecimal expectedPremium) {
		this.expectedPremium = expectedPremium;
	}

	public String getContractCurrency() {
		return contractCurrency;
	}

	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	public BigDecimal getEntryFeesPct() {
		return entryFeesPct;
	}

	public void setEntryFeesPct(BigDecimal entryFeesPct) {
		this.entryFeesPct = entryFeesPct;
	}

	public BigDecimal getEntryFeesAmt() {
		return entryFeesAmt;
	}

	public void setEntryFeesAmt(BigDecimal entryFeesAmt) {
		this.entryFeesAmt = entryFeesAmt;
	}

	public BigDecimal getCompanyEntryFeesPct() {
		return companyEntryFeesPct;
	}

	public void setCompanyEntryFeesPct(BigDecimal companyEntryFeesPct) {
		this.companyEntryFeesPct = companyEntryFeesPct;
	}

	public BigDecimal getCompanyEntryFeesAmt() {
		return companyEntryFeesAmt;
	}

	public void setCompanyEntryFeesAmt(BigDecimal companyEntryFeesAmt) {
		this.companyEntryFeesAmt = companyEntryFeesAmt;
	}

	public BigDecimal getMngtFeesPct() {
		return mngtFeesPct;
	}

	public void setMngtFeesPct(BigDecimal mngtFeesPct) {
		this.mngtFeesPct = mngtFeesPct;
	}

	public BigDecimal getCompanyMngtFeesPct() {
		return companyMngtFeesPct;
	}

	public void setCompanyMngtFeesPct(BigDecimal companyMngtFeesPct) {
		this.companyMngtFeesPct = companyMngtFeesPct;
	}

	public BigDecimal getSurrenderFees() {
		return surrenderFees;
	}

	public void setSurrenderFees(BigDecimal surrenderFees) {
		this.surrenderFees = surrenderFees;
	}

	public String getSendingRules() {
		return sendingRules;
	}

	public void setSendingRules(String sendingRules) {
		this.sendingRules = sendingRules;
	}

	public String getMailToAgent() {
		return mailToAgent;
	}

	public void setMailToAgent(String mailToAgent) {
		this.mailToAgent = mailToAgent;
	}

	public Boolean getOrderByFax() {
		return orderByFax;
	}

	public void setOrderByFax(Boolean orderByFax) {
		this.orderByFax = orderByFax;
	}

	public Boolean getMandate() {
		return mandate;
	}

	public void setMandate(Boolean mandate) {
		this.mandate = mandate;
	}

	public Boolean getScudo() {
		return scudo;
	}

	public void setScudo(Boolean scudo) {
		this.scudo = scudo;
	}

	public Boolean getTax() {
		return tax;
	}

	public void setTax(Boolean tax) {
		this.tax = tax;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public Date getNonSurrenderClauseDt() {
		return nonSurrenderClauseDt;
	}

	public void setNonSurrenderClauseDt(Date nonSurrenderClauseDt) {
		this.nonSurrenderClauseDt = nonSurrenderClauseDt;
	}

	public Boolean getMandatoAllincasso() {
		return mandatoAllincasso;
	}

	public void setMandatoAllincasso(Boolean mandatoAllincasso) {
		this.mandatoAllincasso = mandatoAllincasso;
	}

	public Boolean getNoCoolOff() {
		return noCoolOff;
	}

	public void setNoCoolOff(Boolean noCoolOff) {
		this.noCoolOff = noCoolOff;
	}

	public String getBrokerRefContract() {
		return brokerRefContract;
	}

	public void setBrokerRefContract(String brokerRefContract) {
		this.brokerRefContract = brokerRefContract;
	}

	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public Date getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(Date paymentDt) {
		this.paymentDt = paymentDt;
	}

	public Boolean getPaymentTransfer() {
		return paymentTransfer;
	}

	public void setPaymentTransfer(Boolean paymentTransfer) {
		this.paymentTransfer = paymentTransfer;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public Integer getDeathCoverageTp() {
		return deathCoverageTp;
	}

	public void setDeathCoverageTp(Integer deathCoverageTp) {
		this.deathCoverageTp = deathCoverageTp;
	}

	public BigDecimal getDeathCoveragePct() {
		return deathCoveragePct;
	}

	public void setDeathCoveragePct(BigDecimal deathCoveragePct) {
		this.deathCoveragePct = deathCoveragePct;
	}

	public BigDecimal getDeathCoverageAmt() {
		return deathCoverageAmt;
	}

	public void setDeathCoverageAmt(BigDecimal deathCoverageAmt) {
		this.deathCoverageAmt = deathCoverageAmt;
	}

	public Integer getLives() {
		return lives;
	}

	public void setLives(Integer lives) {
		this.lives = lives;
	}

	public BigDecimal getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(BigDecimal multiplier) {
		this.multiplier = multiplier;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getFirstCpsUser() {
		return firstCpsUser;
	}

	public void setFirstCpsUser(String firstCpsUser) {
		this.firstCpsUser = firstCpsUser;
	}

	public Collection<PartnerFormEntity> getPartnerForms() {
		return partnerForms;
	}

	public void setPartnerForms(Collection<PartnerFormEntity> partnerForms) {
		this.partnerForms = partnerForms;
	}

	public Collection<FundFormEntity> getFundForms() {
		return fundForms;
	}

	public void setFundForms(Collection<FundFormEntity> fundForms) {
		this.fundForms = fundForms;
	}

	public Collection<BenefClauseFormEntity> getLifeBenefClauseForms() {
		return lifeBenefClauseForms;
	}

	public void setLifeBenefClauseForms(Collection<BenefClauseFormEntity> lifeBenefClauseForms) {
		this.lifeBenefClauseForms = lifeBenefClauseForms;
	}

	public Collection<BenefClauseFormEntity> getDeathBenefClauseForms() {
		return deathBenefClauseForms;
	}

	public void setDeathBenefClauseForms(Collection<BenefClauseFormEntity> deathBenefClauseForms) {
		this.deathBenefClauseForms = deathBenefClauseForms;
	}

	public Collection<ClientFormEntity> getClientForms() {
		return clientForms;
	}

	public void setClientForms(Collection<ClientFormEntity> clientForms) {
		this.clientForms = clientForms;
	}
	
	public Collection<PolicyTransferFormEntity> getPolicyTransferForms() {
		return policyTransferForms;
	}
	
	public void setPolicyTransferForms(Collection<PolicyTransferFormEntity> policyTransferForms) {
		this.policyTransferForms = policyTransferForms;
	}

	public Boolean getDeathCoverageStd() {
		return deathCoverageStd;
	}

	public void setDeathCoverageStd(Boolean deathCoverageStd) {
		this.deathCoverageStd = deathCoverageStd;
	}

	public Boolean getPolicyTransfer() {
		return policyTransfer;
	}

	public void setPolicyTransfer(Boolean policyTransfer) {
		this.policyTransfer = policyTransfer;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

}
