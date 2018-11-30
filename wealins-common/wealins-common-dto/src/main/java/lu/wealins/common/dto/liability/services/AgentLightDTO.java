package lu.wealins.common.dto.liability.services;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentLightDTO {

	private static final String CO = "c/o ";

	private String agtId;
	private String crmId;
	private String name;
	private String status;
	private String category;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String blockAddress;
	private String town;
	private String country;
	private String countryCode;
	private String postcode;
	private String telephone;
	private String fax;
	private String email;
	private String mobile;
	private Integer documentationLanguage;
	private String transcodedDocumentationLanguage;
	private String title;
	private String firstname;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agtId == null) ? 0 : agtId.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentLightDTO other = (AgentLightDTO) obj;
		if (agtId == null) {
			if (other.agtId != null)
				return false;
		} else if (!agtId.equals(other.agtId))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public String getBlockAddress() {
		blockAddress = "";

		// Crystal report needs the address line 1 starting with "C/o", but this data is useless on the new reporting system
		// TODO : clean the database when Crystal report won't be use to create document and remove this check
		if (!StringUtils.isEmpty(addressLine1) && !addressLine1.toLowerCase().startsWith(CO)) {
			blockAddress = addressLine1 + "\n";
		}
		if (!StringUtils.isEmpty(addressLine2)) {
			blockAddress += addressLine2 + "\n";
		}
		if (!StringUtils.isEmpty(addressLine3)) {
			blockAddress += addressLine3 + "\n";
		}
		if (!StringUtils.isEmpty(addressLine4)) {
			blockAddress += addressLine4;
		}

		return blockAddress;
	}

	public void setBlockAddress(String blockAddress) {
		this.blockAddress = blockAddress;
	}

	public String getAgtId() {
		return agtId;
	}

	public void setAgtId(String agtId) {
		this.agtId = agtId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getDocumentationLanguage() {
		return documentationLanguage;
	}

	public void setDocumentationLanguage(Integer documentationLanguage) {
		this.documentationLanguage = documentationLanguage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getTranscodedDocumentationLanguage() {
		return transcodedDocumentationLanguage;
	}

	public void setTranscodedDocumentationLanguage(String transcodedDocumentationLanguage) {
		this.transcodedDocumentationLanguage = transcodedDocumentationLanguage;
	}

	public String getCrmId() {
		return crmId;
	}

	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}

}
