package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InjectSapAccountingsForMathematicalReserveResponse {


	private boolean success;
	
	private int numberOfSapAccountingInserted;
	
	private int numberOfReversalSapAccountingInserted;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the numberOfSapAccountingInserted
	 */
	public int getNumberOfSapAccountingInserted() {
		return numberOfSapAccountingInserted;
	}

	/**
	 * @param numberOfSapAccountingInserted the numberOfSapAccountingInserted to set
	 */
	public void setNumberOfSapAccountingInserted(int numberOfSapAccountingInserted) {
		this.numberOfSapAccountingInserted = numberOfSapAccountingInserted;
	}

	/**
	 * @return the numberOfReversalSapAccountingInserted
	 */
	public int getNumberOfReversalSapAccountingInserted() {
		return numberOfReversalSapAccountingInserted;
	}

	/**
	 * @param numberOfReversalSapAccountingInserted the numberOfReversalSapAccountingInserted to set
	 */
	public void setNumberOfReversalSapAccountingInserted(int numberOfReversalSapAccountingInserted) {
		this.numberOfReversalSapAccountingInserted = numberOfReversalSapAccountingInserted;
	}
	
	

}
