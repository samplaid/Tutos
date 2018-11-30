package lu.wealins.liability.services.core.business.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.exceptions.PolicyIdFormatException;

@Component
public class PolicyIdHelper {

	private static final String POLICY_ID_SPLITTER = "/";

	public String getPolicyRootId(String policyId) {
		ImmutablePair<String, Integer> policyIdPair = splitPolicyId(policyId);
		return policyIdPair.getLeft();
	}

	private Integer getIncrement(String policyId) {
		ImmutablePair<String, Integer> policyIdPair = splitPolicyId(policyId);
		return policyIdPair.getRight();
	}

	private ImmutablePair<String, Integer> splitPolicyId(String policyId) {
		String[] splittedPolicyName = policyId.trim().split(POLICY_ID_SPLITTER);
		if (splittedPolicyName.length == 1) {
			return new ImmutablePair<String, Integer>(splittedPolicyName[0], null);
		} else if (splittedPolicyName.length == 2 && NumberUtils.isNumber(splittedPolicyName[1])) {
			return new ImmutablePair<String, Integer>(splittedPolicyName[0], Integer.parseInt(splittedPolicyName[1]));
		} else {
			throw new PolicyIdFormatException(String.format("Could not parse the policy id [%s]", policyId));
		}		
	}

	public String createNextId(List<String> allVersionsOfIds, String rootName) {
		int currentMaxIncrement = allVersionsOfIds.stream()
				.map(this::getIncrement)
				.mapToInt(i -> i != null ? i : -1)
				.max()
				.getAsInt();

		return new StringBuilder()
				.append(rootName)
				.append(POLICY_ID_SPLITTER)
				.append(currentMaxIncrement + 1)
				.toString();
	}
}
