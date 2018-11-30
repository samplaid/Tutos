package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;

public class SurrenderTotalReportDTO {
	private String eventType;
	private BigDecimal totalAmount;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
