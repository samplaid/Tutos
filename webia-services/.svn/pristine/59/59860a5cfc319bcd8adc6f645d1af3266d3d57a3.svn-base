package lu.wealins.webia.services.core.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lu.wealins.common.dto.webia.services.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;

import lu.wealins.common.dto.webia.services.AccountingNavDTO;
import lu.wealins.common.dto.webia.services.AccountingNavToInject;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlRequest;
import lu.wealins.common.dto.webia.services.CloturedVniInjectionControlResponse;

import lu.wealins.webia.services.core.mapper.AccountingNavMapper;
import lu.wealins.webia.services.core.persistence.entity.AccountingNavEntity;
import lu.wealins.webia.services.core.persistence.repository.AccountingNavRepository;
import lu.wealins.webia.services.core.service.AccountingNavService;
import lu.wealins.webia.services.core.service.validations.AccountingNavValidationService;
import lu.wealins.webia.services.core.utils.CloturedVNIFundType;
import lu.wealins.webia.services.core.utils.InjectionOperationType;
import lu.wealins.common.dto.webia.services.GetAccountingNavRequest;
import lu.wealins.common.dto.webia.services.GetAccountingNavResponse;

import lu.wealins.webia.services.core.service.validations.AccountingNavValidationService;
import lu.wealins.webia.services.core.utils.CloturedVNIFundType;
import lu.wealins.webia.services.core.utils.InjectionOperationType;

