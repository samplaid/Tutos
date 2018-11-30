package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lu.wealins.common.dto.webia.services.enums.StatusCode;

/**
 * Entity for the ISIN
 *
 */
@Entity
@Table(name = "SAS_ISIN")
public class SasIsinEntity implements Serializable {

	/** Serial Id. */
	private static final long serialVersionUID = -7947550582650453966L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SAS_ISIN_ID")
	private Integer sasIsinId;

	@Column(name = "ISIN", nullable = false)
	private String isin;

	@Column(name = "CURRENCY", nullable = false)
	private String currency;

	@Column(name = "STATUS_CD")
	@Enumerated(EnumType.STRING)
	private StatusCode statusCode;

	@Column(name = "BIC")
	private String bankBic;

	@Column(name = "FUND_TITLE")
	private String fundTitle;

	@Column(name = "CREATED_DT", nullable = false)
	private Date creationDate;

	public Integer getSasIsinId() {
		return sasIsinId;
	}

	public void setSasIsinId(Integer sasIsinId) {
		this.sasIsinId = sasIsinId;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public String getBankBic() {
		return bankBic;
	}

	public void setBankBic(String bankBic) {
		this.bankBic = bankBic;
	}

	public String getFundTitle() {
		return fundTitle;
	}

	public void setFundTitle(String fundTitle) {
		this.fundTitle = fundTitle;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
