/**
 * 
 */
package lu.wealins.common.dto.liability.services.enums;

import org.springframework.util.Assert;

/**
 * Enumerate the status of the client policy relationship:<br>
 * {@code ACTIVE = 1} and {@code INACTIVE = 2}
 * 
 * @author oro
 *
 */
public enum CliPolRelationshipStatus {

	/**
	 * The value of this type is 1.
	 */
	ACTIVE((short) 1),

	/**
	 * The value of this type is 2.
	 */
	INACTIVE((short) 2);

	private final short value;

	/**
	 * Construct the enumeration
	 */
	private CliPolRelationshipStatus(short value) {
		this.value = value;
	}

	/**
	 * Retrieves the relationship status value.
	 * 
	 * @return the status
	 */
	public short getValue() {
		return value;
	}

	/**
	 * Convert the giving value to a type of this enum.
	 * 
	 * @param value the value to convert
	 * @return The corresponding enum
	 */
	public static CliPolRelationshipStatus toEnum(Short value) {
		Assert.notNull(value, "Cannot convert a null value to the enum type of CliPolRelationshipStatus.");

		CliPolRelationshipStatus[] values = values();

		for (int pos = 0; pos < values.length; pos++) {
			if (value.shortValue() == values[pos].getValue()) {
				return values[pos];
			}
		}

		throw new IllegalArgumentException("Cannot convert a value " + value.shortValue() + " to the enum type of CliPolRelationshipStatus.");
	}

}
