package lu.wealins.common.dto.liability.services.drm;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DRMEmailDTO implements Serializable {

	private String email_address;
	private boolean primary_address;

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public boolean isPrimary_address() {
		return primary_address;
	}

	public void setPrimary_address(boolean primary_address) {
		this.primary_address = primary_address;
	}

}
