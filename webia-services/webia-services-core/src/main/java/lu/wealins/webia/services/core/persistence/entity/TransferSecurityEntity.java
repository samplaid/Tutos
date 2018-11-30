package lu.wealins.webia.services.core.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSFER_SECURITIES")
public class TransferSecurityEntity extends AuditingEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TRANSFER_SECURITIES_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transferSecuritesId;

	@Column(name = "FUND_NAME")
	private String fundName;

	@Column(name = "ISIN")
	private String isin;

	@Column(name = "UNITS")
	private BigDecimal units;

	public Long getTransferSecuritesId() {
		return transferSecuritesId;
	}

	public void setTransferSecuritesId(Long transferSecuritesId) {
		this.transferSecuritesId = transferSecuritesId;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}
}
