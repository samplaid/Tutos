package lu.wealins.webia.services.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.webia.services.core.mapper.TransactionTaxDetailsMapper;
import lu.wealins.webia.services.core.persistence.entity.TransactionTaxDetailsEntity;
import lu.wealins.webia.services.core.persistence.repository.TransactionTaxDetailsRepository;
import lu.wealins.webia.services.core.service.TransactionTaxDetailsService;
import lu.wealins.common.dto.webia.services.TransactionTaxDetailsDTO;

@Service
public class TransactionTaxDetailsServiceImpl implements TransactionTaxDetailsService {
	

	@Autowired
	private TransactionTaxDetailsRepository transactionTaxRepository;

	@Autowired
	private TransactionTaxDetailsMapper transactionTaxDetailsMapper;

	@Override
	public void create(List<TransactionTaxDetailsDTO> transactionTaxDetails) {
		List<TransactionTaxDetailsEntity> entities = transactionTaxDetailsMapper.asTransactionTaxDetailsEntities(transactionTaxDetails);
		transactionTaxRepository.save(entities);
	}

	@Override
	public List<TransactionTaxDetailsDTO> getByTransactionTaxId(long id) {
		List<TransactionTaxDetailsEntity> entities = transactionTaxRepository.findAllByTransactionTaxId(id);
		return transactionTaxDetailsMapper.asTransactionTaxDetailsDTOs(entities);
	}
}
