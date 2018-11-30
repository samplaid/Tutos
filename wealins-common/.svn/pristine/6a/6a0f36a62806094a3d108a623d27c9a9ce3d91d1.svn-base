package lu.wealins.common.dto.liability.services.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lu.wealins.common.dto.liability.services.OptionDetailDTO;

public enum PolicyActiveStatus {

	ACTIVE("ACTIVE"), PENDING("PENDING"), INACTIVE("INACTIVE");

	public static Set<String> ACTIVE_STATUS = Stream.of("Inforce", "Paid Up", "Lapsed").collect(Collectors.toSet());
	public static Set<String> PENDING_STATUS = Stream.of("Pending", "Pending, Payment Requested", "New Business Entry").collect(Collectors.toSet());

	private String status;

	private PolicyActiveStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static PolicyActiveStatus getStatus(OptionDetailDTO policyStatus) {
		String statusName = policyStatus.getDescription();

		if (ACTIVE_STATUS.contains(statusName)) {
			return PolicyActiveStatus.ACTIVE;
		}

		if (PENDING_STATUS.contains(statusName)) {
			return PolicyActiveStatus.PENDING;
		}

		return PolicyActiveStatus.INACTIVE;

	}

	public static PolicyActiveStatus getStatus(int status) {
		if (status == PolicyStatus.IN_FORCE.getStatus()) {
			return PolicyActiveStatus.ACTIVE;
		}

		if (status == PolicyStatus.PENDING.getStatus() || status == PolicyStatus.NEW_BUSINESS_ENTRY.getStatus()) {
			return PolicyActiveStatus.PENDING;
		}

		return PolicyActiveStatus.INACTIVE;

	}
}
