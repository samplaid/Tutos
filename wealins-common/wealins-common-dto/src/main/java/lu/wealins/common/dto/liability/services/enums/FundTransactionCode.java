package lu.wealins.common.dto.liability.services.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum FundTransactionCode {

	WITHDRAWAL(15), SURRENDER(4);

	private final int code;

	private FundTransactionCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
