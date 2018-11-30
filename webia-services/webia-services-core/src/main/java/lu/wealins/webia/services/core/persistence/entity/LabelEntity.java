package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LABEL")
public class LabelEntity implements Serializable {

	private static final long serialVersionUID = 7054611558057441244L;

	@Id
	@Column(name = "LABEL_ID")
	private Integer labelId;

	@Column(name = "LABEL_DESC")
	private String labelDesc;

	@Column(name = "LABEL_ORDER")
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
