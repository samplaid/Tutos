package lu.wealins.rest.model.ods.common;

/**
 * Role linked to an actor. Model returned as component of a service response
 * 
 * @author bqv55
 *
 */
public class Role {

	/**
	 * Type of the role
	 */
	private RoleType roleType;

	// ***** BENEFICIARY *****//
	/**
	 * Is the beneficiary irrevocable => Beneficiaire acceptant
	 */
	private Boolean irrevocableBeneficiary;

	/*
	 * Active transfer => cession active
	 */
	private Boolean activeTransfer;

	/**
	 * Type of beneficiary
	 */
	private String beneficiaryType;
	// ***** END BENEFICIARY *****//

	// ***** HOLDER *****//
	/**
	 * Type of transfert for the holder
	 */
	private String holderTransfertType;

	/**
	 * Type of holder Usufructuary (usufruitier) : USUFR Bare owner (nu propriétaire) : NUPRO
	 */
	private String holderType;
	// ***** END HOLDER *****//

	// ***** TRANSFEREE *****//
	/**
	 * Type of transferee
	 */
	private String transfereeType;

	// ***** END TRANSFEREE *****//

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Boolean getIrrevocableBeneficiary() {
		return irrevocableBeneficiary;
	}

	public void setIrrevocableBeneficiary(Boolean irrevocableBeneficiary) {
		this.irrevocableBeneficiary = irrevocableBeneficiary;
	}

	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	public String getHolderTransfertType() {
		return holderTransfertType;
	}

	public void setHolderTransfertType(String holderTransfertType) {
		this.holderTransfertType = holderTransfertType;
	}

	public String getHolderType() {
		return holderType;
	}

	public void setHolderType(String holderType) {
		this.holderType = holderType;
	}

	/**
	 * @return the transfereeType
	 */
	public String getTransfereeType() {
		return transfereeType;
	}

	/**
	 * @param transfereeType the transfereeType to set
	 */
	public void setTransfereeType(String transfereeType) {
		this.transfereeType = transfereeType;
	}

	/**
	 * @return the activeTransfer
	 */
	public Boolean getActiveTransfer() {
		return activeTransfer;
	}

	/**
	 * @param activeTransfer the activeTransfert to set
	 */
	public void setActiveTransfer(Boolean activeTransfer) {
		this.activeTransfer = activeTransfer;
	}

}
