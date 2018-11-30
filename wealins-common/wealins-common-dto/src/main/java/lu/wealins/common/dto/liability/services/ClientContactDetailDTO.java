package lu.wealins.common.dto.liability.services;

import java.util.Date;

public class ClientContactDetailDTO {

	private static final String CO = "C/o ";

	private String clcId;
	private String cliId;
	private String state;
	private String salutation;
	private String createdProcess;
	private String modifyProcess;
	private int status;
	private String createdBy;
	private Date createdDate;
	private String contactType;
	private Date createdTime;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String town;
	private String county;
	private String country;
	private String countryCode;
	private String postcode;
	private String fax;
	private String homePhone;
	private String email;
	private Date dateOfCountryChange;
	private String previousCountry;
	private String blockAddress;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getClcId() {
		return clcId;
	}

	public void setClcId(String clcId) {
		this.clcId = clcId;
	}

	public String getCliId() {
		return cliId;
	}

	public void setCliId(String cliId) {
		this.cliId = cliId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
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

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfCountryChange() {
		return dateOfCountryChange;
	}

	public void setDateOfCountryChange(Date dateOfCountryChange) {
		this.dateOfCountryChange = dateOfCountryChange;
	}

	public String getPreviousCountry() {
		return previousCountry;
	}

	public void setPreviousCountry(String previousCountry) {
		this.previousCountry = previousCountry;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBlockAddress() {
		blockAddress = "";

		// Crystal report needs the address line 1 starting with "C/o", but this data is useless on the new reporting system
		// TODO : clean the database when Crystal report won't be use to create document and remove this check
		if (!addressLine1.isEmpty() && !addressLine1.startsWith(CO)) {
			blockAddress = addressLine1 + "\n";
		}
		if (!addressLine2.isEmpty()) {
			blockAddress += addressLine2 + "\n";
		}
		if (!addressLine3.isEmpty()) {
			blockAddress += addressLine3 + "\n";
		}

		// remove the 4th line because it's now filled with the mail address
		/*
		 * if (!addressLine4.isEmpty()) { blockAddress += addressLine4; }
		 */

		return blockAddress;
	}

	public void setBlockAddress(String blockAddress) {
		this.blockAddress = blockAddress;
	}

}