@Service
@Transactional
public class AccountingNavServiceImpl implements AccountingNavService {

	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountingNavServiceImpl.class);
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
	@Autowired
	private AccountingNavRepository accountingNavRepository;

	@Autowired
	private AccountingNavValidationService validator;


	@Autowired
	private AccountingNavMapper accountingNavMapper;

	@Override
	public GetAccountingNavResponse getAccountingNavByFundIdAndNavDate(GetAccountingNavRequest request) {

		AccountingNavEntity result = accountingNavRepository.findByFundIdAndNavDate(request.getFundId(), request.getNavDate());
		AccountingNavDTO toReturn = accountingNavMapper.asAccountingNavDTO(result);

		GetAccountingNavResponse response = new GetAccountingNavResponse();
		response.setAccountingNav(toReturn);
		return response;
	}

	@Override
	public GetAccountingNavResponse getAccountingNavByIsinAndNavDate(GetAccountingNavRequest request) {
		AccountingNavEntity result = accountingNavRepository.findByIsinAndNavDate(request.getFundId(),
				request.getNavDate(), request.getCurrency());
		AccountingNavDTO toReturn = accountingNavMapper.asAccountingNavDTO(result);

		GetAccountingNavResponse response = new GetAccountingNavResponse();
		response.setAccountingNav(toReturn);
		return response;
	}

	@Override
	public CloturedVniInjectionControlResponse injectAccountingNav(SecurityContext context,
			AccountingNavToInject request) throws ParseException {
		CloturedVniInjectionControlResponse response = new CloturedVniInjectionControlResponse();
		if (InjectionOperationType.INSERT.name().equalsIgnoreCase(request.getOperationType())) {
			insertNewCloturedVni(request, response);
		} else if (InjectionOperationType.UPDATE.name().equalsIgnoreCase(request.getOperationType())) {
			updateExistingCloturedVni(request, response);
		} else {
			response.setErrorLine("UNSUPPORTED OPERATION IN THAT VNI");
		}
		return response;
	}

	@Override
	public CloturedVniInjectionControlResponse checkAccountingNav(CloturedVniInjectionControlRequest request)
			throws ParseException {
		CloturedVniInjectionControlResponse cloturedVniInjectionControlResponse = new CloturedVniInjectionControlResponse();

		if (request == null) {
			cloturedVniInjectionControlResponse.setErrorLine("LA VNI NE PEUT ETRE NULL");
			return cloturedVniInjectionControlResponse;
		}

		Optional<String> vadationResult = validator.validate(request);

		if (vadationResult.isPresent()) {
			String errorLine = getErrorLine(request, vadationResult.get());
			cloturedVniInjectionControlResponse.setErrorLine(errorLine);

			return cloturedVniInjectionControlResponse;
		}

		InjectionOperationType OperationType = getOperationTypeOfRequest(request, formatter);
		BigDecimal newPrice = provideNewPrice(request, OperationType);
		String successLine = getSuccessLine(request, OperationType, newPrice);
		cloturedVniInjectionControlResponse.setSuccessLine(successLine);

		return cloturedVniInjectionControlResponse;
	}

	/**
	 * Get error line.
	 * 
	 * @param request
	 *            the request containing data
	 * @param vadationResult
	 * @return error line
	 */
	private String getErrorLine(CloturedVniInjectionControlRequest request, String errorMessage) {
		return String.join(",", request.getFundId(), request.getPrice().toString(), request.getCurrency(),
				request.getPriceDate(), request.getIsinCode(), request.getFundType(), errorMessage);

	}

	/**
	 * Get success line.
	 * 
	 * @param request
	 *            the request containing data to inject
	 * @param navDate
	 * @param OperationType
	 * @param newPrice
	 *            the price to inject
	 * @return success line
	 * @throws ParseException
	 */
	private String getSuccessLine(CloturedVniInjectionControlRequest request, InjectionOperationType OperationType,
			BigDecimal newPrice) throws ParseException {
		Date navDate = formatter.parse(request.getPriceDate());
		return String.join(",", request.getFundId() != null ? request.getFundId() : StringUtils.EMPTY,
				newPrice.toString(), request.getCurrency(),
				formatter.format(navDate), formatter.format(new Date()),
				request.getIsinCode() != null ? request.getIsinCode() : StringUtils.EMPTY, request.getFundType(),
				OperationType.name());

	}

	/**
	 * Provide new price for operation.
	 * 
	 * @param request
	 *            the vni to inject
	 * @param navDate
	 * @param OperationType
	 *            the operation
	 * @return the price to inject
	 * @throws ParseException
	 */
	private BigDecimal provideNewPrice(CloturedVniInjectionControlRequest request, InjectionOperationType OperationType)
			throws ParseException {

		if (InjectionOperationType.UPDATE != OperationType) {
			return request.getPrice();
		}

		BigDecimal newPrice;
		Date navDate = formatter.parse(request.getPriceDate());
		AccountingNavEntity accountingNavEntity = null;
		if (CloturedVNIFundType.FIDFAS.name().equals(request.getFundType())) {
			accountingNavEntity = accountingNavRepository.findByFundIdCurrencyAndNavDate(request.getFundId(),
					request.getCurrency(), navDate);
		} else if (CloturedVNIFundType.FE.name().equals(request.getFundType())) {
			accountingNavEntity = accountingNavRepository.findByIsinCodeCurrencyAndNavDate(request.getIsinCode(),
					request.getCurrency(), navDate);
		}

		if (accountingNavEntity == null) {
			throw new UnsupportedOperationException("La VNI DOIT ETRE UNE FID, UNE FAS, OU UNE FE");
		}

		newPrice = accountingNavEntity.getNav().add(request.getPrice());
		return newPrice;
	}

	/**
	 * Get Operation Type of VNI to inject.
	 * 
	 * @param request
	 *            vni to inject
	 * @param formatter
	 *            formatter of navDate
	 * @return operation type
	 * @throws ParseException
	 */
	private InjectionOperationType getOperationTypeOfRequest(CloturedVniInjectionControlRequest request,
			SimpleDateFormat formatter) throws ParseException {
		Date navDate = formatter.parse(request.getPriceDate());
		int existsVni = -1;
		if (CloturedVNIFundType.FIDFAS.name().equals(request.getFundType())) {
			existsVni = accountingNavRepository.ExistFidFasVni(request.getFundId(), request.getCurrency(), navDate);
		} else if (CloturedVNIFundType.FE.name().equals(request.getFundType())) {
			existsVni = accountingNavRepository.ExistFeVni(request.getIsinCode(), request.getCurrency(), navDate);
		}

		if (existsVni > 0) {
			return InjectionOperationType.UPDATE;
		}
		return InjectionOperationType.INSERT;

	}

	/**
	 * Insert clotured vni.
	 * 
	 * @param request
	 *            clotured vni to inject
	 * @param response
	 */
	private void insertNewCloturedVni(lu.wealins.common.dto.webia.services.AccountingNavToInject request,
			CloturedVniInjectionControlResponse response) {
		AccountingNavEntity accountingNavEntity = initAccountingNavEntityToSave(request);
		accountingNavRepository.save(accountingNavEntity);
		response.setSuccessLine("SUCCESS");
	}

	/**
	 * Update existing clotured vni.
	 * 
	 * @param request
	 *            clotured vni to update
	 * @param response
	 */
	private void updateExistingCloturedVni(AccountingNavToInject request,
			CloturedVniInjectionControlResponse response) {
		if (CloturedVNIFundType.FE.name().equalsIgnoreCase(request.getFundType())) {
			accountingNavRepository.updateFE(request.getNavAmount(), request.getIsinCode(), request.getCurrency(),
					request.getNavDate());
			response.setSuccessLine("SUCCESS");
		} else if (CloturedVNIFundType.FIDFAS.name().equalsIgnoreCase(request.getFundType())) {
			accountingNavRepository.updateFIDandFAS(request.getNavAmount(), request.getFund(), request.getCurrency(),
					request.getNavDate());
			response.setSuccessLine("SUCCESS");
		}

	}

	/**
	 * Initialize AccountingNavEntity from AccountingNavToInject.
	 * 
	 * @param request
	 *            AccountingNavToInject
	 * @return AccountingNavEntity
	 */
	private AccountingNavEntity initAccountingNavEntityToSave(AccountingNavToInject request) {
		AccountingNavEntity accountingNavEntity = new AccountingNavEntity();
		accountingNavEntity.setCreationDate(request.getCreateDate());
		accountingNavEntity.setNavDate(request.getNavDate());
		accountingNavEntity.setCurrency(request.getCurrency());
		accountingNavEntity.setFundId(request.getFund());
		accountingNavEntity.setIsinCode(request.getIsinCode());
		accountingNavEntity.setNav(request.getNavAmount());
		return accountingNavEntity;
	}

}
