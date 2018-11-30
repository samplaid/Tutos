/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class holds informations which are used for the acceptance report.
 * 
 * @author oro
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptanceReportDTO implements Comparable<AcceptanceReportDTO> {
	private String labelDesciption;
	private int labelOrder;
	private String checkDesciption;
	private String dataValueYesNoNa;
	private String dataValueText;
	private BigDecimal dataValueNumber;
	private String commentIf;
	private Integer stepId;
	private Integer workflowItemId;
	private Integer checkOrder;
	private String score;
	private String checkType;
	private String checkCode;

	/**
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * @param checkCode the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * @return the checkType
	 */
	public String getCheckType() {
		return checkType;
	}

	/**
	 * @param checkType the checkType to set
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * @return the labelOrder
	 */
	public int getLabelOrder() {
		return labelOrder;
	}

	/**
	 * @param labelOrder the labelOrder to set
	 */
	public void setLabelOrder(int labelOrder) {
		this.labelOrder = labelOrder;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @return the checkOrder
	 */
	public Integer getCheckOrder() {
		return checkOrder;
	}

	/**
	 * @param checkOrder the checkOrder to set
	 */
	public void setCheckOrder(Integer checkOrder) {
		this.checkOrder = checkOrder;
	}

	/**
	 * @return the labelDesciption
	 */
	public String getLabelDesciption() {
		return labelDesciption;
	}

	/**
	 * @param labelDesciption the labelDesciption to set
	 */
	public void setLabelDesciption(String labelDesciption) {
		this.labelDesciption = labelDesciption;
	}

	/**
	 * @return the checkDesciption
	 */
	public String getCheckDesciption() {
		return checkDesciption;
	}

	/**
	 * @param checkDesciption the checkDesciption to set
	 */
	public void setCheckDesciption(String checkDesciption) {
		this.checkDesciption = checkDesciption;
	}

	/**
	 * @return the dataValueYesNoNa
	 */
	public String getDataValueYesNoNa() {
		return dataValueYesNoNa;
	}

	/**
	 * @param dataValueYesNoNa the dataValueYesNoNa to set
	 */
	public void setDataValueYesNoNa(String dataValueYesNoNa) {
		this.dataValueYesNoNa = dataValueYesNoNa;
	}

	/**
	 * @return the dataValueText
	 */
	public String getDataValueText() {
		return dataValueText;
	}

	/**
	 * @param dataValueText the dataValueText to set
	 */
	public void setDataValueText(String dataValueText) {
		this.dataValueText = dataValueText;
	}

	/**
	 * @return the dataValueNumber
	 */
	public BigDecimal getDataValueNumber() {
		return dataValueNumber;
	}

	/**
	 * @param dataValueNumber the dataValueNumber to set
	 */
	public void setDataValueNumber(BigDecimal dataValueNumber) {
		this.dataValueNumber = dataValueNumber;
	}

	/**
	 * @return the commentIf
	 */
	public String getCommentIf() {
		return commentIf;
	}

	/**
	 * @param commentIf the commentIf to set
	 */
	public void setCommentIf(String commentIf) {
		this.commentIf = commentIf;
	}

	/**
	 * @return the stepId
	 */
	public Integer getStepId() {
		return stepId;
	}

	/**
	 * @param stepId the stepId to set
	 */
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	/**
	 * @return the workflowItemId
	 */
	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	/**
	 * @param workflowItemId the workflowItemId to set
	 */
	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(AcceptanceReportDTO other) {

		if (other == null) {
			return 1;
		}

		int comp = compareInteger(this.stepId, other.stepId, true);

		if (comp != 0) {
			return comp;
		}

		comp = compareInteger(this.labelOrder, other.labelOrder, true);

		if (comp != 0) {
			return comp;
		}

		return compareInteger(this.checkOrder, other.checkOrder, true);
	}

	private int compareInteger(Integer i1, Integer i2, boolean useless) {
		if (i1 == i2) {
			return 0;
		}

		if (i1 == null) {
			return useless ? -1 : 1;
		}

		if (i2 == null) {
			return useless ? 1 : -1;
		}

		return i1.compareTo(i2);
	}

}
