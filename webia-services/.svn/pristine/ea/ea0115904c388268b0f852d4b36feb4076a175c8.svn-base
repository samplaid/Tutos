package lu.wealins.webia.services.core.service.impl;

import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

/**
 * This component is only used because of the transactional aspect that it provides. the internal method can not be transactional, that's why it was externalized here.
 *
 */
@Component
public class TaxBatchSaver {


	private static final Logger LOGGER = LoggerFactory.getLogger(TaxBatchSaver.class);

	@Transactional
	public void saveDetails(List<TransactionTaxDetailsDTO> details, long transactionTaxId, Consumer<Long> taxPersister,
			Consumer<List<TransactionTaxDetailsDTO>> detailsPersister) {
		detailsPersister.accept(details);
		taxPersister.accept(transactionTaxId);
		LOGGER.info("generated {} transaction tax details for the transaction tax having id {}", details.size(), transactionTaxId);
	}
}
