package lu.wealins.common.dto.webia.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommissionReconciliationDTO implements Comparable<CommissionReconciliationDTO> {

	private List<CommissionToPayDTO> sapCommissionsToPay;

	private List<CommissionToPayDTO> webiaCommissionToPay;

	/** Contains the agent id */
	private String agentId;

	/** Contains the agent crm id */
	private String crmId;

	/** Contains the name of the agent */
	private String name;

	/** Contains the type of the commission (ENTRY, ADMIN) */
	private String comType;

	/** Contains the commission period */
	private String period;

	/** Contains commission currency */
	private String currency;

	/** Contains commission extracted from sap system */
	private BigDecimal sapBalance;

	/** Contains commission extracted from webia system */
	private BigDecimal statementBalance;

	/** Contains the reconciliation value */
	private BigDecimal gap;

	/** Contains the reconciliation status */
	private String status;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getSapBalance() {
		return sapBalance;
	}

	public void setSapBalance(BigDecimal sapBalance) {
		this.sapBalance = sapBalance;
	}

	public BigDecimal getStatementBalance() {
		return statementBalance;
	}

	public void setStatementBalance(BigDecimal statementBalance) {
		this.statementBalance = statementBalance;
	}

	public BigDecimal getGap() {
		return gap;
	}

	public void setGap(BigDecimal gap) {
		this.gap = gap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CommissionToPayDTO> getSapCommissionsToPay() {
		if (sapCommissionsToPay == null) {
			sapCommissionsToPay = new ArrayList<>();
		}
		return sapCommissionsToPay;
	}

	public void setSapCommissionsToPay(List<CommissionToPayDTO> sapCommissionsToPay) {
		this.sapCommissionsToPay = sapCommissionsToPay;
	}

	public List<CommissionToPayDTO> getWebiaCommissionToPay() {
		if (webiaCommissionToPay == null) {
			webiaCommissionToPay = new ArrayList<>();
		}
		return webiaCommissionToPay;
	}

	public void setWebiaCommissionToPay(List<CommissionToPayDTO> webiaCommissionToPay) {
		this.webiaCommissionToPay = webiaCommissionToPay;
	}

	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	public String getCrmId() {
		return crmId;
	}

	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}

	/**
	 * Compare this commission with the commission passed in parameter by agent id, period and currency.
	 */
	@Override
	public int compareTo(CommissionReconciliationDTO other) {
		if (other == null) {
			return 1;
		}

		if (StringUtils.isBlank(this.getAgentId())) {
			if (StringUtils.isNotBlank(other.getAgentId())) {
				return -1;
			}
		} else if (StringUtils.isBlank(other.getAgentId())) {
			return 1;
		}

		int comp = this.getAgentId().compareTo(other.getAgentId());

		if (comp != 0) {
			return comp;
		}

		if (StringUtils.isBlank(this.getPeriod())) {
			if (StringUtils.isNotBlank(other.getPeriod())) {
				return -1;
			}
		} else if (StringUtils.isNotBlank(other.getPeriod())) {
			return 1;
		}

		comp = this.getPeriod().compareTo(other.getPeriod());

		if (comp != 0) {
			return comp;
		}

		if (StringUtils.isBlank(this.getCurrency())) {
			if (StringUtils.isNotBlank(other.getCurrency())) {
				return 1;
			}
		} else if (StringUtils.isNotBlank(other.getCurrency())) {
			return -1;
		}

		return this.getCurrency().compareTo(other.getCurrency());
	}

}
