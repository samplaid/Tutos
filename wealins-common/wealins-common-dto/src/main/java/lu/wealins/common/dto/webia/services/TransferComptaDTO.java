package lu.wealins.common.dto.webia.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.common.dto.liability.services.enums.PaymentMethod;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferComptaDTO extends TransferDTO {

	private PaymentMethod mode;

	public PaymentMethod getMode() {
		return mode;
	}

	public void setMode(PaymentMethod mode) {
		this.mode = mode;
	}
}
