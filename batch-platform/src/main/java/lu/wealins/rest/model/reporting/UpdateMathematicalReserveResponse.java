package lu.wealins.rest.model.reporting;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateMathematicalReserveResponse {


	/**
	 * The list of the mathematical reserve to save
	 */
	private List<Integer> mathematicalReserveIdsList;

	public List<Integer> getMathematicalReserveIdsList() {
		return mathematicalReserveIdsList;
	}

	public void setMathematicalReserveIdsList(List<Integer> mathematicalReserveIdsList) {
		this.mathematicalReserveIdsList = mathematicalReserveIdsList;
	}


	
	

}
