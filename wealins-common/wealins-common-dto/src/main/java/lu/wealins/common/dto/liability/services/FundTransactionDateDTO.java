package lu.wealins.common.dto.liability.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FundTransactionDateDTO {

	private String ftdId;
	private String fdsId;
	private Date date0;
	private int unitTypes;
	private Integer transactionsWaiting;
	private String fundId;
	private int status;
	private String createdBy;
	private Date createdDate;
	private Date createdTime;
	private Date createdSystemDate;
	private String createdProcess;
	private String modifyBy;
	private Date modifyDate;
	private Date modifyTime;
	private Date modifySystemDate;
	private String modifyProcess;
	private Set<FundTransactionDTO> fundTransactions = new HashSet<FundTransactionDTO>(0);

	public String getFtdId() {
		return ftdId;
	}

	public void setFtdId(String ftdId) {
		this.ftdId = ftdId;
	}

	public String getFdsId() {
		return fdsId;
	}

	public void setFdsId(String fdsId) {
		this.fdsId = fdsId;
	}

	public Date getDate0() {
		return date0;
	}

	public void setDate0(Date date0) {
		this.date0 = date0;
	}

	public int getUnitTypes() {
		return unitTypes;
	}

	public void setUnitTypes(int unitTypes) {
		this.unitTypes = unitTypes;
	}

	public Integer getTransactionsWaiting() {
		return transactionsWaiting;
	}

	public void setTransactionsWaiting(Integer transactionsWaiting) {
		this.transactionsWaiting = transactionsWaiting;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getCreatedSystemDate() {
		return createdSystemDate;
	}

	public void setCreatedSystemDate(Date createdSystemDate) {
		this.createdSystemDate = createdSystemDate;
	}

	public String getCreatedProcess() {
		return createdProcess;
	}

	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifySystemDate() {
		return modifySystemDate;
	}

	public void setModifySystemDate(Date modifySystemDate) {
		this.modifySystemDate = modifySystemDate;
	}

	public String getModifyProcess() {
		return modifyProcess;
	}

	public void setModifyProcess(String modifyProcess) {
		this.modifyProcess = modifyProcess;
	}

	public Set<FundTransactionDTO> getFundTransactions() {
		return fundTransactions;
	}

	public void setFundTransactions(Set<FundTransactionDTO> fundTransactions) {
		this.fundTransactions = fundTransactions;
	}


}
