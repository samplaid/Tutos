package lu.wealins.common.dto.liability.services.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.util.Assert;

public enum CliPolRelationshipType {
	OWNER(1), JOINT_OWNER(2), ADDN_OWNER(3), LIFE_ASSURED(4), JNT_LIFE_ASSURED(5), ADDN_LIFE_ASSURED(6), BENEFICIARY_AT_DEATH(7), TRUSTEE(8), ASSIGNEE(9), APPOINTEE(10), NOMINEE(11), PAYEE(
			12), NEW_OWNER(13), COMMUNICATIONS(14), OWNER_ON_DEATH(15), BENEFICIARY_AT_MATURITY(16), IRREVOCABLE_BEN(17), CESSION_SURRENDER_RIGHTS(18), CESSION_SWITCH_RIGHTS(
					19), CESSION_CHANGING_STRATEGY_RIGHTS(20), PLEDGEE(21), JOINT_OWNER_ON_DEATH(22), POWER_OF_ATTORNEY(23), USUFRUCTUARY(24), BARE_OWNER(25), ADDITIONAL_OWNER_ON_DEATH(
							26), ECONOMICAL_BENEFICIARY(27), DIRECTOR_MANAGER_REPRESENTING_COMPANY(
									28), BENEFICIARY_BARE_OWNER(
											29), BENEFICIARY_USUFRUCTUARY(30), ACCEPTANT(
													31), SEPARATE_PROPERTY_RIGHTS(32), SEPARATE_PROPERTY_NO_RIGHTS(33), SUCCESSION_DEATH(34), SUCCESSION_LIFE(35), CESSION_ALL_RIGHTS(36);

	private static final String CLIENT_POLICY_RELATIONSHIP_TYPES_CANNOT_BE_NULL = "Client policy relationship cannot be null.";
	private int value;

	public static final List<CliPolRelationshipType> BENEFICIARY_RELATIONSHIP_TYPE_GROUP = Arrays.asList(CliPolRelationshipType.BENEFICIARY_AT_DEATH, CliPolRelationshipType.BENEFICIARY_AT_MATURITY,
			CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY, CliPolRelationshipType.IRREVOCABLE_BEN, CliPolRelationshipType.ACCEPTANT, CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS,
			CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS, CliPolRelationshipType.BENEFICIARY_BARE_OWNER);

	public static final List<CliPolRelationshipType> POLICY_HOLDER_RELATIONSHIP_TYPE_GROUP = Arrays.asList(CliPolRelationshipType.OWNER, CliPolRelationshipType.JOINT_OWNER,
			CliPolRelationshipType.ADDN_OWNER, CliPolRelationshipType.USUFRUCTUARY, CliPolRelationshipType.BARE_OWNER, CliPolRelationshipType.SUCCESSION_DEATH, CliPolRelationshipType.SUCCESSION_LIFE);

	public static final List<CliPolRelationshipType> INSURED_RELATIONSHIP_TYPE_GROUP = Arrays.asList(CliPolRelationshipType.LIFE_ASSURED, CliPolRelationshipType.JNT_LIFE_ASSURED,
			CliPolRelationshipType.ADDN_LIFE_ASSURED, CliPolRelationshipType.ECONOMICAL_BENEFICIARY);

	public static final List<CliPolRelationshipType> OTHER_CLIENT_RELATIONSHIP_TYPE_GROUP;

	public static final List<Integer> DEFAULT_POLICY_HOLDER_ROLES_TYPES = Arrays.asList(CliPolRelationshipType.OWNER.getValue(), CliPolRelationshipType.JOINT_OWNER.getValue(),
			CliPolRelationshipType.ADDN_OWNER.getValue());

	public static final List<CliPolRelationshipType> POLICY_HOLDER_PRINCIPAL_RELATIONSHIP_TYPE_GROUP = Arrays.asList(CliPolRelationshipType.OWNER, CliPolRelationshipType.JOINT_OWNER,
			CliPolRelationshipType.ADDN_OWNER);

	public static final List<CliPolRelationshipType> BENEFICIARY_PRINCIPAL_RELATIONSHIP_TYPE_GROUP = Arrays.asList(CliPolRelationshipType.BENEFICIARY_AT_DEATH,
			CliPolRelationshipType.BENEFICIARY_AT_MATURITY);

	public static final List<CliPolRelationshipType> INSURED_PRINCIPAL_RELATIONSHIP_TYPE_GROUP = Arrays.asList(CliPolRelationshipType.LIFE_ASSURED,
			CliPolRelationshipType.JNT_LIFE_ASSURED, CliPolRelationshipType.ADDN_LIFE_ASSURED);

	static {
		List<CliPolRelationshipType> temp = ListUtils.subtract(Arrays.asList(CliPolRelationshipType.values()), BENEFICIARY_RELATIONSHIP_TYPE_GROUP);
		temp = ListUtils.subtract(temp, POLICY_HOLDER_RELATIONSHIP_TYPE_GROUP);
		temp = ListUtils.subtract(temp, INSURED_RELATIONSHIP_TYPE_GROUP);
		OTHER_CLIENT_RELATIONSHIP_TYPE_GROUP = temp;
	}

	private CliPolRelationshipType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean isInGroup(List<CliPolRelationshipType> group) {
		Assert.notNull(group);

		return group.contains(this);
	}

	public static CliPolRelationshipType toCliPolRelationshipType(int value) {
		for (CliPolRelationshipType cliPolRelationshipType : values()) {
			if (cliPolRelationshipType.getValue() == value) {
				return cliPolRelationshipType;
			}
		}
		throw new IllegalArgumentException("Unknown CliPolRelationshipType " + value + ".");
	}

	public static List<CliPolRelationshipType> toCliPolRelationshipTypes(List<Integer> cliPolRelationshipTypes) {
		Assert.notNull(cliPolRelationshipTypes, CLIENT_POLICY_RELATIONSHIP_TYPES_CANNOT_BE_NULL);

		return cliPolRelationshipTypes.stream().map(x -> toCliPolRelationshipType(x.intValue())).collect(Collectors.toList());
	}
}

