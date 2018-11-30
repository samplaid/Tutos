package lu.wealins.common.dto.webia.services;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommissionToPayAggregatedDTO {

	private String agentId;

	/** Contains the agent name */
	private String name;
	private String currency;
	private String period;
	private String type;

	public CommissionToPayAggregatedDTO(CommissionToPayDTO commissionToPayDTO) {
		Assert.notNull(commissionToPayDTO, "Could not construct an instance of this class. The object passed in parameter is null.");

		this.agentId = StringUtils.stripToNull(commissionToPayDTO.getAgentId());
		this.currency = commissionToPayDTO.getComCurrency();

		// Do not use account_month field as it contains a wrong value from DB.
		if (commissionToPayDTO.getComDate() != null) {
			this.period = new SimpleDateFormat("yyyyMM").format(commissionToPayDTO.getComDate());
		}

		this.type = commissionToPayDTO.getComType();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CommissionToPayAggregatedDTO)) {
			return false;
		}

		CommissionToPayAggregatedDTO other = (CommissionToPayAggregatedDTO) obj;

		if (agentId == null) {
			if (other.agentId != null) {
				return false;
			}

		} else if (!agentId.equals(other.agentId)) {
			return false;
		}

		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}

		if (period == null) {
			if (other.period != null) {
				return false;
			}

		} else if (!period.equals(other.period)) {
			return false;
		}

		if (type == null) {
			if (other.type != null) {
				return false;
			}

		} else if (!type.equals(other.type)) {
			return false;
		}

		return true;
	}


}
