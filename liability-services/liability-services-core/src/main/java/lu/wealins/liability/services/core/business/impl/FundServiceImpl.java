package lu.wealins.liability.services.core.business.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.tempuri.wssexpfds.WSSEXPFDS;
import org.tempuri.wssexpfds.WssexpfdsExport;
import org.tempuri.wssexpfds.WssexpfdsImport;
import org.tempuri.wssexpfds.WssexpfdsImport.ImpFdsFunds;
import org.tempuri.wssstvlf.WSSSTVLF;
import org.tempuri.wssstvlf.WssstvlfImport;
import org.tempuri.wssstvlf.WssstvlfImport.ImpFundValuationFundPrices;
import org.tempuri.wssupdfds.Exception_Exception;
import org.tempuri.wssupdfds.WSSUPDFDS;
import org.tempuri.wssupdfds.WssupdfdsExport;
import org.tempuri.wssupdfds.WssupdfdsImport;
import org.tempuri.wssupdfds.WssupdfdsImport.ImpCreateModifyDatesFunds;
import org.tempuri.wssupdfds.WssupdfdsImport.ImpMethodCommunications;
import org.tempuri.wssupdfds.WssupdfdsImport.ImpUseridUsers;
import org.tempuri.wssupdfds.WssupdfdsImport.ImpValidationUsers;

import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.FundDTO;
import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.liability.services.FundSearcherRequest;
import lu.wealins.common.dto.liability.services.SearchResult;
import lu.wealins.common.dto.liability.services.UoptDetailDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.FundStatus;
import lu.wealins.common.dto.liability.services.enums.FundSubType;
import lu.wealins.common.dto.liability.services.enums.PricingFrequencyType;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.FundPriceService;
import lu.wealins.liability.services.core.business.FundService;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.business.UoptDetailService;
import lu.wealins.liability.services.core.business.exceptions.FundCreationException;
import lu.wealins.liability.services.core.business.exceptions.FundStatusException;
import lu.wealins.liability.services.core.business.exceptions.ReportExceptionHelper;
import lu.wealins.liability.services.core.business.exceptions.WebServiceInvocationException;
import lu.wealins.liability.services.core.mapper.FundMapper;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.persistence.repository.FundRepository;
import lu.wealins.liability.services.core.persistence.specification.FundSpecifications;
import lu.wealins.liability.services.core.utils.HistoricManager;
import lu.wealins.liability.services.core.utils.IsinUtils;
import lu.wealins.liability.services.core.utils.SecurityContextUtils;
import lu.wealins.liability.services.core.utils.constantes.Constantes;
import lu.wealins.liability.services.ws.rest.exception.WssUpdateFundException;

@Service
public class FundServiceImpl implements FundService {

	private static final char WHITE_SPACE = ' ';
	private static final int FDS_ID_SIZE = 8;
	private static final String FUND_ID_CANNOT_BE_NULL = "Fund id cannot be null.";
	private static final short FLAG_EXPORTED_FUND = 1;
	private static final Logger logger = LoggerFactory.getLogger(FundServiceImpl.class);
	private static final String THE_FUND_CLASSIFICATION_IS_MANDATORY = "the fund classification is mandatory.";
	private static final String THE_CIRCULAR_LETTER_IS_MANDATORY = "the circular letter is mandatory.";
	private static final String THE_ISIN_CODE_IS_NOT_VALID = "the ISIN Code is not valid.";
	private static final String THE_PRICING_DAY_OF_MONTH_IS_MANDATORY = "the pricing day of month is mandatory.";
	private static final String THE_LC15_3_D_IS_MANDATORY = "the LC15/3 D is mandatory.";
	private static final String THE_LC15_3_C_IS_MANDATORY = "the LC15/3 C is mandatory.";
	private static final String THE_LC15_3_B_IS_MANDATORY = "the LC15/3 B is mandatory.";
	private static final String THE_LC15_3_A_IS_MANDATORY = "the LC15/3 A is mandatory.";
	private static final String THE_LC15_3_N_IS_MANDATORY = "the LC15/3 N is mandatory.";
	private static final String THE_LC08_1_IS_MANDATORY = "the LC08/1 is mandatory.";
	private static final String THE_UCITS_IS_MANDATORY = "the ucits is mandatory.";
	private static final String THE_PRICING_DAY_IS_MANDATORY = "the pricing day is mandatory.";
	private static final String THE_PRICING_FREQUENCY_IS_MANDATORY = "the pricing frequency is mandatory.";
	private static final String THE_ISIN_IS_MANDATORY = "the ISIN is mandatory";
	private static final String THE_CURRENCY_IS_MANDATORY = "the currency is mandatory.";
	private static final String DOCUMENTATION_IS_MANDATORY = "the documentation name is mandatory.";
	private static final String THE_CUSTODIAN_BANK_IS_MANDATORY = "the custodian bank is mandatory.";
	private static final String FDS_ID = "fdsId";
	private static final String WSSUPDFDS_PROCESS = "WSSUPDFDS";
	private static final Logger log = LoggerFactory.getLogger(FundServiceImpl.class);
	private static final String FUND_CANNOT_BE_NULL = "Fund cannot be null.";
	private static final int FUND_NAME_COLUMN_SIZE = 35;
	private static final String CUSTODIAN_BANK_CANNOT_BE_NULL = "Custodian bank cannot be null";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";
	private static final String IN_FUND = "Fund ";
	private static final char PERCENT_SYMBOLE = '%';

	private enum ACTION {
		CREATE, UPDATE
	}

	@Value("${liability.ws.credential.login}")
	private String wsLoginCredential;

	@Value("${liability.ws.credential.password}")
	private String wsPasswordCredential;

	@Autowired
	private AgentService agentService;

	@Autowired
	private FundRepository fundRepository;

	@Autowired
	private FundTransactionService fundTransactionService;

	@Autowired
	private WSSUPDFDS wssupdfds;

	@Autowired
	private WSSSTVLF wssstvlf;

