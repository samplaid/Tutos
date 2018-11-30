package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentDataForTransferDTO {
	
	private String brokerId;
	private String currency;
	private String ibanDonord;
	private String swiftDonord;
	private String libDonord;
	private String ibanBenef;
	private String swiftBenef;
	private String libBenef;
	private String transferType;
		
	/**
	 * @return the brokerId
	 */
	public String getBrokerId() {
		return brokerId;
	}
	/**
	 * @param brokerId the brokerId to set
	 */
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
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
	 * @return the ibanDonord
	 */
	public String getIbanDonord() {
		return ibanDonord;
	}
	/**
	 * @param ibanDonord the ibanDonord to set
	 */
	public void setIbanDonord(String ibanDonord) {
		this.ibanDonord = ibanDonord;
	}
	/**
	 * @return the swiftDonord
	 */
	public String getSwiftDonord() {
		return swiftDonord;
	}
	/**
	 * @param swiftDonord the swiftDonord to set
	 */
	public void setSwiftDonord(String swiftDonord) {
		this.swiftDonord = swiftDonord;
	}
	/**
	 * @return the libDonord
	 */
	public String getLibDonord() {
		return libDonord;
	}
	/**
	 * @param libDonord the libDonord to set
	 */
	public void setLibDonord(String libDonord) {
		this.libDonord = libDonord;
	}
	/**
	 * @return the ibanBenef
	 */
	public String getIbanBenef() {
		return ibanBenef;
	}
	/**
	 * @param ibanBenef the ibanBenef to set
	 */
	public void setIbanBenef(String ibanBenef) {
		this.ibanBenef = ibanBenef;
	}
	/**
	 * @return the swiftBenef
	 */
	public String getSwiftBenef() {
		return swiftBenef;
	}
	/**
	 * @param swiftBenef the swiftBenef to set
	 */
	public void setSwiftBenef(String swiftBenef) {
		this.swiftBenef = swiftBenef;
	}
	/**
	 * @return the libBenef
	 */
	public String getLibBenef() {
		return libBenef;
	}
	/**
	 * @param libBenef the libBenef to set
	 */
	public void setLibBenef(String libBenef) {
		this.libBenef = libBenef;
	}
	/**
	 * @return the transfertType
	 */
	public String getTransferType() {
		return transferType;
	}
	/**
	 * @param transfertType the transfertType to set
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
}
