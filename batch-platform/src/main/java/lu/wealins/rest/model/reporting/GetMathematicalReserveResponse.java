package lu.wealins.rest.model.reporting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.PageResult;
import lu.wealins.common.dto.webia.services.MathematicalReserveDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMathematicalReserveResponse {


	/**
	 * The list of the mathematical reserve to save
	 */
	private PageResult<MathematicalReserveDTO> mathematicalReserveList;

	/**
	 * @return the mathematicalReserveList
	 */
	public PageResult<MathematicalReserveDTO> getMathematicalReserveList() {
		return mathematicalReserveList;
	}

	/**
	 * @param mathematicalReserveList the mathematicalReserveList to set
	 */
	public void setMathematicalReserveList(PageResult<MathematicalReserveDTO> mathematicalReserveList) {
		this.mathematicalReserveList = mathematicalReserveList;
	}

	
	

}
