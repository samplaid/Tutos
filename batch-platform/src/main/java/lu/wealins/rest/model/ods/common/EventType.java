package lu.wealins.rest.model.ods.common;

/**
 * Type of a role Model returned as component of a service response
 * 
 * @author bqv55
 *
 */
public enum EventType {

	IADJU, IPREA, IPREM, MADMF, MDIVI, MMORT, MPROF, ODEAT, OMATU, OSURR, OWITH, RNPOL, SWITI, SWITO;

	public static final EventType[] FOR_HISTORY = new EventType[] { IADJU, IPREA, IPREM, MADMF, MDIVI, MMORT, MPROF, OMATU, OSURR, OWITH, SWITI, SWITO, ODEAT };
	public static final EventType[] FOR_CLIENT_EVENT_HISTORY = new EventType[] { IADJU, IPREA, IPREM, MPROF, OMATU, OSURR, OWITH, SWITI, SWITO, ODEAT };
	public static final EventType[] FOR_CLIENT_EVENT_HISTORY_NEGATIVE = new EventType[] { OSURR, OWITH };
	public static final EventType[] FOR_DASHBOARD_EVENTS = new EventType[] { IADJU, IPREA, IPREM, MDIVI, MPROF, OMATU, OSURR, OWITH, SWITI, SWITO };

	public static final EventType[] FOR_ACCOUNT_STATEMENT = new EventType[] { IADJU, IPREA, IPREM, MDIVI, MPROF, ODEAT, OMATU, OSURR, OWITH, RNPOL, SWITI, SWITO };

	public static final EventType[] FOR_EVENTS_DETAIL = new EventType[] { MDIVI, IPREM, IPREA, IADJU, SWITI, OWITH, SWITO, MMORT, MADMF, MPROF, ODEAT };

	public static final EventType[] FOR_PORTFOLIO_EVENT_HISTORY = new EventType[] { IPREA, IPREM, SWITI };
	public static final EventType[] FOR_PORTFOLIO_TG_EVENT_HISTORY = new EventType[] { IPREA, IPREM };
}
