/**
 * 
 */
package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.CommissionToPayDTO;
import lu.wealins.webia.services.core.mapper.CommissionToPayMapper;
import lu.wealins.webia.services.core.mapper.SapOpenBalanceMapper;
import lu.wealins.webia.services.core.persistence.entity.CommissionToPayEntity;
import lu.wealins.webia.services.core.persistence.entity.SapOpenBalanceEntity;
import lu.wealins.webia.services.core.persistence.repository.CommissionToPayRepository;
import lu.wealins.webia.services.core.persistence.repository.SapOpenBalanceRepository;
import lu.wealins.webia.services.core.service.ReconciliableCommissionService;
import lu.wealins.webia.services.core.utils.CommissionUtils;

/**
 * Default implementation of {@link ReconciliableCommissionService} interface.
 * 
 * @author oro
 *
 */
@Service
public class ReconciliableCommissionServiceImpl implements ReconciliableCommissionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReconciliableCommissionServiceImpl.class);

	@Autowired
	private CommissionToPayMapper commissionToPayMapper;

	@Autowired
	private SapOpenBalanceMapper sapOpenBalanceMapper;

	@Autowired
	private SapOpenBalanceRepository sapOpenBalanceRepository;

	@Autowired
	private CommissionToPayRepository commissionToPayRepository;

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> retrieveWebiaReconcilableCommission(String commissionType) {
		LOGGER.info("Retrieving webia reconcilable commission with type {0}", commissionType);
		List<String> commissionTypes = CommissionUtils.asWebiaCommissionTypes(commissionType);
		List<CommissionToPayEntity> result = commissionToPayRepository.findReconcilableCommissionByType(commissionTypes);
		return commissionToPayMapper.asCommissionToPayDTOs(result);
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public List<CommissionToPayDTO> retrieveSapReconcilableCommission(String commissionType) {
		LOGGER.info("Retrieving sap reconcilable commission with aggregated types {0}", commissionType);

		List<SapOpenBalanceEntity> sapOpenBalances = new ArrayList<>();
		sapOpenBalances.addAll(sapOpenBalanceRepository.findReconcilableOpenBalanceByType(commissionType));

		return sapOpenBalanceMapper.asCommissionToPayDTOs(sapOpenBalances);
	}

}
