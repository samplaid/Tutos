package lu.wealins.common.dto;

import java.util.Date;

public class FollowUpPolicyDTO implements Comparable<FollowUpPolicyDTO> {
	private String policy;
	private String appForm;
	private Date effectDate;

	/**
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * 
	 * @param policy
	 *            the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * @return the appForm
	 */
	public String getAppForm() {
		return appForm;
	}

	/**
	 * @param appForm
	 *            the appForm to set
	 */
	public void setAppForm(String appForm) {
		this.appForm = appForm;
	}

	/**
	 * @return the effectDate
	 */
	public Date getEffectDate() {
		return effectDate;
	}

	/**
	 * @param effectDate
	 *            the effectDate to set
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	@Override
	public int compareTo(FollowUpPolicyDTO secondFollowup) {
		return compare(this, secondFollowup);
	}
	
	public int compare(FollowUpPolicyDTO t1, FollowUpPolicyDTO t2) {

		if (t1 == null) {
			if (t2 != null) {
				return 1;
			}
		}

		if (t2 == null) {
			if (t1 != null) {
				return -1;
			}
		}

		if (t2 != null && t1 != null) {
			if (t1.getEffectDate() == null) {
				if (t2.getEffectDate() != null) {
					return -1;
				}
			}

			if (t2.getEffectDate() == null) {
				if (t1.getEffectDate() != null) {
					return 1;
				}
			}

			if (t2.getEffectDate() != null && t1.getEffectDate() != null) {
				int result = t2.getEffectDate().compareTo(t1.getEffectDate());
				if(result == 0) {
					return t1.getPolicy().compareTo(t2.getPolicy());
				}
				return result;
			}
		}

		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((policy == null) ? 0 : policy.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FollowUpPolicyDTO)) {
			return false;
		}
		FollowUpPolicyDTO other = (FollowUpPolicyDTO) obj;
		if (policy == null) {
			if (other.policy != null) {
				return false;
			}
		} else if (!policy.equals(other.policy)) {
			return false;
		}
		return true;
	}

	
	
	

}
