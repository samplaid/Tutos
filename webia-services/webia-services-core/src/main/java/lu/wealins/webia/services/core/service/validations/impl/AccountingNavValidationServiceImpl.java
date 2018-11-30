package lu.wealins.webia.services.core.service.validations.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.webia.services.core.service.validations.AccountingNavValidationService;

@Service
public class AccountingNavValidationServiceImpl implements AccountingNavValidationService {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

	@Override
	public Optional<String> validate(CloturedVniInjectionControlRequest accountingNav) throws ParseException {
		Optional<String> vniAmountCheckResultMessage = checkVniValue(accountingNav.getPrice());
		Date navDate = formatter.parse(accountingNav.getPriceDate());
		Optional<String> vniDateCheckMessage = checkDate(navDate);

		if (vniAmountCheckResultMessage.isPresent() || vniDateCheckMessage.isPresent()) {
			return Optional.ofNullable(vniAmountCheckResultMessage.orElse("") + " , " + vniDateCheckMessage.orElse(""));
		}

		return Optional.empty();
	}

	@Override
	public Optional<String> checkDate(Date navDate) {

		LocalDate lastDayOfPreviousMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		LocalDate navLocalDate = navDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		boolean isNavDateEqualLastDayOfpreviousMonth = lastDayOfPreviousMonth.equals(navLocalDate);

		if (!isNavDateEqualLastDayOfpreviousMonth) {
			return Optional.of(
					" LA DATE DE VALUATION DOIT ETRE EGALE A CELLE DU DERNIER JOUR DU MOIS PRECEDENT LE MOIS EN COUR ");
		}

		return Optional.empty();
	}

	@Override
	public Optional<String> checkVniValue(BigDecimal vniValue) {

		if ((BigDecimal.ZERO.compareTo(vniValue) >= 0)) {
			return Optional.of("LA VALEUR DE LA VNI DOIT ETRE SUPERIEURE A ZERO ");
		}

		return Optional.empty();
	}

}
