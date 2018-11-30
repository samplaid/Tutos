package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelDTO {

	private Integer labelId;

	private String labelDesc;

	private Integer labelOrder;

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getLabelDesc() {
		return labelDesc;
	}

	public void setLabelDesc(String labelDesc) {
		this.labelDesc = labelDesc;
	}

	public Integer getLabelOrder() {
		return labelOrder;
	}

	public void setLabelOrder(Integer labelOrder) {
		this.labelOrder = labelOrder;
	}

}
