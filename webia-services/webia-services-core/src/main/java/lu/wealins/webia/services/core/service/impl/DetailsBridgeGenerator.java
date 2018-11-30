package lu.wealins.webia.services.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransactionTaxDTO;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

@Component
public class DetailsBridgeGenerator {

	private List<TransactionTaxDetailsGenerator> detailsGenerators;

	@Autowired
	public void setDetailsGenerators(List<TransactionTaxDetailsGenerator> detailsGenerators) {
		this.detailsGenerators = detailsGenerators;
	}

	public List<TransactionTaxDetailsDTO> generateTransactionTaxDetails(TransactionTaxDTO transactionTax, List<TransactionTaxDetailsDTO> previousDetails, boolean frenchTaxable) {
		List<TransactionTaxDetailsGenerator> generators = detailsGenerators
				.stream()
				.filter(g -> g.supportsType(transactionTax.getTransactionType()))
				.collect(Collectors.toList());

		if (generators.size() != 1) {
			throw new IllegalStateException(String.format("Expected 1 generator for type %s, found : %s", transactionTax.getTransactionType(), generators.size()));
		}

		return generators.get(0).generateTransactionTaxDetails(transactionTax, previousDetails, frenchTaxable);
	}
}
