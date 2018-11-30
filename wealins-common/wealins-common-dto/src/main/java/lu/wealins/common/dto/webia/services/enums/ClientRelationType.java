package lu.wealins.common.dto.webia.services.enums;

import java.util.Arrays;
import java.util.List;

import lu.wealins.common.dto.webia.services.ActivableRoleBasedCountry;

@SuppressWarnings("boxing")
public enum ClientRelationType implements ActivableRoleBasedCountry {

	OWNER(1), JOINT_OWNER(2), ADDN_OWNER(3), LIFE_ASSURED(4), JNT_LIFE_ASSURED(5), ADDN_LIFE_ASSURED(6), BENEFICIARY_AT_DEATH(7), TRUSTEE(8), ASSIGNEE(9), APPOINTEE(10), NOMINEE(11), PAYEE(
			12), NEW_OWNER(13), COMMUNICATIONS(14), OWNER_ON_DEATH(15), BENEFICIARY_AT_MATURITY(16), IRREVOCABLE_BEN(17), CESSION_SURRENDER_RIGHTS(18), CESSION_SWITCH_RIGHTS(
					19), CESSION_CHANGING_STRATEGY_RIGHTS(20), PLEDGEE(21), JOINT_OWNER_ON_DEATH(22), POWER_OF_ATTORNEY(23), USUFRUCTUARY(24), BARE_OWNER(25), ADDITIONAL_OWNER_ON_DEATH(
							26), ECONOMICAL_BENEFICIARY(27), DIRECTOR_MANAGER_REPRESENTING_COMPANY(
									28), BENEFICIARY_BARE_OWNER(
											29), BENEFICIARY_USUFRUCTUARY(30), ACCEPTANT(31), SEPARATE_PROPERTY_RIGHTS(32), SEPARATE_PROPERTY_NO_RIGHTS(33), SUCCESSION_DEATH(34), SUCCESSION_LIFE(35);

	public static final List<Integer> CLIENTS_EXCLUDED_RELATIONS = Arrays.asList(ClientRelationType.OWNER.getValue(), ClientRelationType.JOINT_OWNER.getValue(),
			ClientRelationType.ADDN_OWNER.getValue(),
			/*
			 * ClientRelationType.USUFRUCTUARY.getValue(), ClientRelationType.BARE_OWNER.getValue(),
			 */ ClientRelationType.LIFE_ASSURED.getValue(), ClientRelationType.JNT_LIFE_ASSURED.getValue(), ClientRelationType.ADDN_LIFE_ASSURED.getValue()
			/*
			 * , ClientRelationType.ECONOMICAL_BENEFICIARY.getValue()
			 */ , ClientRelationType.BENEFICIARY_AT_MATURITY.getValue(),
			ClientRelationType.BENEFICIARY_AT_DEATH.getValue()/*
																 * , ClientRelationType.IRREVOCABLE_BEN.getValue(), ClientRelationType.SEPARATE_PROPERTY_RIGHTS.getValue(),
																 * ClientRelationType.SEPARATE_PROPERTY_NO_RIGHTS.getValue(), ClientRelationType.ACCEPTANT.getValue(),
																 * ClientRelationType.SUCCESSION_LIFE.getValue(), ClientRelationType.SUCCESSION_DEATH.getValue(),
																 * ClientRelationType.BENEFICIARY_USUFRUCTUARY.getValue(), ClientRelationType.BENEFICIARY_BARE_OWNER.getValue()
																 */);

	public static final List<Integer> CESSION_ROLES = Arrays.asList(ClientRelationType.CESSION_CHANGING_STRATEGY_RIGHTS.getValue(), ClientRelationType.CESSION_SURRENDER_RIGHTS.getValue(),
			ClientRelationType.CESSION_SWITCH_RIGHTS.getValue());

	private Integer value;

	private boolean enable;

	private ClientRelationType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.common.dto.webia.services.enums.ActivableRoleBasedCountry#setEnable(boolean)
	 */
	@Override
	public ActivableRoleBasedCountry setEnable(boolean enable) {
		this.enable = enable;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.common.dto.webia.services.enums.ActivableRoleBasedCountry#isEnable()
	 */
	@Override
	public boolean isEnable() {
		return this.enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.common.dto.webia.services.enums.ActivableRoleBasedCountry#getRoleNumber()
	 */
	@Override
	public Integer getRoleNumber() {
		return this.getValue();
	}

	public static ClientRelationType toEnum(Integer input) {
		ClientRelationType result = null;
		ClientRelationType[] array = values();

		if (input != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i].getValue().intValue() == input.intValue()) {
					result = array[i];
					break;
				}
			}
		}

		return result;
	}

}
