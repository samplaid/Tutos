package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMethodsDTO {

	private String fundId;
	private Integer paymentMethod;

	public PaymentMethodsDTO() {
	}

	// Constructor used for spring-data mapping.
	public PaymentMethodsDTO(String fundId, Integer paymentMethod) {
		this.fundId = fundId;
		this.paymentMethod = paymentMethod;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
