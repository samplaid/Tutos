package lu.wealins.liability.services.core.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lu.wealins.common.dto.liability.services.enums.PolicyChangeStatus;

@Converter
public class PolicyChangesStatusConverter implements AttributeConverter<PolicyChangeStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(PolicyChangeStatus statusEnum) {
		if (statusEnum == null) {
			return null;
		}
		return statusEnum.getValue();
	}

	@Override
	public PolicyChangeStatus convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return null;
		}
		return PolicyChangeStatus.fromNumber(dbData);
	}

}
