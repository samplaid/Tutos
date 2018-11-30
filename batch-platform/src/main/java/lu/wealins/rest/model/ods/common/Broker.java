package lu.wealins.rest.model.ods.common;

/**
 * The broker exposed
 * 
 * @author xqv60
 *
 */
public class Broker {

	/**
	 * The broker code
	 */
	private String code;

	/**
	 * The network code = master_broker_id
	 */
	private String networkCode;

	/**
	 * Broker's name
	 */
	private String name;

	/**
	 * The network name = mst_broker_nm
	 */
	private String networkName;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the networkCode
	 */
	public String getNetworkCode() {
		return networkCode;
	}

	/**
	 * @param networkCode the networkCode to set
	 */
	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

}
