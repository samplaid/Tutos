package lu.wealins.common.dto.liability.services;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.webia.services.OrderFIDDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValorizedTransactionSearchResponse {

	
	List<OrderFIDDTO> orders;

	/**
	 * @return the orders
	 */
	public List<OrderFIDDTO> getOrders() {
		return orders;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrders(List<OrderFIDDTO> orders) {
		this.orders = orders;
	}


	
	

}
