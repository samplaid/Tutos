package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.OrderDTO;

/**
 * Update order request
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderRequest {

	/**
	 * The estimated orders to update
	 */
	private List<OrderDTO> estimatedOrders = new ArrayList<>();

	/**
	 * The valorized orders to update
	 */
	private List<OrderDTO> valorizedOrders = new ArrayList<>();

	/**
	 * @return the estimatedOrders
	 */
	public List<OrderDTO> getEstimatedOrders() {
		if (estimatedOrders == null) {
			estimatedOrders = new ArrayList<>();
		}
		return estimatedOrders;
	}

	/**
	 * @param estimatedOrders the estimatedOrders to set
	 */
	public void setEstimatedOrders(List<OrderDTO> estimatedOrders) {
		this.estimatedOrders = estimatedOrders;
	}

	/**
	 * @return the valorizedOrders
	 */
	public List<OrderDTO> getValorizedOrders() {
		if (valorizedOrders == null) {
			valorizedOrders = new ArrayList<>();
		}
		return valorizedOrders;
	}

	/**
	 * @param valorizedOrders the valorizedOrders to set
	 */
	public void setValorizedOrders(List<OrderDTO> valorizedOrders) {
		this.valorizedOrders = valorizedOrders;
	}

}
