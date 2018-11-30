package lu.wealins.common.dto.liability.services.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum TransactionCode {

	MORT(4), PRE_ALLOC(8), ADM_FEE(12), WITHDRAWAL(15), SURRENDER(3);

	private final int code;

	private TransactionCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
