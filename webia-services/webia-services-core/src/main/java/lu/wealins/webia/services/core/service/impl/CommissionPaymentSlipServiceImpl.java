/**
 * 
 */
package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.webia.services.core.mapper.TransferMapper;
import lu.wealins.webia.services.core.persistence.entity.TransferEntity;
import lu.wealins.webia.services.core.persistence.repository.TransferRepository;
import lu.wealins.webia.services.core.service.CommissionPaymentSlipService;

/**
 * Default implementation of the {@link CommissionPaymentSlipService} interface
 * 
 * @author ORO
 *
 */
@Service
public class CommissionPaymentSlipServiceImpl implements CommissionPaymentSlipService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommissionPaymentSlipServiceImpl.class);

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private TransferMapper transferMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TransferDTO> retrievePaymentSlip(Long statementId) {
		LOGGER.info("Retrieve the commission payment slip from transfers table");
		Collection<TransferEntity> paymentSlipEntities = transferRepository.findPaymentSlipsByStatementId(statementId);
		Collection<TransferDTO> paymentSlips = transferMapper.asTransferDTOs(paymentSlipEntities);
		return new ArrayList<>(paymentSlips);
	}

}
