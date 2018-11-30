package lu.wealins.common.dto.liability.services;

public class ClientSearchRequest extends PageableSearchRequest{
	private String date;	
	private Integer type;	
	private Integer status; 
	private String name;
	private String firstName;
	private Integer code;
	private String maidenName;
	private Boolean includeDeceased;
		
	
	/**
	 * @return the includeDeceased
	 */
	public Boolean getIncludeDeceased() {
		return includeDeceased;
	}

	/**
	 * @param includeDeceased the includeDeceased to set
	 */
	public void setIncludeDeceased(Boolean includeDeceased) {
		this.includeDeceased = includeDeceased;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
}
