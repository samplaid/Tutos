/**
 * 
 */
package lu.wealins.common.dto.webia.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the group of the commission reconciliations. Its instance differs from other by using the hashcode and equal methods of the {@link CommissionReconciliationGroupAggregateDTO}
 * class.
 * 
 * @author oro
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommissionReconciliationGroupDTO implements Comparable<CommissionReconciliationGroupDTO> {

	private CommissionReconciliationGroupAggregateDTO aggregate;

	private List<CommissionReconciliationDTO> commissions = new ArrayList<>();


	/**
	 * Default constructor
	 */
	@JsonCreator
	public CommissionReconciliationGroupDTO(@JsonProperty("aggregate") CommissionReconciliationGroupAggregateDTO aggregate) {
		Assert.notNull(aggregate, "Could not create an iinstance of this class. The aggregated object is null.");
		this.aggregate = aggregate;
	}

	/**
	 * @return the aggregate
	 */
	public CommissionReconciliationGroupAggregateDTO getAggregate() {
		return aggregate;
	}

	/**
	 * @return the commissions
	 */
	public List<CommissionReconciliationDTO> getCommissions() {
		if (commissions == null) {
			commissions = new ArrayList<>();
		}
		return commissions;
	}

	/**
	 * @param commissions the commissions to set
	 */
	public void setCommissions(List<CommissionReconciliationDTO> commissions) {
		this.commissions = commissions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggregate == null) ? 0 : aggregate.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommissionReconciliationGroupDTO other = (CommissionReconciliationGroupDTO) obj;
		if (aggregate == null) {
			if (other.aggregate != null)
				return false;
		} else if (!aggregate.equals(other.aggregate))
			return false;
		return true;
	}

	/**
	 * Compare this object to other using the agent code.
	 */
	@Override
	public int compareTo(CommissionReconciliationGroupDTO other) {
		if (other == null) {
			return 1;
		}

		int comp = Integer.compare(getStatusOrdinal(this.aggregate.getStatus()), getStatusOrdinal(other.aggregate.getStatus()));

		if (comp != 0) {
			return comp;
		}

		return StringUtils.compare(this.aggregate.getName(), other.aggregate.getName());
	}

	private int getStatusOrdinal(String status) {

		if ("Reconciled".equals(status)) {
			return 0;
		}

		if ("NOT RECONCILED".equals(status)) {
			return 1;
		}

		if ("Validated".equals(status)) {
			return 2;
		}

		if ("Done".equals(status)) {
			return 3;
		}

		return Integer.MAX_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommissionReconciliationGroupDTO [aggregate=");
		builder.append(aggregate.toString());
		builder.append("]");
		builder.append("\n");
		return builder.toString();
	}

}
