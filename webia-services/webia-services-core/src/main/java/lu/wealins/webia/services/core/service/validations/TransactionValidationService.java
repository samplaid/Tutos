package lu.wealins.webia.services.core.service.validations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;

public interface TransactionValidationService {

	void validateMandatoryFields(TransactionFormDTO transaction, List<String> errors);

	void validateTransactionFees(BigDecimal transactionFees, BigDecimal brokerTransactionFees, String operation, List<String> errors);

	void validateEffectiveDate(Date effectiveDate, List<String> errors);

	void validatePayment(TransactionFormDTO transaction, List<String> errors);

	void validateMandatorySpecificAmount(TransactionFormDTO transaction, List<String> errors);
}
