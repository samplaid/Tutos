package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 
 * @author xqv99
 *
 */
@Entity
@Table(name = "TRANSFERS")
public class TransferEntity extends AuditingEntity {

	private static final long serialVersionUID = -4654390049314045191L;

	@Id
	@Column(name = "TRANSFER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transferId;

	@Column(name = "TRANSFER_CD")
	private String transferCd;

	@Column(name = "TRANSFER_STATUS")
	private String transferStatus;

	@Column(name = "TRF_MT")
	private BigDecimal trfMt;

	@Column(name = "TRF_CURRENCY")
	private String trfCurrency;

	@Column(name = "TRF_DT")
	private Date trfDt;

	@Column(name = "TRF_COMM")
	private String trfComm;

	@Column(name = "IBANDONORD")
	private String ibanDonOrd;

	@Column(name = "SWIFTDONORD")
	private String swiftDonOrd;

	@Column(name = "LIBDONORD")
	private String libDonOrd;

	@Column(name = "IBANBENEF")
	private String ibanBenef;

	@Column(name = "SWIFTBENEF")
	private String swiftBenef;
	
	@Column(name = "LIBBENEF")
	private String libBenef;
	
	@Column(name = "TRANSFER_TYPE")
	private String transferType;

	@Column(name = "FILE_NM")
	private String fileNm;
	
	@Column(name = "STATEMENT_ID")
	private Long statementId;
	
	@Column(name = "BROKER_ID")
	private String brokerId;

	@Column(name = "WORKFLOW_ITEM_ID")
	private Integer workflowItemId;

	@Column(name = "USER_CPS1")
	private String userCps1;

	@Column(name = "USER_CPS2")
	private String userCps2;

	@Column(name = "USER_COMPTA")
	private String userCompta;

	@Column(name = "CPS1_DT")
	private Date cps1Dt;

	@Column(name = "CPS2_DT")
	private Date cps2Dt;

	@Column(name = "COMPTA_DT")
	private Date comptaDt;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("transferSecuritesId")
	@JoinColumn(name = "TRANSFER_ID")
	private Collection<TransferSecurityEntity> transferSecurities = new ArrayList<>();

	@Column(name = "REJECT_COMMENT")
	private String rejectComment;

	@Column(name = "POLICY_ID")
	private String policyId;

	@Column(name = "POLICY_OUT")
	private String policyOut;

	@Column(name = "FDS_ID")
	private String fdsId;

	@Column(name = "TRANSFER_SECURITIES_COMMENT")
	private String transferSecuritiesComment;

	@Column(name = "EDITING_ID")
	private Long editingId;

	public String getPolicyOut() {
		return policyOut;
	}

	public void setPolicyOut(String policyOut) {
		this.policyOut = policyOut;
	}

	public Long getEditingId() {
		return editingId;
	}

	public void setEditingId(Long editingId) {
		this.editingId = editingId;
	}

	public String getTransferSecuritiesComment() {
		return transferSecuritiesComment;
	}

	public void setTransferSecuritiesComment(String transferSecuritiesComment) {
		this.transferSecuritiesComment = transferSecuritiesComment;
	}

	public String getFdsId() {
		return fdsId;
	}

	public void setFdsId(String fdsId) {
		this.fdsId = fdsId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getRejectComment() {
		return rejectComment;
	}

	public void setRejectComment(String rejectComment) {
		this.rejectComment = rejectComment;
	}

	public Collection<TransferSecurityEntity> getTransferSecurities() {
		return transferSecurities;
	}

	public void setTransferSecurities(Collection<TransferSecurityEntity> transferSecurities) {
		this.transferSecurities = transferSecurities;
	}

	/**
	 * @return the transferId
	 */
	public Long getTransferId() {
		return transferId;
	}

	/**
	 * @param transferId the transferId to set
	 */
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	/**
	 * @return the transferCd
	 */
	public String getTransferCd() {
		return transferCd;
	}

	/**
	 * @param transferCd the transferCd to set
	 */
	public void setTransferCd(String transferCd) {
		this.transferCd = transferCd;
	}

	/**
	 * @return the transferStatus
	 */
	public String getTransferStatus() {
		return transferStatus;
	}

	/**
	 * @param transferStatus the transferStatus to set
	 */
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	/**
	 * @return the trfMt
	 */
	public BigDecimal getTrfMt() {
		return trfMt;
	}

	/**
	 * @param trfMt the trfMt to set
	 */
	public void setTrfMt(BigDecimal trfMt) {
		this.trfMt = trfMt;
	}

	/**
	 * @return the trfCurrency
	 */
	public String getTrfCurrency() {
		return trfCurrency;
	}

	/**
	 * @param trfCurrency the trfCurrency to set
	 */
	public void setTrfCurrency(String trfCurrency) {
		this.trfCurrency = trfCurrency;
	}

	/**
	 * @return the trfDt
	 */
	public Date getTrfDt() {
		return trfDt;
	}

	/**
	 * @param trfDt the trfDt to set
	 */
	public void setTrfDt(Date trfDt) {
		this.trfDt = trfDt;
	}

	/**
	 * @return the trfComm
	 */
	public String getTrfComm() {
		return trfComm;
	}

	/**
	 * @param trfComm the trfComm to set
	 */
	public void setTrfComm(String trfComm) {
		this.trfComm = trfComm;
	}

	/**
	 * @return the ibanDonOrd
	 */
	public String getIbanDonOrd() {
		return ibanDonOrd;
	}

	/**
	 * @param ibanDonOrd the ibanDonOrd to set
	 */
	public void setIbanDonOrd(String ibanDonOrd) {
		this.ibanDonOrd = ibanDonOrd;
	}

	/**
	 * @return the swiftDonOrd
	 */
	public String getSwiftDonOrd() {
		return swiftDonOrd;
	}

	/**
	 * @param swiftDonOrd the swiftDonOrd to set
	 */
	public void setSwiftDonOrd(String swiftDonOrd) {
		this.swiftDonOrd = swiftDonOrd;
	}

	/**
	 * @return the libDonOrd
	 */
	public String getLibDonOrd() {
		return libDonOrd;
	}

	/**
	 * @param libDonOrd the libDonOrd to set
	 */
	public void setLibDonOrd(String libDonOrd) {
		this.libDonOrd = libDonOrd;
	}

	/**
	 * @return the ibanBenef
	 */
	public String getIbanBenef() {
		return ibanBenef;
	}

	/**
	 * @param ibanBenef the ibanBenef to set
	 */
	public void setIbanBenef(String ibanBenef) {
		this.ibanBenef = ibanBenef;
	}

	/**
	 * @return the libBenef
	 */
	public String getLibBenef() {
		return libBenef;
	}

	/**
	 * @param libBenef the libBenef to set
	 */
	public void setLibBenef(String libBenef) {
		this.libBenef = libBenef;
	}

	/**
	 * @return the swiftBenef
	 */
	public String getSwiftBenef() {
		return swiftBenef;
	}

	/**
	 * @param swiftBenef the swiftBenef to set
	 */
	public void setSwiftBenef(String swiftBenef) {
		this.swiftBenef = swiftBenef;
	}

	/**
	 * @return the transferType
	 */
	public String getTransferType() {
		return transferType;
	}

	/**
	 * @param transferType the transferType to set
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return fileNm;
	}

	/**
	 * @param fileNm the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * @return the statementId
	 */
	public Long getStatementId() {
		return statementId;
	}

	/**
	 * @param statementId the statementId to set
	 */
	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}

	/**
	 * @return the brokerId
	 */
	public String getBrokerId() {
		return brokerId;
	}

	/**
	 * @param brokerId the brokerId to set
	 */
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public Integer getWorkflowItemId() {
		return workflowItemId;
	}

	public void setWorkflowItemId(Integer workflowItemId) {
		this.workflowItemId = workflowItemId;
	}

	public String getUserCps1() {
		return userCps1;
	}

	public void setUserCps1(String userCps1) {
		this.userCps1 = userCps1;
	}

	public String getUserCps2() {
		return userCps2;
	}

	public void setUserCps2(String userCps2) {
		this.userCps2 = userCps2;
	}

	public String getUserCompta() {
		return userCompta;
	}

	public void setUserCompta(String userCompta) {
		this.userCompta = userCompta;
	}

	public Date getCps1Dt() {
		return cps1Dt;
	}

	public void setCps1Dt(Date cps1Dt) {
		this.cps1Dt = cps1Dt;
	}

	public Date getCps2Dt() {
		return cps2Dt;
	}

	public void setCps2Dt(Date cps2Dt) {
		this.cps2Dt = cps2Dt;
	}

	public Date getComptaDt() {
		return comptaDt;
	}

	public void setComptaDt(Date comptaDt) {
		this.comptaDt = comptaDt;
	}
}
