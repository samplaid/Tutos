package lu.wealins.common.dto.liability.services.enums;

public enum InterfaceLissiaType {

	SAP_ACCOUNTING("SAP_ACCOUNTING"), COMMISSION_TO_PAY("COMMISSION_TO_PAY"), REPORT_BIL("REPORT_BIL");

	private String type;

	private InterfaceLissiaType(String type) {
		this.type = type;
	}

	public String getInterfaceLissiaType() {
		return type;
	}

}
