package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.OrderDTO;

/**
 * Extract order object used for the extract order batch
 * 
 * @author xqv60
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractOrderResponse {

	/**
	 * The list of estimated order from DALI
	 */
	private List<OrderDTO> daliEstimatedOrder = new ArrayList<>();

	/**
	 * The list of valorized order from DALI
	 */
	private List<OrderDTO> daliValorizedOrder = new ArrayList<>();

	/**
	 * The list of estimated order from LISSIA
	 */
	private List<OrderDTO> lissiaEstimatedOrder = new ArrayList<>();

	/**
	 * The list of valorized order from LISSIA
	 */
	private List<OrderDTO> lissiaValorizedOrder = new ArrayList<>();

	/**
	 * @return the daliEstimatedOrder
	 */
	public List<OrderDTO> getDaliEstimatedOrder() {
		if (this.daliEstimatedOrder == null) {
			daliEstimatedOrder = new ArrayList<>();
		}
		return daliEstimatedOrder;
	}

	/**
	 * @param daliEstimatedOrder the daliEstimatedOrder to set
	 */
	public void setDaliEstimatedOrder(List<OrderDTO> daliEstimatedOrder) {
		this.daliEstimatedOrder = daliEstimatedOrder;
	}

	/**
	 * @return the daliValorizedOrder
	 */
	public List<OrderDTO> getDaliValorizedOrder() {
		if (this.daliValorizedOrder == null) {
			daliValorizedOrder = new ArrayList<>();
		}
		return daliValorizedOrder;
	}

	/**
	 * @param daliValorizedOrder the daliValorizedOrder to set
	 */
	public void setDaliValorizedOrder(List<OrderDTO> daliValorizedOrder) {
		this.daliValorizedOrder = daliValorizedOrder;
	}

	/**
	 * @return the lissiaEstimatedOrder
	 */
	public List<OrderDTO> getLissiaEstimatedOrder() {
		if (this.lissiaEstimatedOrder == null) {
			lissiaEstimatedOrder = new ArrayList<>();
		}
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
		if (this.lissiaValorizedOrder == null) {
			lissiaValorizedOrder = new ArrayList<>();
		}
		return lissiaValorizedOrder;
	}

	/**
	 * @param lissiaValorizedOrder the lissiaValorizedOrder to set
	 */
	public void setLissiaValorizedOrder(List<OrderDTO> lissiaValorizedOrder) {
		this.lissiaValorizedOrder = lissiaValorizedOrder;
	}
}
