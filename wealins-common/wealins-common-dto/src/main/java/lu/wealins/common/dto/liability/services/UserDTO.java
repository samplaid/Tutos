package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

	/** The user id. */
	private String usrId;

	/** The name. */
	private String name;

	/** The email. */
	private String emailExtn;

	/** The fax. */
	private String faxExtn;

	/** The phone number. */
	private String telExtn;

	/****************************/
	/** Getters and Setters. */
	/****************************/

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailExtn() {
		return emailExtn;
	}

	public void setEmailExtn(String emailExtn) {
		this.emailExtn = emailExtn;
	}

	public String getFaxExtn() {
		return faxExtn;
	}

	public void setFaxExtn(String faxExtn) {
		this.faxExtn = faxExtn;
	}

	public String getTelExtn() {
		return telExtn;
	}

	public void setTelExtn(String telExtn) {
		this.telExtn = telExtn;
	}
}
