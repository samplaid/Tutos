package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

public class CodeLabelEntityId implements Serializable {

	private static final long serialVersionUID = 2509775837153179717L;

	private String typeCd;

	private String code;

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
