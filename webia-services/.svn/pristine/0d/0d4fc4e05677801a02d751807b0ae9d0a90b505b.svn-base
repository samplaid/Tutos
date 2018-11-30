package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCORE_BCFT")
@IdClass(ScoreBCFTId.class)
public class ScoreBCFTEntity implements Serializable {

	private static final long serialVersionUID = -3522365169324094748L;

	@Id
	@Column(name = "CHECKCODE", length=5)
	private String checkCode;
	
	@Id
	@Column(name = "RESPONSE")
	private String response;

	@Column(name = "SCORE")
	private Integer score;
		

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
