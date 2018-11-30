package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum FundStrategyTypeDTO {

	FID("Dedicated internal fund"), FIC("Collectives internal fond"), FE("External fund"), UNKNOWN("Unknown");

	private String val;

	private FundStrategyTypeDTO(String val) {
		this.val = val;
	}

	public String val() {
		return this.val;
	}

	public static FundStrategyTypeDTO fromString(String val) {
		if (val != null) {
			for (FundStrategyTypeDTO b : values()) {
				if (val.equalsIgnoreCase(b.val)) {
					return b;
				}
			}
		}
		return null;
	}
}