	@Autowired
	private FundMapper fundMapper;	
	@Autowired
	private WSSEXPFDS wssexppfds;

	@Autowired
	private UoptDetailService uoptDetailService;

	@Autowired
	private HistoricManager historicManager;

	@Autowired
	private SecurityContextUtils securityContextUtils;

	@Autowired
	private FundPriceService fundPriceService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#getFund(java.lang .String)
	 */
	@Override
	public FundDTO getFund(String fundId) {
		return fundMapper.asFundDTO(getFundEntity(fundId));
	}

	@Override
	public FundEntity getFundEntity(String fundId) {
		Assert.notNull(fundId, FUND_ID_CANNOT_BE_NULL);

		String fundIdWithRightPad = org.apache.commons.lang.StringUtils.rightPad(fundId, FDS_ID_SIZE, WHITE_SPACE);
		return fundRepository.findOne(fundIdWithRightPad);
	}

	@Override
	public FundLiteDTO getFundLite(String fundId) {
		return fundMapper.asFundLiteDTO(getFundEntity(fundId));
	}

	@Override
	public Collection<FundLiteDTO> getFunds(Collection<String> fundIds) {
		Collection<String> fundIdsWithRightPad = fundIds.stream().map(x -> org.apache.commons.lang.StringUtils.rightPad(x, FDS_ID_SIZE, WHITE_SPACE)).collect(Collectors.toList());
		return fundMapper.asFundLiteDTOs(fundRepository.findAll(fundIdsWithRightPad));
	}

