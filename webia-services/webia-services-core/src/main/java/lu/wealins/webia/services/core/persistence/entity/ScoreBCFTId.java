package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

public class ScoreBCFTId implements Serializable {

	private static final long serialVersionUID = 182516310227933361L;

	private String checkCode;

	private String response;

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
