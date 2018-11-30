package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the REPORTING_COM database table.
 */
@Entity
@Table(name = "REPORTING_COM")
public class ReportingComEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 165465874714L;
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "REPORTCOM_ID")
	private Long reportComId;
	
	@Column(name = "PERIOD")
	private String period;
	
	@Column(name = "POLICY")
	private String policy;

	@Column(name = "BROKER_POLICY")
	private String brokerPolicy;
	
	@Column(name = "PRODUCT_CD")
	private String productCd;
	
	@Column(name = "MVT_CD")
	private String mvtCd;
	
	@Column(name = "COM_DT")
	@Temporal(TemporalType.DATE)
	private Date comDt;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "BASE_AMT")
	private BigDecimal baseAmt;
	
	@Column(name = "SIGNBASE")
	private Character signbase;
	
	@Column(name = "COM_AMT")
	private BigDecimal comAmt;
	
	@Column(name = "SIGNCOM")
	private Character signcom;
	
	@Column(name = "CODE_ISIN")
	private String codeIsin;
	
	@Column(name = "ORIGIN")
	private String origin;
	
	@Column(name = "ORIGIN_ID")
	private Long originId;
	
	@Column(name = "EXPORT_DT")
	@Temporal(TemporalType.DATE)
	private Date exportDt;
	
	@Column(name = "REPORT_ID")
	private Long reportId;
	
	@Column(name = "CREATION_DT")
	@Temporal(TemporalType.DATE)
	private Date creationDt;
	
	@Transient
	private Long pstId;
	
	/**
	 * @return the reportComId
	 */
	public Long getReportComId() {
		return reportComId;
	}

	/**
	 * @param reportComId the reportComId to set
	 */
	public void setReportComId(Long reportComId) {
		this.reportComId = reportComId;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * @return the brokerPolicy
	 */
	public String getBrokerPolicy() {
		return brokerPolicy;
	}

	/**
	 * @param brokerPolicy the brokerPolicy to set
	 */
	public void setBrokerPolicy(String brokerPolicy) {
		this.brokerPolicy = brokerPolicy;
	}

	/**
	 * @return the productCd
	 */
	public String getProductCd() {
		return productCd;
	}

	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	/**
	 * @return the mvtCd
	 */
	public String getMvtCd() {
		return mvtCd;
	}

	/**
	 * @param mvtCd the mvtCd to set
	 */
	public void setMvtCd(String mvtCd) {
		this.mvtCd = mvtCd;
	}

	/**
	 * @return the comDt
	 */
	public Date getComDt() {
		return comDt;
	}

	/**
	 * @param comDt the comDt to set
	 */
	public void setComDt(Date comDt) {
		this.comDt = comDt;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the baseAmt
	 */
	public BigDecimal getBaseAmt() {
		return baseAmt;
	}

	/**
	 * @param baseAmt the baseAmt to set
	 */
	public void setBaseAmt(BigDecimal baseAmt) {
		this.baseAmt = baseAmt;
	}

	/**
	 * @return the signbase
	 */
	public Character getSignbase() {
		return signbase;
	}

	/**
	 * @param signbase the signbase to set
	 */
	public void setSignbase(Character signbase) {
		this.signbase = signbase;
	}

	/**
	 * @return the comAmt
	 */
	public BigDecimal getComAmt() {
		return comAmt;
	}

	/**
	 * @param bigDecimal the comAmt to set
	 */
	public void setComAmt(BigDecimal comAmt) {
		this.comAmt = comAmt;
	}

	/**
	 * @return the signcom
	 */
	public Character getSigncom() {
		return signcom;
	}

	/**
	 * @param signcom the signcom to set
	 */
	public void setSigncom(Character signcom) {
		this.signcom = signcom;
	}
	
	/**
	 * @return the codeIsin
	 */
	public String getCodeIsin() {
		return codeIsin;
	}

	/**
	 * @param codeIsin the codeIsin to set
	 */
	public void setCodeIsin(String codeIsin) {
		this.codeIsin = codeIsin;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the originId
	 */
	public Long getOriginId() {
		return originId;
	}

	/**
	 * @param originId the originId to set
	 */
	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	/**
	 * @return the exportDt
	 */
	public Date getExportDt() {
		return exportDt;
	}

	/**
	 * @param exportDt the exportDt to set
	 */
	public void setExportDt(Date exportDt) {
		this.exportDt = exportDt;
	}

	/**
	 * @return the reportId
	 */
	public Long getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the creationDt
	 */
	public Date getCreationDt() {
		return creationDt;
	}

	/**
	 * @param creationDt the creationDt to set
	 */
	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}

	/**
	 * @return the pstId
	 */
	public Long getPstId() {
		return pstId;
	}

	/**
	 * @param pstId the pstId to set
	 */
	public void setPstId(Long pstId) {
		this.pstId = pstId;
	}
		
}
