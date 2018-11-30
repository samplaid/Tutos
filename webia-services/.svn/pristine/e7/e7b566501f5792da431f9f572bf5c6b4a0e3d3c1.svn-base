package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEQUENCE")
public class SequenceEntity implements Serializable {

	private static final long serialVersionUID = 9107048777123069777L;

	@Id
	@Column(name = "TARGET")
	private String target;

	@Column(name = "PREFIX")
	private String prefix;

	@Column(name = "SEQUENCE")
	private Integer sequence;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
