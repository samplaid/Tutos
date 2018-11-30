package lu.wealins.webia.services.core.service.validations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;

@Service
public interface AccountingNavValidationService {

	Optional<String> validate(CloturedVniInjectionControlRequest accountingNav) throws ParseException;

	Optional<String> checkDate(Date navDate);

	Optional<String> checkVniValue(BigDecimal vniValue);
}
