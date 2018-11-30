package lu.wealins.common.dto.webia.services;

import java.io.Serializable;

public class SapMappingDTO implements Serializable {

	private String type;

	private String dataIn;

	private String dataOut;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataIn() {
		return dataIn;
	}

	public void setDataIn(String dataIn) {
		this.dataIn = dataIn;
	}

	public String getDataOut() {
		return dataOut;
	}

	public void setDataOut(String dataOut) {
		this.dataOut = dataOut;
	}

}
