package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "CODE_LABEL")
@IdClass(CodeLabelEntityId.class)
public class CodeLabelEntity implements Serializable {

	private static final long serialVersionUID = 7140829689463959968L;

	@Id
	@Column(name = "TYPE_CD")
	private String typeCd;

	@Id
	@Column(name = "CODE")
	private String code;

	@Id
	@Column(name = "LABEL")
	private String label;

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
