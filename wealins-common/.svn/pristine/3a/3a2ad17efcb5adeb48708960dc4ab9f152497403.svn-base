package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.OrderDTO;
import lu.wealins.common.dto.PageResult;

/**
 * Extract order object used for the extract order batch
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractOrderResponse {

	/**
	 * The list of valorized order
	 */
	private PageResult<OrderDTO> valorizedResult;

	/**
	 * The list of estimated order
	 */
	private PageResult<OrderDTO> estimatedResult;

	/**
	 * @return the valorizedResult
	 */
	public PageResult<OrderDTO> getValorizedResult() {
		return valorizedResult;
	}

	/**
	 * @param valorizedResult the valorizedResult to set
	 */
	public void setValorizedResult(PageResult<OrderDTO> valorizedResult) {
		this.valorizedResult = valorizedResult;
	}

	/**
	 * @return the estimatedResult
	 */
	public PageResult<OrderDTO> getEstimatedResult() {
		return estimatedResult;
	}

	/**
	 * @param estimatedResult the estimatedResult to set
	 */
	public void setEstimatedResult(PageResult<OrderDTO> estimatedResult) {
		this.estimatedResult = estimatedResult;
	}

}
