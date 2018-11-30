package lu.wealins.liability.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "AGENT_BANK_ACCOUNTS")

public class AgentBankAccountEntity implements Serializable {
	private static final long serialVersionUID = -1370237614669066161L;

	@Id
	@Column(name = "AGB_ID")
	private Integer AgbId;
	@Column(name = "AGENT")
	private String agent0;
	@Column(name = "ACCOUNT_NAME")
	private String accountName;
	@Column(name = "IBAN")
	private String iban;
	@Column(name = "BIC")
	private String bic;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "BANK_ADDRESS1")
	private String bankAddress1;
	@Column(name = "BANK_ADDRESS2")
	private String bankAddress2;
	@Column(name = "BANK_ADDRESS3")
	private String bankAddress3;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "COMMISSION_TYPE")
	private String commissionType;
	@Column(name = "COMM_PAYMENT_TYPE")
	private String commPaymentType;
	@Column(name = "COMM_PAYMENT_CURRENCY")
	private String commPaymentCurrency;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME")
	private Date createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_PROCESS")
	private String createdProcess;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@Column(name = "MODIFIED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedTime;
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	@Column(name = "MODIFIED_PROCESS")
	private String modifiedProcess;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_AGENTSAGT_ID", referencedColumnName = "AGT_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private AgentEntity agent;

	@Column(name = "ACCOUNT_CURRENCY")
	private String accountCurrency;
	public Integer getAgbId() {
		return AgbId;
	}
	public void setAgbId(Integer agbId) {
		AgbId = agbId;
	}

	public String getAgent0() {
		return agent0;
	}

	public void setAgent0(String agent0) {
		this.agent0 = agent0;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress1() {
		return bankAddress1;
	}
	public void setBankAddress1(String bankAddress1) {
		this.bankAddress1 = bankAddress1;
	}
	public String getBankAddress2() {
		return bankAddress2;
	}
	public void setBankAddress2(String bankAddress2) {
		this.bankAddress2 = bankAddress2;
	}
	public String getBankAddress3() {
		return bankAddress3;
	}
	public void setBankAddress3(String bankAddress3) {
		this.bankAddress3 = bankAddress3;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	public String getCommPaymentType() {
		return commPaymentType;
	}
	public void setCommPaymentType(String commPaymentType) {
		this.commPaymentType = commPaymentType;
	}
	public String getCommPaymentCurrency() {
		return commPaymentCurrency;
	}
	public void setCommPaymentCurrency(String commPaymentCurrency) {
		this.commPaymentCurrency = commPaymentCurrency;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedProcess() {
		return createdProcess;
	}
	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedProcess() {
		return modifiedProcess;
	}
	public void setModifiedProcess(String modifiedProcess) {
		this.modifiedProcess = modifiedProcess;
	}
	public AgentEntity getAgent() {
		return agent;
	}
	public void setAgent(AgentEntity agent) {
		this.agent = agent;
	}
	public String getAccountCurrency() {
		return accountCurrency;
	}
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}
	
	
}
