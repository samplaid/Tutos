package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO {

	private String ctyId;
	private String name;
	private int status;
	private String nationality;
	private Integer isoNumber;
	private String carPlateCode;	
	private String ptCode;
	private String riskCategory;
	private String isoCode2Pos;
	private String isoNameFrench;
	private String isoNameEnglish;

	public String getCtyId() {
		return ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Integer getIsoNumber() {
		return isoNumber;
	}

	public void setIsoNumber(Integer isoNumber) {
		this.isoNumber = isoNumber;
	}

	public String getCarPlateCode() {
		return carPlateCode;
	}

	public void setCarPlateCode(String carPlateCode) {
		this.carPlateCode = carPlateCode;
	}

	public String getPtCode() {
		return ptCode;
	}

	public void setPtCode(String ptCode) {
		this.ptCode = ptCode;
	}

	/**
	 * @return the riskCategory
	 */
	public String getRiskCategory() {
		return riskCategory;
	}

	/**
	 * @param riskCategory the riskCategory to set
	 */
	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
	}

	/**
	 * @return the isoCode2pos
	 */
	public String getIsoCode2Pos() {
		return isoCode2Pos;
	}

	/**
	 * @param isoCode2pos the isoCode2pos to set
	 */
	public void setIsoCode2Pos(String isoCode2Pos) {
		this.isoCode2Pos = isoCode2Pos;
	}

	/**
	 * @return the isoNameFrench
	 */
	public String getIsoNameFrench() {
		return isoNameFrench;
	}

	/**
	 * @param isoNameFrench the isoNameFrench to set
	 */
	public void setIsoNameFrench(String isoNameFrench) {
		this.isoNameFrench = isoNameFrench;
	}

	/**
	 * @return the isoNameEnglish
	 */
	public String getIsoNameEnglish() {
		return isoNameEnglish;
	}

	/**
	 * @param isoNameEnglish the isoNameEnglish to set
	 */
	public void setIsoNameEnglish(String isoNameEnglish) {
		this.isoNameEnglish = isoNameEnglish;
	}
	
	

}
