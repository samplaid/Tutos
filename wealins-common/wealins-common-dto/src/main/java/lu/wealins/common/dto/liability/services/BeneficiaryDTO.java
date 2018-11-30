package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryDTO extends ClientDTO implements CommonBeneficiary {

	private BigDecimal percentageSplit; // split by rank
	private Boolean irrevocable;
	private Boolean separatePropertyRights;
	private Boolean separatePropertyNoRights;
	private Boolean acceptant;
	private Boolean usufructuary;
	private Boolean bareOwner;
	private Boolean exEqualParts;

	/**
	 * @return the exEqualParts
	 */
	public Boolean getExEqualParts() {
		return exEqualParts;
	}

	/**
	 * @param exEqualParts the exEqualParts to set
	 */
	public void setExEqualParts(Boolean exEqualParts) {
		this.exEqualParts = exEqualParts;
	}

	@Override
	public BigDecimal getPercentageSplit() {
		return percentageSplit;
	}

	public void setPercentageSplit(BigDecimal percentageSplit) {
		this.percentageSplit = percentageSplit;
	}

	public Boolean getIrrevocable() {
		return irrevocable;
	}

	public void setIrrevocable(Boolean irrevocable) {
		this.irrevocable = irrevocable;
	}

	public Boolean getSeparatePropertyRights() {
		return separatePropertyRights;
	}

	public void setSeparatePropertyRights(Boolean separatePropertyRights) {
		this.separatePropertyRights = separatePropertyRights;
	}

	public Boolean getSeparatePropertyNoRights() {
		return separatePropertyNoRights;
	}

	public void setSeparatePropertyNoRights(Boolean separatePropertyNoRights) {
		this.separatePropertyNoRights = separatePropertyNoRights;
	}

	public Boolean getAcceptant() {
		return acceptant;
	}

	public void setAcceptant(Boolean acceptant) {
		this.acceptant = acceptant;
	}

	public Boolean getUsufructuary() {
		return usufructuary;
	}

	public void setUsufructuary(Boolean usufructuary) {
		this.usufructuary = usufructuary;
	}

	public Boolean getBareOwner() {
		return bareOwner;
	}

	public void setBareOwner(Boolean bareOwner) {
		this.bareOwner = bareOwner;
	}

}
