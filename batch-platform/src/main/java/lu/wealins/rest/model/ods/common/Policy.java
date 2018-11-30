package lu.wealins.rest.model.ods.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Policy exposed into a web service response
 * 
 * @author xqv60
 *
 */
public class Policy {
	/**
	 * The policy id
	 */
	private String code;

	/**
	 * The FID number
	 */
	private String fidNumber;

	/**
	 * The actors (Holders)
	 */
	private List<Actor> holders;

	/**
	 * The actors (Transferees)
	 */
	private List<Actor> transferees;

	/**
	 * The actors (Beneficiaries)
	 */
	private List<Actor> beneficiaries;

	/**
	 * The initial contract
	 */
	private String initialContract;

	/**
	 * The status contract
	 */
	private String statusContract;

	/**
	 * The product name
	 */
	private String productName;
	/**
	 * The product nature
	 */
	private String productNature;

	/**
	 * The brokers
	 */
	private List<Broker> brokers;

	/**
	 * Contract number
	 */
	private String contractNumber;

	/**
	 * Effect date
	 */
	private Date effectDate;

	/**
	 * Duration
	 */
	private Integer duration;

	/**
	 * Language
	 */
	private String language;

	/**
	 * Source
	 */
	private String sourceName;

	/**
	 * Currency
	 */
	private String currency;

	/**
	 * Fax
	 */
	private String faxDischarge;

	/**
	 * Transfert mandate
	 */
	private String transfertMandate;

	/**
	 * the termination date
	 */
	private Date terminationDate;

	/**
	 * the subscription Country
	 */
	private String subscriptionCountry;

	/**
	 * the life Beneficiary Clause
	 */
	private String lifeBenefClause;

	/**
	 * the deathBenefClause
	 */
	private String deathBenefClause;

	/**
	 * the collaterization Flag
	 */
	private String collaterization;

	/**
	 * the general Conditions
	 */
	private String generalConditions;

	/**
	 * the correspondance address of the policy
	 */
	private List<CorrespondanceAddress> correspondanceAddress;

	/**
	 * Boolean to indicate if an event is not OK (not valorized/validated/on going)
	 */
	private boolean eventInProgress;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the fidNumber
	 */
	public String getFidNumber() {
		return fidNumber;
	}

	/**
	 * @param fidNumber the fidNumber to set
	 */
	public void setFidNumber(String fidNumber) {
		this.fidNumber = fidNumber;
	}

	/**
	 * @return the statusContract
	 */
	public String getStatusContract() {
		return statusContract;
	}

	/**
	 * @param statusContract the statusContract to set
	 */
	public void setStatusContract(String statusContract) {
		this.statusContract = statusContract;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productNature
	 */
	public String getProductNature() {
		return productNature;
	}

	/**
	 * @param productNature the productNature to set
	 */
	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}

	/**
	 * @return the brokers
	 */
	public List<Broker> getBrokers() {
		if (brokers == null) {
			brokers = new ArrayList<>();
		}
		return brokers;
	}

	/**
	 * @param brokers the brokers to set
	 */
	public void setBrokers(List<Broker> brokers) {
		this.brokers = brokers;
	}

	/**
	 * @return the holders
	 */
	public List<Actor> getHolders() {
		return holders;
	}

	/**
	 * @param holders the holders to set
	 */
	public void setHolders(List<Actor> holders) {
		this.holders = holders;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the effectDate
	 */
	public Date getEffectDate() {
		return effectDate;
	}

	/**
	 * @param effectDate the effectDate to set
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the transfertMandate
	 */
	public String getTransfertMandate() {
		return transfertMandate;
	}

	/**
	 * @param transfertMandate the transfertMandate to set
	 */
	public void setTransfertMandate(String transfertMandate) {
		this.transfertMandate = transfertMandate;
	}

	/**
	 * @return the terminationDate
	 */
	public Date getTerminationDate() {
		return terminationDate;
	}

	/**
	 * @param terminationDate the terminationDate to set
	 */
	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	/**
	 * @return the subscriptionCountry
	 */
	public String getSubscriptionCountry() {
		return subscriptionCountry;
	}

	/**
	 * @param subscriptionCountry the subscriptionCountry to set
	 */
	public void setSubscriptionCountry(String subscriptionCountry) {
		this.subscriptionCountry = subscriptionCountry;
	}

	/**
	 * @return the lifeBenefClause
	 */
	public String getLifeBenefClause() {
		return lifeBenefClause;
	}

	/**
	 * @param lifeBenefClause the lifeBenefClause to set
	 */
	public void setLifeBenefClause(String lifeBenefClause) {
		this.lifeBenefClause = lifeBenefClause;
	}

	/**
	 * @return the deathBenefClause
	 */
	public String getDeathBenefClause() {
		return deathBenefClause;
	}

	/**
	 * @param deathBenefClause the deathBenefClause to set
	 */
	public void setDeathBenefClause(String deathBenefClause) {
		this.deathBenefClause = deathBenefClause;
	}

	/**
	 * @return the generalConditions
	 */
	public String getGeneralConditions() {
		return generalConditions;
	}

	/**
	 * @param generalConditions the generalConditions to set
	 */
	public void setGeneralConditions(String generalConditions) {
		this.generalConditions = generalConditions;
	}

	/**
	 * @return the initialContract
	 */
	public String getInitialContract() {
		return initialContract;
	}

	/**
	 * @param initialContract the initialContract to set
	 */
	public void setInitialContract(String initialContract) {
		this.initialContract = initialContract;
	}

	/**
	 * @return the transferees
	 */
	public List<Actor> getTransferees() {
		return transferees;
	}

	/**
	 * @param transferees the transferees to set
	 */
	public void setTransferees(List<Actor> transferees) {
		this.transferees = transferees;
	}

	/**
	 * @return the beneficiaries
	 */
	public List<Actor> getBeneficiaries() {
		return beneficiaries;
	}

	/**
	 * @param beneficiaries the beneficiaries to set
	 */
	public void setBeneficiaries(List<Actor> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getFaxDischarge() {
		return faxDischarge;
	}

	public void setFaxDischarge(String faxDischarge) {
		this.faxDischarge = faxDischarge;
	}

	public String getCollaterization() {
		return collaterization;
	}

	public void setCollaterization(String collaterization) {
		this.collaterization = collaterization;
	}

	public List<CorrespondanceAddress> getCorrespondanceAddress() {
		return correspondanceAddress;
	}

	public void setCorrespondanceAddress(List<CorrespondanceAddress> correspondanceAddress) {
		this.correspondanceAddress = correspondanceAddress;
	}

	public boolean isEventInProgress() {
		return eventInProgress;
	}

	public void setEventInProgress(boolean eventInProgress) {
		this.eventInProgress = eventInProgress;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Policy other = (Policy) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
