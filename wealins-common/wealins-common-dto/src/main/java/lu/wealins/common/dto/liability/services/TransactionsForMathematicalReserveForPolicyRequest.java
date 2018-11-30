package lu.wealins.common.dto.liability.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsForMathematicalReserveForPolicyRequest {

	private List<String> policyIds = new ArrayList<>();
	
	private Date date;
	
	private int pageNum;
	
	private int pageSize;
	

	

	/**
	 * @return the policyIds
	 */
	public List<String> getPolicyIds() {
		return policyIds;
	}

	/**
	 * @param policyIds the policyIds to set
	 */
	public void setPolicyIds(List<String> policyIds) {
		this.policyIds = policyIds;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	
}
