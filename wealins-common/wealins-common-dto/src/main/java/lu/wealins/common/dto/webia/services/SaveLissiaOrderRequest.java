package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.OrderDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveLissiaOrderRequest {
	/**
	 * The list of estimated order from LISSIA
	 */
	private List<OrderDTO> lissiaEstimatedOrder = new ArrayList<>();

	/**
	 * The list of valorized order from LISSIA
	 */
	private List<OrderDTO> lissiaValorizedOrder = new ArrayList<>();

	/**
	 * @return the lissiaEstimatedOrder
	 */
	public List<OrderDTO> getLissiaEstimatedOrder() {
		return lissiaEstimatedOrder;
	}

	/**
	 * @param lissiaEstimatedOrder the lissiaEstimatedOrder to set
	 */
	public void setLissiaEstimatedOrder(List<OrderDTO> lissiaEstimatedOrder) {
		this.lissiaEstimatedOrder = lissiaEstimatedOrder;
	}

	/**
	 * @return the lissiaValorizedOrder
	 */
	public List<OrderDTO> getLissiaValorizedOrder() {
		return lissiaValorizedOrder;
	}

	/**
	 * @param lissiaValorizedOrder the lissiaValorizedOrder to set
	 */
	public void setLissiaValorizedOrder(List<OrderDTO> lissiaValorizedOrder) {
		this.lissiaValorizedOrder = lissiaValorizedOrder;
	}

}
