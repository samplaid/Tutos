package lu.wealins.rest.model.ods;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lu.wealins.rest.model.ods.common.Holding;

/**
 * The holding load response class
 * 
 * @author bqv55
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoldingsLoadResponse {

	/**
	 * List of the policy's holdings
	 */
	public Collection<Holding> holdings;
	public Date statementDate;

	public Collection<Holding> getHoldings() {
		return holdings;
	}

	public void setHoldings(Collection<Holding> holdings) {
		this.holdings = holdings;
	}

	/**
	 * @return the statementDate
	 */
	public Date getStatementDate() {
		return statementDate;
	}

	/**
	 * @param statementDate the statementDate to set
	 */
	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}
}
