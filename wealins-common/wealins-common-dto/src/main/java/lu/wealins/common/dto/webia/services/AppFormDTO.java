package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppFormDTO extends FormDataDTO {

	private String productCd;
	private String productCountryCd;
	private String applicationForm;
	private String clientName;
	private String countryCd;
	private BigDecimal expectedPremium;
	private String contractCurrency;
	private BigDecimal entryFeesPct;
	private BigDecimal entryFeesAmt;
	private BigDecimal companyEntryFeesPct;
	private BigDecimal companyEntryFeesAmt;
	private BigDecimal mngtFeesPct;
	private BigDecimal companyMngtFeesPct;
	private BigDecimal surrenderFees;
	private String sendingRules;
	private String mailToAgent;
	private Boolean orderByFax;
	private Boolean mandate;
	private Boolean scudo;
	private Boolean tax;
	private BigDecimal taxRate;
	private Date nonSurrenderClauseDt;
	private Boolean mandatoAllincasso;
	private Boolean noCoolOff;
	private String brokerRefContract;
	private BigDecimal paymentAmt;
	private Date paymentDt;
	private Boolean paymentTransfer;
	private BigDecimal term;
	private Integer deathCoverageTp;
	private Boolean deathCoverageStd;
	private BigDecimal deathCoveragePct;
	private BigDecimal deathCoverageAmt;
	private Integer lives;
	private BigDecimal multiplier;
	private Integer score;
	private PartnerFormDTO broker;
	private PartnerFormDTO subBroker;
	private PartnerFormDTO brokerContact;
	private PartnerFormDTO businessIntroducer;
	private Collection<PartnerFormDTO> countryManagers = new ArrayList<PartnerFormDTO>();
	private Collection<FundFormDTO> fundForms = new ArrayList<>();
	private Collection<BenefClauseFormDTO> lifeBenefClauseForms = new ArrayList<>();
	private Collection<BenefClauseFormDTO> deathBenefClauseForms = new ArrayList<>();
	private Collection<PolicyHolderFormDTO> policyHolders = new ArrayList<>();
	private Collection<InsuredFormDTO> insureds = new ArrayList<>();
	private Collection<BeneficiaryFormDTO> lifeBeneficiaries = new ArrayList<>();
	private Collection<BeneficiaryFormDTO> deathBeneficiaries = new ArrayList<>();
	private Collection<ClientFormDTO> deathSuccessors = new ArrayList<>();
	private Collection<ClientFormDTO> lifeSuccessors = new ArrayList<>();
	private Collection<ClientFormDTO> otherClients = new ArrayList<>();
	private Collection<PolicyTransferFormDTO> policyTransferForms = new ArrayList<>();
	private Boolean existedFid;
	private Boolean existedFas;
	private Boolean existedFe;
	private Boolean existedFic;
	private String premiumCountryCd;
	private Boolean policyTransfer;
	private Integer coverage;
	private String creationUser;
	private Date creationDt;
	private String updateUser;
	private Date updateDt;

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Date getCreationDt() {
		return creationDt;
	}

	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getProductCountryCd() {
		return productCountryCd;
	}

	public void setProductCountryCd(String productCountryCd) {
		this.productCountryCd = productCountryCd;
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

	public PartnerFormDTO getBroker() {
		return broker;
	}

	public void setBroker(PartnerFormDTO broker) {
		this.broker = broker;
	}

	public PartnerFormDTO getSubBroker() {
		return subBroker;
	}

	public void setSubBroker(PartnerFormDTO subBroker) {
		this.subBroker = subBroker;
	}

	public PartnerFormDTO getBrokerContact() {
		return brokerContact;
	}

	public void setBrokerContact(PartnerFormDTO brokerContact) {
		this.brokerContact = brokerContact;
	}

	public PartnerFormDTO getBusinessIntroducer() {
		return businessIntroducer;
	}

	public void setBusinessIntroducer(PartnerFormDTO businessIntroducer) {
		this.businessIntroducer = businessIntroducer;
	}

	public Collection<PartnerFormDTO> getCountryManagers() {
		return countryManagers;
	}

	public void setCountryManagers(Collection<PartnerFormDTO> countryManagers) {
		this.countryManagers = countryManagers;
	}

	public Collection<FundFormDTO> getFundForms() {
		return fundForms;
	}

	public void setFundForms(Collection<FundFormDTO> fundForms) {
		this.fundForms = fundForms;
	}

	public Collection<BenefClauseFormDTO> getLifeBenefClauseForms() {
		return lifeBenefClauseForms;
	}

	public void setLifeBenefClauseForms(Collection<BenefClauseFormDTO> lifeBenefClauseForms) {
		this.lifeBenefClauseForms = lifeBenefClauseForms;
	}

	public Collection<BenefClauseFormDTO> getDeathBenefClauseForms() {
		return deathBenefClauseForms;
	}

	public void setDeathBenefClauseForms(Collection<BenefClauseFormDTO> deathBenefClauseForms) {
		this.deathBenefClauseForms = deathBenefClauseForms;
	}

	public Collection<PolicyHolderFormDTO> getPolicyHolders() {
		return policyHolders;
	}

	public void setPolicyHolders(Collection<PolicyHolderFormDTO> policyHolders) {
		this.policyHolders = policyHolders;
	}

	public Collection<InsuredFormDTO> getInsureds() {
		return insureds;
	}

	public void setInsureds(Collection<InsuredFormDTO> insureds) {
		this.insureds = insureds;
	}

	public Collection<BeneficiaryFormDTO> getLifeBeneficiaries() {
		return lifeBeneficiaries;
	}

	public void setLifeBeneficiaries(Collection<BeneficiaryFormDTO> lifeBeneficiaries) {
		this.lifeBeneficiaries = lifeBeneficiaries;
	}

	public Collection<BeneficiaryFormDTO> getDeathBeneficiaries() {
		return deathBeneficiaries;
	}

	public void setDeathBeneficiaries(Collection<BeneficiaryFormDTO> deathBeneficiaries) {
		this.deathBeneficiaries = deathBeneficiaries;
	}

	public Collection<ClientFormDTO> getOtherClients() {
		return otherClients;
	}

	public void setOtherClients(Collection<ClientFormDTO> otherClients) {
		this.otherClients = otherClients;
	}
	
	public Collection<PolicyTransferFormDTO> getPolicyTransferForms() {
		return policyTransferForms;
	}
	
	public void setPolicyTransferForms(Collection<PolicyTransferFormDTO> policyTransferForms) {
		this.policyTransferForms = policyTransferForms;
	}

	/**
	 * @return the existedFid
	 */
	public Boolean getExistedFid() {
		return existedFid;
	}

	/**
	 * @param existedFid
	 *            the existedFid to set
	 */
	public void setExistedFid(Boolean existedFid) {
		this.existedFid = existedFid;
	}

	/**
	 * @return the existedFas
	 */
	public Boolean getExistedFas() {
		return existedFas;
	}

	/**
	 * @param existedFas
	 *            the existedFas to set
	 */
	public void setExistedFas(Boolean existedFas) {
		this.existedFas = existedFas;
	}

	/**
	 * @return the existedFe
	 */
	public Boolean getExistedFe() {
		return existedFe;
	}

	/**
	 * @param existedFe
	 *            the existedFe to set
	 */
	public void setExistedFe(Boolean existedFe) {
		this.existedFe = existedFe;
	}

	/**
	 * @return the existedFic
	 */
	public Boolean getExistedFic() {
		return existedFic;
	}

	/**
	 * @param existedFic
	 *            the existedFic to set
	 */
	public void setExistedFic(Boolean existedFic) {
		this.existedFic = existedFic;
	}

	public String getPremiumCountryCd() {
		return premiumCountryCd;
	}

	public void setPremiumCountryCd(String premiumCountryCd) {
		this.premiumCountryCd = premiumCountryCd;
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

	public Collection<ClientFormDTO> getDeathSuccessors() {
		return deathSuccessors;
	}

	public void setDeathSuccessors(Collection<ClientFormDTO> deathSuccessors) {
		this.deathSuccessors = deathSuccessors;
	}

	public Collection<ClientFormDTO> getLifeSuccessors() {
		return lifeSuccessors;
	}

	public void setLifeSuccessors(Collection<ClientFormDTO> lifeSuccessors) {
		this.lifeSuccessors = lifeSuccessors;
	}

	public Integer getCoverage() {
		return coverage;
	}

	public void setCoverage(Integer coverage) {
		this.coverage = coverage;
	}

}
