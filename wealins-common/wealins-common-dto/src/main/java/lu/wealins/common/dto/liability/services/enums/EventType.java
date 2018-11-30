package lu.wealins.common.dto.liability.services.enums;

// See EVENTS table
public enum EventType {

	PREMIUM_PAYMENT(2, "Premium Payment"), CASH_RECEIVED(7, "Cash Received"), ADMINISTRATION_FEE(12, "Administration Fee"), SURRENDER(4, "Surrender");

	private Integer evtId;
	private String name;

	private EventType(Integer evtId, String name) {
		this.evtId = evtId;
		this.name = name;
	}

	public Integer getEvtId() {
		return evtId;
	}

	public void setEvtId(Integer evtId) {
		this.evtId = evtId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}