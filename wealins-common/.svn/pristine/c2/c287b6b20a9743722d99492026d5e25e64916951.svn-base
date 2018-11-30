/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author NGA
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurrenderReportResultDTO {
	private BigDecimal totalPremium;
	private BigDecimal totalWithdrawal;
	private List<SurrenderReportResultDetailsDTO> surrenderReportDetails = new ArrayList<>();

	/**
	 * @return the totalPremium
	 */
	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	/**
	 * @param totalPremium
	 *            the totalPremium to set
	 */
	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}


	public BigDecimal getTotalWithdrawal() {
		return totalWithdrawal;
	}

	public void setTotalWithdrawal(BigDecimal totalWithdrawal) {
		this.totalWithdrawal = totalWithdrawal;
	}

	/**
	 * @return the surrenderReportDetails
	 */
	public List<SurrenderReportResultDetailsDTO> getSurrenderReportDetails() {
		return surrenderReportDetails;
	}

	/**
	 * @param surrenderReportDetails
	 *            the surrenderReportDetails to set
	 */
	public void setSurrenderReportDetails(List<SurrenderReportResultDetailsDTO> surrenderReportDetails) {
		this.surrenderReportDetails = surrenderReportDetails;
	}

}
