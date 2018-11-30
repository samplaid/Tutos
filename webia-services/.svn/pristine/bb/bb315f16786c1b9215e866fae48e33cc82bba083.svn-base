package lu.wealins.webia.services.ws.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.services.core.service.validations.TransactionValidationService;
import lu.wealins.webia.services.ws.rest.TransactionValidationRestService;

@Component
public class TransactionValidationRestServiceImpl implements TransactionValidationRestService {

	@Autowired
	private TransactionValidationService transactionValidationService;

	@Override
	public Collection<String> validateMandatorySpecificAmount(TransactionFormDTO transaction) {
		List<String> errors = new ArrayList<>();
		transactionValidationService.validateMandatorySpecificAmount(transaction, errors);
		return errors;
	}

}