	@Override
	public Collection<String> getFundIds(String agentId) {
		AgentDTO agent = agentService.getAgent(agentId);
		Assert.notNull(agent, "Agent with the id " + agentId + " has been not found.");

		if (AgentCategory.DEPOSIT_BANK.getCategory().equals(agent.getCategory())) {
			return fundRepository.findFdsIdsByDepositBank(agentId);
		} else if (AgentCategory.ASSET_MANAGER.getCategory().equals(agent.getCategory())) {
			return fundRepository.findFdsIdsByAssetManager(agentId);
		}
			throw new IllegalStateException("Agent of category " + agent.getCategory() + " is not managed in order to retrieve funds.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#update(lu.wealins .liability.services.ws.rest.dto.FundDTO, java.lang.String)
	 */
	@Override
	public FundDTO update(FundDTO updatedFund) {
		Assert.notNull(updatedFund, FUND_CANNOT_BE_NULL);
		Assert.notNull(updatedFund.getFdsId(), FUND_CANNOT_BE_NULL);

		setPricingRules(updatedFund);
		removeIbanSpace(updatedFund);
		List<String> errors = new ArrayList<>();
		List<String> warns = new ArrayList<>();

		validateFund(updatedFund, errors, warns);
		ReportExceptionHelper.throwIfErrorsIsNotEmptyWithWarns(errors, warns, FundCreationException.class);

		// FID and FAS
		if (isFIDorFAS(updatedFund)) {
			FundDTO currentFund = getFund(updatedFund.getFdsId());

			if (currentFund != null) {
				if (currentFund.getStatus() == 0) {
					solveFundStatus(updatedFund);
				} else if (currentFund.getStatus() > 0 && updatedFund.getStatus() == 0) {
					updatedFund.setStatus(currentFund.getStatus());
				} else if (updatedFund.getStatus() > 2 || currentFund.getStatus() > 2) {
					throw new FundStatusException("The fund with id " + updatedFund.getFdsId() + " has an invalid status ("
							+ updatedFund.getStatus() + ")!");
				}
			} else {
				ReportExceptionHelper.throwIfErrorsIsNotEmpty(Arrays.asList("Could not find the fund with id=" + updatedFund.getFdsId()), FundCreationException.class);
			}

		}

		setupName(updatedFund);
		setupDocumentationName(updatedFund);
		_internal_soap(updatedFund, ACTION.UPDATE);

		log.info("WSSUPDFDS has successfully updated the fund {}", updatedFund.getFdsId());

		FundDTO response = getFund(updatedFund.getFdsId());
		response.setWarns(warns);
		
		return response;
	}

	private void solveFundStatus(FundDTO fund) {
		List<String> errors = new ArrayList<>();
		validateActiveFund(fund, null, errors);
		// Do not throw exception. The validation is used to set the status !
		// ReportExceptionHelper.throwIfErrorsIsNotEmpty(errors, FundCreationException.class);

		if (CollectionUtils.isEmpty(errors)) {
			/// TODO add condition on StringUtils.isNotBlank(fund.getClassOfRisk())
			/// after confirmation.
			// The fund is complete change its status to active.
			fund.setStatus(1);
		} else {
			fund.setStatus(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#create(lu.wealins .liability.services.ws.rest.dto.FundDTO, java.lang.String)
	 */
	@Override
	public FundDTO create(FundDTO fund) {
		Assert.notNull(fund, FUND_CANNOT_BE_NULL);

		initDefaultValues(fund);
		setPricingRules(fund);
		removeIbanSpace(fund);

		List<String> errors = new ArrayList<>();
		List<String> warns = new ArrayList<>();
		validateFund(fund, errors, warns);

		ReportExceptionHelper.throwIfErrorsIsNotEmptyWithWarns(errors, warns, FundCreationException.class);

		fund.setCreatedBy(securityContextUtils.getPreferredUsername());
		fund.setCreatedProcess(WSSUPDFDS_PROCESS);
		setupDocumentationName(fund);
		setupName(fund);
		if (isFIDorFAS(fund)) {
			solveFundStatus(fund);
		}
		_internal_soap(fund, ACTION.CREATE);

		log.info("WSSUPDFDS has successfully created the fund {}", fund.getFdsId());

		FundDTO response = getFund(fund.getFdsId());
		response.setWarns(warns);
		
		return response;
	}

	/**
	 * @param fund
	 */
	private void removeIbanSpace(FundDTO fund) {
		if (fund != null && StringUtils.isNotBlank(fund.getIban())) {
			fund.setIban(fund.getIban().replaceAll(" ", ""));
		}
	}

	private void setPricingRules(FundDTO fund) {
		if (isFeOrFic(fund)) {
			switch (fund.getPricingFrequency()) {
			case 2:
			case 9:
				fund.setPricingDayOfMonth(null);
				break;
			case 3:
				fund.setPricingDay(null);
				break;
			default:
				fund.setPricingDayOfMonth(null);
				fund.setPricingDay(null);
				break;
			}
		}
	}

	private void checkIsinFormat(FundDTO fund, List<String> errors) {
		String headMsg = getHeadMsg(fund);
		if (isFe(fund)) {
			if (StringUtils.isNotBlank(fund.getIsinCode()) && !IsinUtils.isValidCode(fund.getIsinCode())) {
				errors.add(headMsg + THE_ISIN_CODE_IS_NOT_VALID);
			}
		}
	}

	private void checkFeFicMandatoryFields(FundDTO fund, List<String> errors) {
		String headMsg = getHeadMsg(fund);
		if (isFeOrFic(fund)) {
			if (StringUtils.isEmpty(fund.getCurrency())) {
				errors.add(headMsg + THE_CURRENCY_IS_MANDATORY);
			}
			if (StringUtils.isEmpty(fund.getDocumentationName())) {
				errors.add(headMsg + DOCUMENTATION_IS_MANDATORY);
			}
			if (StringUtils.isEmpty(fund.getDepositBank())) {
				errors.add(headMsg + THE_CUSTODIAN_BANK_IS_MANDATORY);
			}

			// check pricing
			if (fund.getPricingFrequency() <= 0) {
				errors.add(headMsg + THE_PRICING_FREQUENCY_IS_MANDATORY);
			}
			if (fund.getPricingFrequency() == 2) {
				if (fund.getPricingDay() == null || fund.getPricingDay() <= 0) {
					errors.add(headMsg + THE_PRICING_DAY_IS_MANDATORY);
				}
			}
			if (fund.getPricingFrequency() == 3) {
				if (fund.getPricingDayOfMonth() == null || fund.getPricingDayOfMonth() <= 0) {
					errors.add(headMsg + THE_PRICING_DAY_OF_MONTH_IS_MANDATORY);
				}
			}
			if (isFe(fund)) {
				if (StringUtils.isEmpty(fund.getIsinCode())) {
					errors.add(headMsg + THE_ISIN_IS_MANDATORY);
				}
				if (fund.getUcits() == null) {
					errors.add(headMsg + THE_UCITS_IS_MANDATORY);
				}
				if (fund.getMaxAllocationPercent() == null) {
					errors.add(headMsg + THE_LC08_1_IS_MANDATORY);
				}
				if (fund.getMaxAllocationN() == null) {
					errors.add(headMsg + THE_LC15_3_N_IS_MANDATORY);
				}
				if (fund.getMaxAllocationA() == null) {
					errors.add(headMsg + THE_LC15_3_A_IS_MANDATORY);
				}
				if (fund.getMaxAllocationB() == null) {
					errors.add(headMsg + THE_LC15_3_B_IS_MANDATORY);
				}
				if (fund.getMaxAllocationC() == null) {
					errors.add(headMsg + THE_LC15_3_C_IS_MANDATORY);
				}
				if (fund.getMaxAllocationD() == null) {
					errors.add(headMsg + THE_LC15_3_D_IS_MANDATORY);
				}
			} else if (isFic(fund)) {
				if (StringUtils.isEmpty(fund.getGroupingCode())) {
					errors.add(headMsg + THE_CIRCULAR_LETTER_IS_MANDATORY);
				}
				if (StringUtils.isEmpty(fund.getFundClassification())) {
					errors.add(headMsg + THE_FUND_CLASSIFICATION_IS_MANDATORY);
				}
			}
		}

	}

	private void initDefaultValues(FundDTO fund) {
		if (fund.getPricingFrequency() == 0) {
			fund.setPricingFrequency(PricingFrequencyType.DAILY.getValue());
		}
		if (isFIDorFAS(fund)) {
			fund.setFwdPriceReportDays(-1); // hard coded value for FID/FAS creation
		}
	}

	@Override
	public boolean validateFund(FundDTO fund) {
		List<String> errors = new ArrayList<>();
		List<String> warns = new ArrayList<>();

		validateFund(fund, errors, warns);
		ReportExceptionHelper.throwIfErrorsIsNotEmptyWithWarns(errors, warns, FundCreationException.class);

		return true;
	}

	@Override
	public boolean validateFunds(List<String> fdsIds) {
		Assert.notNull(fdsIds);
		boolean isValid = true;
		for (String fdsId : fdsIds) {
			isValid |= validateFund(getFund(fdsId));
		}

		return isValid;
	}

	private void validateFund(FundDTO fund, List<String> errors, List<String> warns) {

		// check if it is an Update and then if it can be updated
		if (fund != null) {
			FundDTO existingFund = null;

			if (StringUtils.isNotBlank(fund.getFdsId())) {
				existingFund = getFund(fund.getFdsId());
			}

			if (existingFund != null && existingFund.getStatus() > 0) {
				validateActiveFund(fund, existingFund, errors);
			} else {
				validateInactiveFund(fund, errors);
			}

			checkDepositAccountLength(fund, errors);
			checkCustodianFees(fund, errors);
			checkFeFicMandatoryFields(fund, errors);
			checkIsinFormat(fund, errors);
			checkActiveCircular(fund, errors);
			checkFinancialAdvisorFeesForPsiNotEmpty(fund, errors);
			
			checkAccountRoot(fund, warns);

		}

	}

	/**
	 * @param fund
	 * @param errors
	 */
	private void checkCustodianFees(FundDTO fund, List<String> errors) {
		Assert.notNull(errors);

		if (isFid(fund) && BooleanUtils.isTrue(fund.getExAllInFees())
				&& (!nullOrZero(fund.getBankDepositFee()) || !nullOrZero(fund.getDepositBankFlatFee()))) {
			errors.add("It’s not possible to have custodian fees when all in fees is present ");
		}
	}

	private boolean nullOrZero(BigDecimal value) {
		return value == null || BigDecimal.ZERO.equals(value);
	}

	/**
	 * @param fund
	 * @param errors
	 */
	private void checkDepositAccountLength(FundDTO fund, List<String> errors) {
		Assert.notNull(errors);

		if (isFe(fund)) {
			// length = 20
			if (StringUtils.isNotBlank(fund.getDepositAccount()) && fund.getDepositAccount().length() > 20) {
				errors.add("The length of the deposit account should be less than or equal to 20 characters.");
			}
		} else {
			// length = 35
			if (fund != null && StringUtils.isNotBlank(fund.getDepositAccount()) && fund.getDepositAccount().length() > 35) {
				errors.add("The length of the deposit account should be less than or equal to 35 characters.");
			}
		}
	}

	private void checkFinancialAdvisorFeesForPsiNotEmpty(FundDTO fund, List<String> errors) {
		Assert.notNull(errors);

		if (fund != null && isFas(fund) && fund.getFinancialAdvisorAgent() != null
				&& AgentCategory.PRESTATION_SERVICE_INVEST.getCategory().equals(fund.getFinancialAdvisorAgent().getCategory())
				&& (fund.getFinAdvisorFee() == null || fund.getFinAdvisorFee().intValue() < 0)) {
			errors.add("The financial advisor fee is mandatory for the prestation service investment (a.k.a PSI).");
		}
	}

	private void checkActiveCircular(FundDTO fund, List<String> errors) {
		if (fund != null && !StringUtils.isEmpty(fund.getGroupingCode())) {
			List<UoptDetailDTO> circulars = this.uoptDetailService.getCircularLetters();
			boolean selectedCircularMatched = circulars.stream()
					.anyMatch(circular -> !StringUtils.isEmpty(circular.getKeyValue()) && fund.getGroupingCode().trim().equals(circular.getKeyValue().trim()));

			if (!selectedCircularMatched) {
				errors.add("The circular with code (" + fund.getGroupingCode().trim() + ") does not exist uopt_details table.");
				return;
			}

			boolean selectedCircularEnabled = circulars.stream().anyMatch(
					circular -> !StringUtils.isEmpty(circular.getKeyValue()) && fund.getGroupingCode().trim().equals(circular.getKeyValue().trim()) && Integer.valueOf(1).equals(circular.getStatus()));

			// for updated fund$
			FundEntity exstingFund = null;

			if (StringUtils.isNotBlank(fund.getFdsId())) {
				exstingFund = getFundEntity(fund.getFdsId());
			}

			if (exstingFund != null
					&& exstingFund.getGroupingCode() != null
					&& !StringUtils.isEmpty(exstingFund.getGroupingCode().trim())) {
				final FundEntity local = exstingFund;
				boolean existingCircularMatched = circulars.stream()
						.anyMatch(circular -> !StringUtils.isEmpty(circular.getKeyValue()) && local.getGroupingCode().trim().equals(circular.getKeyValue().trim()));

				if (!existingCircularMatched) {
					errors.add("The circular with code (" + exstingFund.getGroupingCode().trim() + ") does not exist in uopt_details table.");
					return;
				}

				boolean existingCircularEnabled = circulars.stream().anyMatch(circular -> !StringUtils.isEmpty(circular.getKeyValue())
						&& local.getGroupingCode().trim().equals(circular.getKeyValue().trim()) && Integer.valueOf(1).equals(circular.getStatus()));

				if (!selectedCircularEnabled && existingCircularEnabled) {
					errors.add("An inactive circular cannot be selected for the fund with id=" + fund.getFdsId() + ".");
				} else if (!existingCircularEnabled && !selectedCircularEnabled && !exstingFund.getGroupingCode().trim().equals(fund.getGroupingCode().trim())) {
					errors.add("Either an active or the existing inactive circular (" + exstingFund.getGroupingCode().trim() + ") should be selected for the fund with id=" + fund.getFdsId() + ".");
				}

			} else if (exstingFund == null || StringUtils.isEmpty(fund.getFdsId())) {
				if (!selectedCircularEnabled) {
					errors.add("An inactive circular cannot be selected for the new funds.");
				}
			}
		}
	}

	private void validateInactiveFund(FundDTO fund, List<String> errors) {
		String headMsg = getHeadMsg(fund);
		if (isFIDorFAS(fund)) {
			if (StringUtils.isEmpty(fund.getCurrency())) {
				errors.add(headMsg + THE_CURRENCY_IS_MANDATORY);
			}

			if (StringUtils.isEmpty(fund.getDepositBank())) {
				errors.add(headMsg + THE_CUSTODIAN_BANK_IS_MANDATORY);
			}

			if (StringUtils.isEmpty(fund.getAssetManager()) && isFIDorFAS(fund)) {
				errors.add(headMsg + "the asset manager is mandatory.");
			}

			// Annulé sur demande de RVN le 02/10/2017
			// if (!StringUtils.isEmpty(fund.getFinancialAdvisor()) && fund.getFinAdvisorFee() == null) {
			// errors.add(headMsg + "the financial advisor fees are mandatory.");
			// }
		}
	}

	private void validateActiveFund(FundDTO fund, FundDTO existingFund, List<String> errors) {
		String headMsg = getHeadMsg(fund);
		if (isFIDorFAS(fund)) {
			validateInactiveFund(fund, errors);

			if (StringUtils.isEmpty(fund.getAccountRoot())) {
				errors.add(headMsg + "the account root is mandatory.");
			}

			// additional check for WEALINS FID/FAS (not those from old FISA)
			if (fund.getExportedFund() == null || fund.getExportedFund().intValue() != 1) {

				// because if it is a fid and all in fees checked, custody bank should be empty or 0. Thus not mandatory
				if ((!isFid(fund) || BooleanUtils.isNotTrue(fund.getExAllInFees()))
						&& fund.getBankDepositFee() == null
						&& fund.getDepositBankFlatFee() == null) {
					errors.add(headMsg + "the custody fees is mandatory.");
				}

				if (fund.getAssetManagerFee() == null && fund.getFinFeesFlatAmount() == null) {
					errors.add(headMsg + "the asset manager fees is mandatory.");
				}
				if (StringUtils.isEmpty(fund.getGroupingCode())) {
					errors.add(headMsg + THE_CIRCULAR_LETTER_IS_MANDATORY);
				}
				if (StringUtils.isEmpty(fund.getFundClassification())) {
					errors.add(headMsg + THE_FUND_CLASSIFICATION_IS_MANDATORY);
				}
				if (StringUtils.isEmpty(fund.getRiskProfile())) {
					errors.add(headMsg + "the risk profile is mandatory.");
				}
			}

			if (existingFund != null && existingFund.getStatus() == FundStatus.IN_FORCE.getValue() && fundTransactionService.countTransactions(fund.getFdsId()) > 0) {
				// Annulé sur demande de RVN le 02/10/2017
				// if (isDifferent(existingFund.getAssetManager(), fund.getAssetManager())) {
				// throw new WssUpdateFundException(IN_FUND + fund.getFdsId() + " is in use and the asset manager can't be modified.", "");
				// }
				if (isDifferent(existingFund.getDepositBank(), fund.getDepositBank())) {
					throw new WssUpdateFundException(IN_FUND + fund.getFdsId() + " is in use and the custodian bank can't be modified.", "");
				}
				if (isDifferent(existingFund.getCurrency(), fund.getCurrency())) {
					throw new WssUpdateFundException(IN_FUND + fund.getFdsId() + " is in use and the fund currency can't be modified.", "");
				}
			}

		}
		if (isFe(fund)) {
			if (StringUtils.isEmpty(fund.getDepositAccount())) {
				errors.add(headMsg + "the deposit account is mandatory.");
			}
			if (existingFund != null && existingFund.getStatus() == 1 && fundTransactionService.countTransactions(fund.getFdsId()) > 0) {
				if (isDifferent(existingFund.getCurrency(), fund.getCurrency())) {
					throw new WssUpdateFundException(IN_FUND + fund.getFdsId() + " is in use and the currency can't be modified.", "");
				}
				if (isDifferent(existingFund.getIsinCode(), fund.getIsinCode())) {
					throw new WssUpdateFundException(IN_FUND + fund.getFdsId() + " is in use and the ISIN code can't be modified.", "");
				}
				if (isDifferent(existingFund.getDepositAccount(), fund.getDepositAccount())) {
					throw new WssUpdateFundException(IN_FUND + fund.getFdsId() + " is in use and the deposit account can't be modified.", "");
				}
			}
		}
	}

	private boolean isDifferent(Object o1, Object o2) {
		return (o1 == null && o2 != null) || (o1 != null && !o1.equals(o2));
	}

	private String getHeadMsg(FundDTO fund) {
		return StringUtils.isNotBlank(fund.getFdsId()) ? IN_FUND + fund.getFdsId() + " - " : IN_FUND + " - ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#validateActiveFunds(java.util.List)
	 */
	@Override
	public List<String> validateActiveFunds(List<String> fundIds) {
		List<FundEntity> funds = fundRepository.findAll(fundIds);

		List<FundEntity> fundsNotActive = funds.stream().filter(x -> x.getStatus() == null || x.getStatus().intValue() != FundStatus.IN_FORCE.getValue()).collect(Collectors.toList());

		return fundsNotActive.stream().map(x -> "Funds " + x.getFdsId() + " is not active.").collect(Collectors.toList());
	}

	/**
	 * Invoke the underneath WSSUPDFDS soap service to create or update a fund.
	 * 
	 * @param context The security principal
	 * @param fund The fund to create or update
	 * @param action CREATE or UPDATE
	 * @return
	 * @throws WssUpdateFundException
	 */
	private WssupdfdsExport _internal_soap(FundDTO fund, ACTION action) throws WssUpdateFundException {
		WssupdfdsImport req = new WssupdfdsImport();
		Date now = new Date();

		// Fund create or modification date
		ImpCreateModifyDatesFunds modify = new ImpCreateModifyDatesFunds();
		switch (action) {
		case CREATE:
			modify.setCreatedDate(DateFormatUtils.format(now, "yyyyMMdd"));
			modify.setCreatedTime(DateFormatUtils.format(now, "HHmmss"));
			break;
		case UPDATE:
			modify.setModifyDate(DateFormatUtils.format(now, "yyyyMMdd"));
			modify.setModifyTime(DateFormatUtils.format(now, "HHmmss"));
		}
		req.setImpCreateModifyDatesFunds(modify);

		// Fund update user
		ImpUseridUsers user = new ImpUseridUsers();
		user.setUsrId(securityContextUtils.getPreferredUsername());
		req.setImpUseridUsers(user);

		// Action
		ImpMethodCommunications method = new ImpMethodCommunications();
		method.setCallFunction(action.name());
		req.setImpMethodCommunications(method);

		// Log
		log.info("{} fund {} by user {}", action.name(), fund.getFdsId(), user.getUsrId());

		// Fund
		req.setImpFdsFunds(fundMapper.asFundDTO(fund));

		// Set credential to access the WS
		ImpValidationUsers value = new ImpValidationUsers();
		value.setLoginId(wsLoginCredential);
		value.setPassword(wsPasswordCredential);
		req.setImpValidationUsers(value);

		// Call web service client
		try {
			WssupdfdsExport resp = wssupdfds.wssupdfdscall(req);

			if (resp.getExpErrormessageBrowserFields() != null
					&& resp.getExpErrormessageBrowserFields().getErrorMessage() != null
					&& resp.getExpErrormessageBrowserFields().getErrorMessage().trim().length() != 0) {

				throw new WssUpdateFundException(resp.getExpErrormessageBrowserFields().getErrorTxt(),
						resp.getExpErrormessageBrowserFields().getErrorMessage());
			}

			// KAN 126-163: Impossible de saisir 0% dans Wealins Fees / Mettre à jours les champs FIn_ADVISOR_FEE, ASSET_MANAGER_FEE, BANK_DEPOSIT_FEE après l'appel du WS Lissia
			if (resp != null && resp.getExpFdsFunds() != null && !StringUtils.isEmpty(resp.getExpFdsFunds().getFdsId())) {
				FundEntity entity = getFundEntity(resp.getExpFdsFunds().getFdsId());

				if (entity != null) {
					entity.setBankDepositFee(fund.getBankDepositFee());
					entity.setAssetManagerFee(fund.getAssetManagerFee());
					entity.setFinAdvisorFee(fund.getFinAdvisorFee());
					entity.setFinFeesFlatAmount(fund.getFinFeesFlatAmount());
					entity.setDepositBankFlatFee(fund.getDepositBankFlatFee());
					entity.setFinFeesMinAmount(fund.getFinFeesMinAmount());
					entity.setFinFeesMaxAmount(fund.getFinFeesMaxAmount());
					if (historicManager.historize(entity)) {
						fundRepository.save(entity);
					}
				}
			}

			return resp;

		} catch (Exception_Exception e) {

			// Just wrap it to an interface exception
			throw new WssUpdateFundException(e);
		}

	}

	/**
	 * Setup the fund name. Fund name equals to Custodian Bank Id + SPACE + Accounting root + SPACE + Custodian Bank Name
	 * 
	 * @param fund The fund.
	 */
	private void setupName(FundDTO fund) {

		if (isTransmittedToSoliam(fund)) {
			return;
		}
		if (isFIDorFAS(fund)) {
			AgentDTO agentDTO = agentService.getAgent(fund.getDepositBank());

			if (agentDTO == null) {
				throw new IllegalStateException(CUSTODIAN_BANK_CANNOT_BE_NULL);
			}

			String name = replaceNull(agentDTO.getAgtId()) + SPACE + replaceNull(fund.getAccountRoot()) + SPACE
					+ replaceNull(agentDTO.getName());

			if (name.length() > FUND_NAME_COLUMN_SIZE) {
				name = name.substring(0, FUND_NAME_COLUMN_SIZE - 1);
			}

			fund.setName(name);
		} else {
			String name = fund.getName();
			if (name != null && name.length() > FUND_NAME_COLUMN_SIZE) {
				name = name.substring(0, FUND_NAME_COLUMN_SIZE - 1);
				fund.setName(name);
			}
		}
	}

	private boolean isTransmittedToSoliam(FundDTO fund) {
		final Integer exportedFund = fund.getExportedFund();

		return exportedFund != null && exportedFund.intValue() == 1;
	}

	/**
	 * Setup the fund documentation name. Accounting root + SPACE + Custodian Bank Name
	 * 
	 * @param fund The fund.
	 */
	private void setupDocumentationName(FundDTO fund) {
		if (isTransmittedToSoliam(fund)) {
			return;
		}
		if (isFIDorFAS(fund)) {
			AgentDTO agentDTO = agentService.getAgent(fund.getDepositBank());

			if (agentDTO == null) {
				throw new IllegalStateException(CUSTODIAN_BANK_CANNOT_BE_NULL);
			}

			String documentationName = replaceNull(fund.getAccountRoot()) + SPACE + replaceNull(agentDTO.getName());
			fund.setDocumentationName(documentationName.trim());
		}
	}

	private String replaceNull(String str) {
		if (str == null) {
			return EMPTY_STRING;
		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#search(java.lang. String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public SearchResult<FundLiteDTO> search(String filter, String type, String brokerId, boolean onlyBroker, int page, int size) {
		Pageable pagable = new PageRequest(page, size);

		Page<FundEntity> pageResult;
		if (FundSubType.FE.name().equals(type)) { // contextualization on FE search by broker (from a policy)
			pageResult = findByTypeAndNameAndBrokerId(filter, type, brokerId, onlyBroker, pagable);
		} else {
			pageResult = findByTypeAndNameAndBrokerId(filter, type, null, onlyBroker, pagable);
		}
		SearchResult<FundLiteDTO> r = new SearchResult<FundLiteDTO>();

		r.setPageSize(size);
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.hasContent() ? pageResult.getNumber() + 1 : 1);
		r.setContent(fundMapper.asFundLiteDTOs(pageResult.getContent()).stream().collect(Collectors.toList()));

		return r;
	}

	private Page<FundEntity> findByTypeAndNameAndBrokerId(String filter, String type, String brokerId, boolean onlyBroker, Pageable pageable) {
		Assert.notNull(type);

		Specifications<FundEntity> spec = Specifications.where(FundSpecifications.initial());
		spec = spec.and(FundSpecifications.withStatusNotEq(2))
				.and(FundSpecifications.withFundTypeIn(1, 2, 3))
				.and(FundSpecifications.withFundSubType(type));

		if (!StringUtils.isEmpty(filter)) {
			String likeFilter = org.apache.commons.lang3.StringUtils.wrap(filter.trim(), PERCENT_SYMBOLE);
			List<Specification<FundEntity>> specifications = new ArrayList<>();
			specifications.add(FundSpecifications.withIban(likeFilter));
			specifications.add(FundSpecifications.withRoot(likeFilter));
			specifications.add(FundSpecifications.withIsinCode(likeFilter));
			specifications.add(FundSpecifications.withCode(likeFilter));
			specifications.add(FundSpecifications.withName(likeFilter));
			spec = spec.and(FundSpecifications.or(specifications));
		}

		if (!StringUtils.isEmpty(brokerId)) {
			if (onlyBroker) {
				spec = spec.and(FundSpecifications.withBroker(brokerId));
			} else {
				List<Specification<FundEntity>> specifications = new ArrayList<>();
				specifications.add(FundSpecifications.withBroker(brokerId));
				specifications.add(FundSpecifications.withNoBroker());
				spec = spec.and(FundSpecifications.or(specifications));

			}
		}
		return fundRepository.findAll(orderBy(Arrays.asList("fdsId"), false, spec), pageable);
	}

	private Specification<FundEntity> orderBy(List<String> attributeNames, boolean desc, Specification<FundEntity> chainSpec) {
		return new Specification<FundEntity>() {

			@Override
			public Predicate toPredicate(Root<FundEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				orderBy(attributeNames, desc, root, query, cb);
				return chainSpec.toPredicate(root, query, cb);
			}

			private void orderBy(List<String> attributeNames, boolean desc, Root<FundEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (attributeNames != null) {
					List<Order> orders = new ArrayList<>();

					for (int i = 0; i < attributeNames.size(); i++) {
						Path<Object> atributePath = root.get(attributeNames.get(i));

						if (atributePath != null) {
							if (desc) {
								orders.add(cb.desc(atributePath));
							} else {
								orders.add(cb.asc(atributePath));
							}
						}
					}

					if (!orders.isEmpty()) {
						query.orderBy(orders);
					}
				}
			}
		};
	}

	@Override
	public SearchResult<FundLiteDTO> search(FundSearcherRequest fundSearcherRequest) {
		Specifications<FundEntity> specifs = Specifications.where(FundSpecifications.initial());
		boolean canUseStatus = false;

		if (StringUtils.isNotBlank(fundSearcherRequest.getFilter())) {
			specifs = specifs.and(FundSpecifications
					.or(Arrays.asList(FundSpecifications.withCode(fundSearcherRequest.getFilter().trim()),
							FundSpecifications.withName(fundSearcherRequest.getFilter().trim()))));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getAccountRoot())) {
			specifs = specifs.and(FundSpecifications.withRoot(fundSearcherRequest.getAccountRoot().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getAssetManager())) {
			specifs = specifs.and(FundSpecifications.withAssetManager(fundSearcherRequest.getAssetManager().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getDepositBank())) {
			specifs = specifs.and(FundSpecifications.withDepositBank(fundSearcherRequest.getDepositBank().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getFundSubType())) {
			specifs = specifs.and(FundSpecifications.withFundSubType(fundSearcherRequest.getFundSubType().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getIban())) {
			specifs = specifs.and(FundSpecifications.withIban(fundSearcherRequest.getIban().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getIsinCode())) {
			specifs = specifs.and(FundSpecifications.withIsinCode(fundSearcherRequest.getIsinCode().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getFinancialAdvisor())) {
			specifs = specifs.and(FundSpecifications.withFinancialAdvisor(fundSearcherRequest.getFinancialAdvisor().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getFdsId())) {
			specifs = specifs.and(FundSpecifications.withCode(fundSearcherRequest.getFdsId().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getName())) {
			specifs = specifs.and(FundSpecifications.withName(fundSearcherRequest.getName().trim()));
			canUseStatus = true;
		}
		if (StringUtils.isNotBlank(fundSearcherRequest.getIbanOrAccountRoot())) {
			specifs = specifs.and(FundSpecifications
					.or(Arrays.asList(FundSpecifications.withIban(fundSearcherRequest.getIbanOrAccountRoot().trim()),
							FundSpecifications.withRoot(fundSearcherRequest.getIbanOrAccountRoot().trim()))));
			canUseStatus = true;
		}

		if (canUseStatus && Integer.valueOf(1).equals(fundSearcherRequest.getStatus())) {
			specifs = specifs.and(FundSpecifications.withStatus(fundSearcherRequest.getStatus()));
		}

		if (BooleanUtils.isTrue(fundSearcherRequest.getExcludeTerminated())) { // exclude status 2 (keep only status 0 and 1)
			specifs = specifs.and(FundSpecifications.or(Arrays.asList(FundSpecifications.withStatus(1), FundSpecifications.withStatus(0))));
		}

		int pageNum = fundSearcherRequest.getPageNum() == null || fundSearcherRequest.getPageNum().intValue() < 1 ? 1
				: fundSearcherRequest.getPageNum().intValue();
		int pageSize = fundSearcherRequest.getPageSize() == null || fundSearcherRequest.getPageSize().intValue() <= 1
				|| fundSearcherRequest.getPageSize().intValue() > SearchResult.MAX_PAGE_SIZE
						? SearchResult.DEFAULT_PAGE_SIZE
						: fundSearcherRequest.getPageSize().intValue();

		Pageable pagable = new PageRequest(--pageNum, pageSize, Sort.Direction.DESC, FDS_ID);
		Page<FundEntity> pageResult = fundRepository.findAll(specifs, pagable);

		SearchResult<FundLiteDTO> r = new SearchResult<FundLiteDTO>();

		r.setPageSize(pageSize);
		r.setTotalPageCount(pageResult.getTotalPages());
		r.setTotalRecordCount(pageResult.getTotalElements());
		r.setCurrentPage(pageResult.hasContent() ? pageResult.getNumber() + 1 : 1);
		r.setContent(fundMapper.asFundLiteDTOs(pageResult.getContent()).stream().collect(Collectors.toList()));

		return r;
	}

	@Override
	public boolean isFIDorFAS(FundDTO fund) {
		return fund != null && (FundSubType.FID.name().equals(fund.getFundSubType())
				|| FundSubType.FAS.name().equals(fund.getFundSubType()));
	}

	@Override
	public boolean isFeOrFic(FundDTO fund) {
		return fund != null && FundSubType.FE.name().equals(fund.getFundSubType()) || FundSubType.FIC.name().equals(fund.getFundSubType());
	}

	@Override
	public boolean isFe(FundDTO fund) {
		return fund != null && fund.getFundSubType() != null
				&& FundSubType.FE.name().equals(fund.getFundSubType().trim());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#isFe(java.lang.String)
	 */
	@Override
	public boolean isFe(String fdsId) {
		FundEntity fund = getFundEntity(fdsId);

		return fund != null && fund.getFundSubType() != null
				&& FundSubType.FE.name().equals(fund.getFundSubType().trim());
	}

	@Override
	public boolean isFic(FundDTO fund) {
		return fund != null && FundSubType.FIC.name().equals(fund.getFundSubType());
	}

	@Override
	public boolean performFundValuation(String fundId, Date dateVNI, BigDecimal price, short priceType) {
		Assert.notNull(org.apache.commons.lang3.StringUtils.trimToNull(fundId), "Fund Id cannot be null");
		Assert.notNull(dateVNI, "Date of valorization cannot be null");

		try {
			WssstvlfImport req = new WssstvlfImport();

			ImpFundValuationFundPrices valuation = new ImpFundValuationFundPrices();
			valuation.setFund(fundId);
			valuation.setDate(DateFormatUtils.format(dateVNI, "yyyyMMdd"));
			valuation.setPriceType(priceType);

			if (Objects.nonNull(price)) {
				valuation.setPrice(price.toString());
			}

			req.setImpFundValuationFundPrices(valuation);
			org.tempuri.wssstvlf.WssstvlfImport.ImpValidationUsers value = new org.tempuri.wssstvlf.WssstvlfImport.ImpValidationUsers();
			value.setLoginId(wsLoginCredential);
			value.setPassword(wsPasswordCredential);
			req.setImpValidationUsers(value);

			wssstvlf.wssstvlfcall(req);
		} catch (org.tempuri.wssstvlf.Exception_Exception e) {
			throw new WebServiceInvocationException(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public boolean initValorization(String fundId, Date dateVNI) {
		// price type = 1 = Bid
		return performFundValuation(fundId, dateVNI, new BigDecimal(100d), (short) 1);
	}

	@Override
	public List<FundEntity> extractFidFund() {
		return fundRepository.findByFundSubType(Arrays.asList(Constantes.FID_FUND_SUB_TYPE));
	}

	@Override
	public Long updatetFidFundFlag(List<String> fids) throws Exception {
		Long recordsUpdated = new Long(0);
		WssexpfdsExport resp;
		for (String fid : fids) {
			resp = _internal_soap(fid);
			if (resp.getExpFdsFunds().getFdsId().equalsIgnoreCase(fid))
				++recordsUpdated;
		}
		logger.info("WSSEXPFDS has successfully updated exported fund flag {}", recordsUpdated);

		return recordsUpdated;
	}

	private WssexpfdsExport _internal_soap(String fid) throws Exception {
		WssexpfdsImport req = new WssexpfdsImport();

		// FdsFunds sets
		ImpFdsFunds fds = new ImpFdsFunds();
		fds.setFdsId(fid);
		fds.setExportedFund(FLAG_EXPORTED_FUND);
		req.setImpFdsFunds(fds);

		// Set credential to access the WS
		WssexpfdsImport.ImpValidationUsers value = new WssexpfdsImport.ImpValidationUsers();
		value.setLoginId(wsLoginCredential);
		value.setPassword(wsPasswordCredential);
		req.setImpValidationUsers(value);

		// Call web service
		try {
			WssexpfdsExport resp = wssexppfds.wssexpfdscall(req);

			if (resp.getExpErrormessageBrowserFields() != null
					&& resp.getExpErrormessageBrowserFields().getErrorMessage() != null
					&& resp.getExpErrormessageBrowserFields().getErrorMessage().trim().length() != 0) {

				throw new Exception(
						resp.getExpErrormessageBrowserFields().getErrorTxt() + " \n" +
								resp.getExpErrormessageBrowserFields().getErrorMessage());
			}
			return resp;

		} catch (org.tempuri.wssexpfds.Exception_Exception e) {
			logger.warn("Exception " + e);
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.FundService#isFas(lu.wealins.common.dto.liability.services.FundDTO)
	 */
	@Override
	public boolean isFas(FundDTO fund) {
		return fund != null && FundSubType.FAS.name().equals(fund.getFundSubType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFid(FundDTO fund) {
		return fund != null && FundSubType.FID.name().equals(fund.getFundSubType());
	}
	
	@Override
	public Boolean canAddFIDorFASValuationAmount(String fdsId, Date date, int priceType) {
		Boolean vniExistBeforeDate = fundPriceService.isExistVniBefore(fdsId, Arrays.asList(priceType), date);
		Boolean vniOfOneHundredExistForDate = fundPriceService.isExistVniOfOneHundred(fdsId, Arrays.asList(priceType), date);		
		return (!vniOfOneHundredExistForDate && vniExistBeforeDate);
	}

	/**
	 * Check if account root matches a pattern if necessary.
	 * 
	 * @param fund The fund with the account root to check
	 * @param warns The list of warnings if the match is KO
	 */
	private void checkAccountRoot(FundDTO fund, List<String> warns) {
		if (fund != null && !StringUtils.isEmpty(fund.getDepositBank()) && !StringUtils.isEmpty(fund.getAccountRoot())) {
			Collection<String> patterns = agentService.getAccountRootPattern(fund.getDepositBank());
			if (!patterns.isEmpty() && !patterns.stream().anyMatch(pattern -> Pattern.matches(pattern, fund.getAccountRoot()))) {
				Collection<String> examples = agentService.getAccountRootPatternExample(fund.getDepositBank());
				warns.add("Fund " + fund.getFdsId() + " - The account root doesn't match any specified pattern. (" + String.join(",", examples) + ").");
			}
		}
	}

	@Override
	public Collection<FundLiteDTO> getInvestedFunds(String polId) {
		Assert.notNull(polId);

		Object[] params = new Object[] { polId, new Date() };

		// Use the same query as the policy valuation service
		return jdbcTemplate.query(PolicyValuationHoldingServiceImpl.sqlValuation, params,
				new RowMapper<FundLiteDTO>() {

					@Override
					public FundLiteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						return getFundLite(rs.getString(3));
					}

				});
	}

}
