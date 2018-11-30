package lu.wealins.liability.services.core.persistence.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

import lu.wealins.liability.services.core.persistence.entity.PolicyEntity;

@Converter
public class PolicyIdConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute == null ? null : StringUtils.rightPad(attribute, PolicyEntity.POLID_LENGTH);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return StringUtils.trim(dbData);
	}
}
