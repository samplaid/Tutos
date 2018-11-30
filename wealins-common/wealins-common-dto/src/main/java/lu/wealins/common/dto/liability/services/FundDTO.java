package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author XQV89
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundDTO extends FundLiteDTO {

	private Integer pricingDay;
	private Integer pricingDayOfMonth;
	private int priceBasis;
	private BigDecimal bidOfferSpread;
	private int unitTypes;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private String createdProcess;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String modifyProcess;
	private Date pricingTime;
	private Integer accountingStream;
	private String documentationName;
	private String nlProduct;
	private Date cutOffTime;
	private String ulFundType;
	private BigDecimal maxAllocationPercent;
	private AgentDTO assetManagerAgent;
	private AgentDTO depositBankAgent;
	private String country;
	private BigDecimal navEntryFee;
	private BigDecimal navExitFee;
	private String riskCurrency;
	private String investCat;
	private String investObjective;
	private Date riskProfileDate;
	private String notes;
	private Integer pricingDelay;
	private String poaType;
	private String mandateHolder;
	private Date poaDate;
	private String classOfRisk;
	private BigDecimal privateEquityFee;
	private String depositAccount;
	private BigDecimal finFeesMinAmount;
	private BigDecimal finFeesMaxAmount;
	private Boolean assetManRiskProfile;
	private Boolean poc;
	private Date pocDate;
	private String pocType;
	private String consultant;
	private AgentDTO financialAdvisorAgent;
	private Boolean hasTransaction;

	private String finAdvisorFeeCcy;
	private AgentDTO brokerAgent;
	private Boolean ucits;
	private BigDecimal maxAllocationA;
	private BigDecimal maxAllocationB;
	private BigDecimal maxAllocationC;
	private BigDecimal maxAllocationD;
	private BigDecimal maxAllocationN;
	private Integer exportedFund; // indicator to know if it is an old fund from FISA before WEALINS
	
	private Collection<String> warns;

	public Integer getPricingDay() {
		return pricingDay;
	}
	public void setPricingDay(Integer pricingDay) {
		this.pricingDay = pricingDay;
	}
	public Integer getPricingDayOfMonth() {
		return pricingDayOfMonth;
	}
	public void setPricingDayOfMonth(Integer pricingDayOfMonth) {
		this.pricingDayOfMonth = pricingDayOfMonth;
	}
	public int getPriceBasis() {
		return priceBasis;
	}
	public void setPriceBasis(int priceBasis) {
		this.priceBasis = priceBasis;
	}
	public BigDecimal getBidOfferSpread() {
		return bidOfferSpread;
	}
	public void setBidOfferSpread(BigDecimal bidOfferSpread) {
		this.bidOfferSpread = bidOfferSpread;
	}
	public int getUnitTypes() {
		return unitTypes;
	}
	public void setUnitTypes(int unitTypes) {
		this.unitTypes = unitTypes;
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
	public String getCreatedProcess() {
		return createdProcess;
	}
	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
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
	public String getModifyProcess() {
		return modifyProcess;
	}
	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}
	public Date getPricingTime() {
		return pricingTime;
	}
	public void setPricingTime(Date pricingTime) {
		this.pricingTime = pricingTime;
	}
	public Integer getAccountingStream() {
		return accountingStream;
	}
	public void setAccountingStream(Integer accountingStream) {
		this.accountingStream = accountingStream;
	}
	public String getDocumentationName() {
		return documentationName;
	}
	public void setDocumentationName(String documentationName) {
		this.documentationName = documentationName;
	}
	public String getNlProduct() {
		return nlProduct;
	}
	public void setNlProduct(String nlProduct) {
		this.nlProduct = nlProduct;
	}
	public Date getCutOffTime() {
		return cutOffTime;
	}
	public void setCutOffTime(Date cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public String getUlFundType() {
		return ulFundType;
	}
	public void setUlFundType(String ulFundType) {
		this.ulFundType = ulFundType;
	}
	public BigDecimal getMaxAllocationPercent() {
		return maxAllocationPercent;
	}
	public void setMaxAllocationPercent(BigDecimal maxAllocationPercent) {
		this.maxAllocationPercent = maxAllocationPercent;
	}
	public AgentDTO getAssetManagerAgent() {
		return assetManagerAgent;
	}
	public void setAssetManagerAgent(AgentDTO assetManagerAgent) {
		this.assetManagerAgent = assetManagerAgent;
	}
	public AgentDTO getDepositBankAgent() {
		return depositBankAgent;
	}
	public void setDepositBankAgent(AgentDTO depositBankAgent) {
		this.depositBankAgent = depositBankAgent;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public BigDecimal getNavEntryFee() {
		return navEntryFee;
	}
	public void setNavEntryFee(BigDecimal navEntryFee) {
		this.navEntryFee = navEntryFee;
	}
	public BigDecimal getNavExitFee() {
		return navExitFee;
	}
	public void setNavExitFee(BigDecimal navExitFee) {
		this.navExitFee = navExitFee;
	}
	public String getRiskCurrency() {
		return riskCurrency;
	}
	public void setRiskCurrency(String riskCurrency) {
		this.riskCurrency = riskCurrency;
	}
	public String getInvestCat() {
		return investCat;
	}
	public void setInvestCat(String investCat) {
		this.investCat = investCat;
	}
	public String getInvestObjective() {
		return investObjective;
	}
	public void setInvestObjective(String investObjective) {
		this.investObjective = investObjective;
	}
	public Date getRiskProfileDate() {
		return riskProfileDate;
	}
	public void setRiskProfileDate(Date riskProfileDate) {
		this.riskProfileDate = riskProfileDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getPricingDelay() {
		return pricingDelay;
	}
	public void setPricingDelay(Integer pricingDelay) {
		this.pricingDelay = pricingDelay;
	}
	public String getPoaType() {
		return poaType;
	}
	public void setPoaType(String poaType) {
		this.poaType = poaType;
	}
	public String getMandateHolder() {
		return mandateHolder;
	}
	public void setMandateHolder(String mandateHolder) {
		this.mandateHolder = mandateHolder;
	}
	public Date getPoaDate() {
		return poaDate;
	}
	public void setPoaDate(Date poaDate) {
		this.poaDate = poaDate;
	}
	public String getClassOfRisk() {
		return classOfRisk;
	}
	public void setClassOfRisk(String classOfRisk) {
		this.classOfRisk = classOfRisk;
	}
	public BigDecimal getPrivateEquityFee() {
		return privateEquityFee;
	}
	public void setPrivateEquityFee(BigDecimal privateEquityFee) {
		this.privateEquityFee = privateEquityFee;
	}

	public String getDepositAccount() {
		return depositAccount;
	}
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	public BigDecimal getFinFeesMinAmount() {
		return finFeesMinAmount;
	}
	public void setFinFeesMinAmount(BigDecimal finFeesMinAmount) {
		this.finFeesMinAmount = finFeesMinAmount;
	}
	public BigDecimal getFinFeesMaxAmount() {
		return finFeesMaxAmount;
	}
	public void setFinFeesMaxAmount(BigDecimal finFeesMaxAmount) {
		this.finFeesMaxAmount = finFeesMaxAmount;
	}
	public Boolean getAssetManRiskProfile() {
		return assetManRiskProfile;
	}
	public void setAssetManRiskProfile(Boolean assetManRiskProfile) {
		this.assetManRiskProfile = assetManRiskProfile;
	}
	public Boolean getPoc() {
		return poc;
	}
	public void setPoc(Boolean poc) {
		this.poc = poc;
	}
	public Date getPocDate() {
		return pocDate;
	}
	public void setPocDate(Date pocDate) {
		this.pocDate = pocDate;
	}
	public String getPocType() {
		return pocType;
	}
	public void setPocType(String pocType) {
		this.pocType = pocType;
	}
	public String getConsultant() {
		return consultant;
	}
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}
	public AgentDTO getFinancialAdvisorAgent() {
		return financialAdvisorAgent;
	}
	public void setFinancialAdvisorAgent(AgentDTO financialAdvisorAgent) {
		this.financialAdvisorAgent = financialAdvisorAgent;
	}
	public Boolean getHasTransaction() {
		return hasTransaction;
	}
	public void setHasTransaction(Boolean hasTransaction) {
		this.hasTransaction = hasTransaction;
	}


	public String getFinAdvisorFeeCcy() {
		return finAdvisorFeeCcy;
	}
	public void setFinAdvisorFeeCcy(String finAdvisorFeeCcy) {
		this.finAdvisorFeeCcy = finAdvisorFeeCcy;
	}
	public AgentDTO getBrokerAgent() {
		return brokerAgent;
	}
	public void setBrokerAgent(AgentDTO brokerAgent) {
		this.brokerAgent = brokerAgent;
	}
	public Boolean getUcits() {
		return ucits;
	}
	public void setUcits(Boolean ucits) {
		this.ucits = ucits;
	}
	public BigDecimal getMaxAllocationA() {
		return maxAllocationA;
	}
	public void setMaxAllocationA(BigDecimal maxAllocationA) {
		this.maxAllocationA = maxAllocationA;
	}
	public BigDecimal getMaxAllocationB() {
		return maxAllocationB;
	}
	public void setMaxAllocationB(BigDecimal maxAllocationB) {
		this.maxAllocationB = maxAllocationB;
	}
	public BigDecimal getMaxAllocationC() {
		return maxAllocationC;
	}
	public void setMaxAllocationC(BigDecimal maxAllocationC) {
		this.maxAllocationC = maxAllocationC;
	}
	public BigDecimal getMaxAllocationD() {
		return maxAllocationD;
	}
	public void setMaxAllocationD(BigDecimal maxAllocationD) {
		this.maxAllocationD = maxAllocationD;
	}
	public BigDecimal getMaxAllocationN() {
		return maxAllocationN;
	}
	public void setMaxAllocationN(BigDecimal maxAllocationN) {
		this.maxAllocationN = maxAllocationN;
	}
	public Integer getExportedFund() {
		return exportedFund;
	}
	public void setExportedFund(Integer exportedFund) {
		this.exportedFund = exportedFund;
	}
	/**
	 * @return the warns
	 */
	public Collection<String> getWarns() {
		return warns;
	}
	/**
	 * @param warns the warns to set
	 */
	public void setWarns(Collection<String> warns) {
		this.warns = warns;
	}

}
