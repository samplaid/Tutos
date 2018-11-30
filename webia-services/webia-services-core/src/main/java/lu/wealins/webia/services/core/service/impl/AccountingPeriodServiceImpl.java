package lu.wealins.webia.services.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.AccountingPeriodDTO;
import lu.wealins.common.dto.webia.services.GetAccountingPeriodResponse;
import lu.wealins.webia.services.core.mapper.AccountingPeriodMapper;
import lu.wealins.webia.services.core.persistence.entity.AccountingPeriodEntity;
import lu.wealins.webia.services.core.persistence.repository.AccountingPeriodRepository;
import lu.wealins.webia.services.core.service.AccountingPeriodService;

@Service
@Transactional
public class AccountingPeriodServiceImpl implements AccountingPeriodService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountingPeriodServiceImpl.class);

	@Autowired
	private AccountingPeriodRepository accountingPeriodRepository;

	@Autowired
	private AccountingPeriodMapper accountingPeriodMapper;

	@Override
	public GetAccountingPeriodResponse getActiveAccountingPeriod() {

		AccountingPeriodEntity entity = accountingPeriodRepository.findActivePeriod();
		AccountingPeriodDTO dto = accountingPeriodMapper.asAccountingPeriodDTO(entity);

		GetAccountingPeriodResponse response = new GetAccountingPeriodResponse();
		response.setAccountingPeriod(dto);
		return response;
	}

}
