package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckWorkflowDTO {

	private Integer checkId;

	private String checkDesc;

	private String checkExplain;

	private String checkType;

	private CheckDataDTO checkData;

	private String defaultValue;

	private String checkCode;

	private Collection<ScoreBCFTDTO> scoreBCFTs = new ArrayList<>();

	private String commentIf;

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	public String getCheckExplain() {
		return checkExplain;
	}

	public void setCheckExplain(String checkExplain) {
		this.checkExplain = checkExplain;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public CheckDataDTO getCheckData() {
		return checkData;
	}

	public void setCheckData(CheckDataDTO checkData) {
		this.checkData = checkData;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public Collection<ScoreBCFTDTO> getScoreBCFTs() {
		return scoreBCFTs;
	}

	public void setScoreBCFTs(Collection<ScoreBCFTDTO> scoreBCFTs) {
		this.scoreBCFTs = scoreBCFTs;
	}

	public String getCommentIf() {
		return commentIf;
	}

	public void setCommentIf(String commentIf) {
		this.commentIf = commentIf;
	}

}
