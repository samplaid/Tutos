package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;
import lu.wealins.webia.services.core.service.TaxBatchService;
import lu.wealins.webia.services.core.service.TransactionTaxDetailsService;
import lu.wealins.webia.services.core.service.TransactionTaxService;

@Service
@Transactional
public class TaxBatchServiceImpl implements TaxBatchService {

	@Autowired
	private TransactionTaxService transactionTaxService;

	@Autowired
	private TransactionTaxDetailsService detailsService;

	@Autowired
	private TaxBatchSaver taxBatchSaver;

	@Autowired
	private DetailsBridgeGenerator detailsBridgeGenerator;

	private static final Logger LOGGER = LoggerFactory.getLogger(TaxBatchServiceImpl.class);

	private static final String PREVIOUS_DETAILS_CANNOT_BE_EMPTY = "The previous details can't be empty";

	@Override
	@Transactional
	public List<Long> createTransactionTaxDetails() {
		List<TransactionTaxDTO> newTransactionTaxes = transactionTaxService.getNewTransactions();

		if (newTransactionTaxes == null || newTransactionTaxes.isEmpty()) {
			LOGGER.info("No transaction tax ready to be processed");
			return new ArrayList<Long>();
		}

		List<Long> ids = newTransactionTaxes.stream().map(transaction -> transaction.getTransactionTaxId())
				.collect(Collectors.toList());

		return transactionTaxService.updateTransactions(ids);
	}

	private void generateDetails(TransactionTaxDTO transactionTax, boolean frenchTaxable) {
		try {
			List<TransactionTaxDetailsDTO> previousDetails = null;
			if (transactionTax.getPreviousTransactionId() != null) {
				previousDetails = detailsService.getByTransactionTaxId(transactionTax.getPreviousTransactionId());
				Assert.notEmpty(previousDetails, PREVIOUS_DETAILS_CANNOT_BE_EMPTY);
			}
			List<TransactionTaxDetailsDTO> details = detailsBridgeGenerator.generateTransactionTaxDetails(transactionTax, previousDetails, frenchTaxable);
			taxBatchSaver.saveDetails(details, transactionTax.getTransactionTaxId(), transactionTaxService::markAsUpdated, detailsService::create);
		} catch (Exception e) {
			LOGGER.error("An error occured while creating the transaction tax details for the transaction tax id : " + transactionTax.getTransactionTaxId(), e);
		}
	}


	
	@Override
	public void createTransactionTaxDetails(List<TransactionTaxDTO> transactionTaxs, boolean frenchTaxable) {
		if (transactionTaxs == null || transactionTaxs.isEmpty()) {
			LOGGER.error("Connot create details there are not TransactionTax in List");
			return ;
		}

		LOGGER.info("found {} transaction tax of tranaction ready to be processed", transactionTaxs.size());

		for (TransactionTaxDTO transactionTax : transactionTaxs) {
			generateDetails(transactionTax, frenchTaxable);
		}
	}

}
