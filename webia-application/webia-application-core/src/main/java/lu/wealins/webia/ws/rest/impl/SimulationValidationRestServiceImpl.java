package lu.wealins.webia.ws.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.editing.services.enums.DocumentType;
import lu.wealins.common.dto.webia.services.TransactionFormDTO;
import lu.wealins.webia.core.service.WebiaTransactionFormService;
import lu.wealins.webia.core.utils.RestClientUtils;
import lu.wealins.webia.ws.rest.SimulationValidationRESTService;

@Component
public class SimulationValidationRestServiceImpl implements SimulationValidationRESTService {

	private static final Logger log = LoggerFactory.getLogger(SimulationValidationRestServiceImpl.class);

	@Autowired
	private RestClientUtils restClientUtils;

	@Autowired
	private WebiaTransactionFormService transactionFormService;

	@Override
	public Collection<String> validate(SecurityContext context, Long workflowItemId, DocumentType documentType) {

		switch (documentType) {
		case WITHDRAWAL_FOLLOWUP:
			return validateEnoughCashSimulation(workflowItemId);
		default:
			log.warn("Simulation validation was called on document type {} but validations on this document are not implemented yet.", documentType.name());
			return new ArrayList<>();
		}
	}

	private List<String> validateEnoughCashSimulation(Long workflowItemId) {

		TransactionFormDTO transactionForm = transactionFormService.getFormData(new Integer(workflowItemId.intValue()));
		Collection<String> errors = restClientUtils.post("webia/transactionValidation/validateMandatorySpecificAmount", transactionForm, new GenericType<List<String>>() {
		});

		return errors.stream().distinct().collect(Collectors.toList());
	}

}
