package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveMathematicalReserveRequest {


	/**
	 * The list of the mathematical reserve to save
	 */
	private List<MathematicalReserveDTO> mathematicalReserveList = new ArrayList<>();

	/**
	 * @return the mathematicalReserveList
	 */
	public List<MathematicalReserveDTO> getMathematicalReserveList() {
		return mathematicalReserveList;
	}

	/**
	 * @param mathematicalReserveList the mathematicalReserveList to set
	 */
	public void setMathematicalReserveList(List<MathematicalReserveDTO> mathematicalReserveList) {
		this.mathematicalReserveList = mathematicalReserveList;
	}

	
	

}
