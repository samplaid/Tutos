package lu.wealins.common.dto.liability.services.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//See STATUS_FTR Option
@JsonIgnoreProperties(ignoreUnknown = true)
public enum FundTransactionStatus {

	POSTED(1, "Posted"), 
	CANCELLED(2, "Cancelled"), 
	AWAITING_PRICE_PSTG(3, "Awaiting Price/Pstg"), 
	AWAITING_FWD_REP(4, "Awaiting Fwd Rep"), 
	REVERSED(5, "Reversed"), 
	AWAITING_LINKAGE(6, "Awaiting Linkage"), 
	CANCELLED_REV(7, "Cancelled(Rev)"), 
	LOCKED_AWAIT_PRICE(8, "Locked (Await price)"), 
	STATEMENT(9, "Statement"), 
	AWAITING_PRICE_PSTG_FP(10, "Awaiting Price/Pstg[FP]"), 
	DELETED_FP(11, "Deleted[FP]");

	private final Integer status;
	private final String description;

	public static Map<Integer, FundTransactionStatus> FUND_TRANSACTION_STATUS_MAP = Arrays.stream(values()).collect(Collectors.toMap(x -> x.getStatus(), y -> y));

	private FundTransactionStatus(Integer status, String description) {
		this.status = status;
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public static FundTransactionStatus toFundTransactionStatus(Integer status) {
		return FUND_TRANSACTION_STATUS_MAP.get(status);
	}

}
