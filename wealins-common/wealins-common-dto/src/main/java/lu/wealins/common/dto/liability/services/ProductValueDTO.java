package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductValueDTO {

	private String prcId;
	private String control;
	private BigDecimal numericValue;
	private String alphaValue;
	private Integer subDataType;

	public String getPrcId() {
		return prcId;
	}

	public void setPrcId(String prcId) {
		this.prcId = prcId;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public BigDecimal getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(BigDecimal numericValue) {
		this.numericValue = numericValue;
	}

	public Integer getSubDataType() {
		return subDataType;
	}

	public void setSubDataType(Integer subDataType) {
		this.subDataType = subDataType;
	}

	public String getAlphaValue() {
		return alphaValue;
	}

	public void setAlphaValue(String alphaValue) {
		this.alphaValue = alphaValue;
	}
}
