package lu.wealins.liability.services.core.utils.constantes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constantes {
	public static final int[] EVENT_TYPE_LIST = { 0, 1, 2, 3, 4, 6, 7, 8, 12, 13, 15, 17, 19, 20, 21, 31, 37, 38, 42, 44, 71 };

	public static final int[] BUY_EVENT_TYPE_LIST = { 38, 44, 8 };
	public static final int[] SELL_EVENT_TYPE_LIST = { 37, 17, 15, 4 };
	public static final int[] OUT_EVENT_TYPE_LIST = { 12, 13, 3, 71 };
	
	public static final int[] COMMISSION_EVENT_SUB_TYPE_LIST = { 2, 4, 12, 13, 15, 19, 21};
	public static final int[] REPORT_EVENT_TYPE_LIST = { 2, 4, 12, 13, 15, 17, 19, 21, 37, 38 };
	
	public static final List<String> ACCOUNT_TYPE_COMMISSION_LIST = Arrays.asList("AGENTBAL");
	
	public static final List<String> FUND_SUB_TYPE_LIST = Arrays.asList("FID", "FAS");
	
	public static final List<String> ACCOUNT_TYPE_REPORT_LIST = Arrays.asList("ULSWI", "ULSWO", "DTHBEN", "ULDTF", "MATBEN", "ULWDPEN", "SURR", "CASHSUSP", "AGENTBAL");
	
	public static final List<String> SAP_MAPPING_BK_LIST = Arrays.asList("12ULDTF", "13ULDTF", "15ULWDPEN", "17DTHBEN", "2012ULDTF", "2013ULDTF", "2015ULWDPEN", "2017DTHBEN", "2021MATBEN", "202CASHSUSP", "2037ULSWO", "2038ULSWI", "204SURR", "21MATBEN", "2CASHSUSP", "37ULSWO", "38ULSWI", "4SURR");
	
	public static final Map<Integer, String> eventTypesTransco;

	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	static {
		Map<Integer, String> events = new HashMap<Integer, String>();
		//events.put(1, "??");
		//events.put(2, "CASHSUSP");
		//events.put(3, "ENTRY");
		events.put(4, "SURR");
		//events.put(12, "ULDTF");
		events.put(13, "ULDTF");
		events.put(15, "ULWDPEN");
		//events.put(16, "??");
		//events.put(17, "DTHBEN");
		events.put(19, "ULSWO");
		events.put(21, "MATBEN");
		//events.put(37, "ULSWO");
		//events.put(38, "ULSWI");
		eventTypesTransco = Collections.unmodifiableMap(events);
	}
	
	
	public static final int DEFAULT_EXTRACT_LISSIA_PAGE_SIZE = 20000;

	public static final Map<Integer, String> eventTypes;
	static {
		Map<Integer, String> events = new HashMap<Integer, String>();
		events.put(38, "SWITI");
		events.put(37, "SWITO");
		events.put(17, "ODEAT");
		events.put(44, "UADJU");
		events.put(12, "MADMF");
		events.put(13, "MADMF");
		events.put(3, "MMORT");
		events.put(15, "OWITH");
		events.put(4, "OSURR");
		events.put(8, "PREAL");
		events.put(44, "UADJU");
		events.put(71, "MADMF");
		eventTypes = Collections.unmodifiableMap(events);
	}

	public static final String[] FID_FUND_SUB_TYPE = { "FID", "FAS" };

	public static final int FAKE_CLIENT_ID = 999999;
	
	public static final String AUDITING_PROCESS_NAME = "LIABILITY-SERV";

	public static final int WS_LISSIA_EXPECTED_CODE = 99999;
}
