package lu.wealins.liability.services.ws.rest.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.liability.services.core.business.PriceInjectionService;
import lu.wealins.liability.services.core.business.exceptions.FundPriceException;
import lu.wealins.liability.services.core.business.exceptions.FundPriceExceptionCode;
import lu.wealins.liability.services.core.persistence.entity.FundEntity;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.ws.rest.PriceInjectionRESTService;
import lu.wealins.common.dto.liability.services.BatchInjectionControlResponse;
import lu.wealins.common.dto.liability.services.ErrorDTO;
import lu.wealins.common.dto.liability.services.PriceInjectionControlRequest;
import lu.wealins.common.dto.liability.services.enums.FundSubType;

@Component
public class PriceInjectionRESTServiceImpl implements PriceInjectionRESTService {

	/**
	 * The logger
	 */
	private static final Logger log = LoggerFactory.getLogger(PriceInjectionRESTServiceImpl.class);

	private static final String SUFFIX = "Z";

	private static final String COMA = ",";

	private static final String PREFIX = "FPR";

	private static final String EMPTY = "";

	private final Integer PRICE_BASIS = new Integer(4);

	@Autowired
	private PriceInjectionService priceInjectionService;

	@Autowired
	private CalendarUtils calendarUtils;

	public BatchInjectionControlResponse checkPriceInjection(SecurityContext context, PriceInjectionControlRequest request) {
		BatchInjectionControlResponse response = new BatchInjectionControlResponse();
		response.setSuccess(Boolean.TRUE);

		if (request != null) {
			try {
				List<FundEntity> funds = priceInjectionService.loadFundsByIsinAndCurrency(request.getIsinCode(), request.getCurrency());
				log.info("{} funds found for the isin {}", funds.size(), request.getIsinCode());
				for (FundEntity fundEntity : funds) {
					String fundId = fundEntity.getFdsId();
					String currency = request.getCurrency();
					BigDecimal price = request.getPrice();
					Date priceDate = calendarUtils.createDate(request.getPriceDate());
					String currentLine = buildLine(priceDate, price, fundEntity);

					try {
						priceInjectionService.checkDuplicatePrice(fundId, priceDate);
						priceInjectionService.checkCurrency(fundId, currency);
						priceInjectionService.checkPriceNotNull(price);
						priceInjectionService.checkPricingDay(fundId, priceDate);
						priceInjectionService.checkPriceVariation(fundId, priceDate, request.getPrice());
						priceInjectionService.checkPriceDateVariation(fundId, priceDate);
						priceInjectionService.checkPendingTransactions(fundId, priceDate);
						priceInjectionService.checkHoliday(priceDate);
						// If everything is right, we can add the line
						response.getLines().add(currentLine);

					} catch (FundPriceException e) {
						ErrorDTO error = new ErrorDTO();

						FundPriceExceptionCode errorCode = e.getErrorCode();
						if (errorCode != null) {
							error.setCode(e.getErrorCode().name());
						}
						error.setMessage(
								" Fund " + fundId + ", Price " + price + ", Currency " + currency + ", Price date " + request.getPriceDate() + ", (isin code " + request.getIsinCode() + ") : "
										+ e.getMessage());

						response.getErrors().add(error);
						response.setSuccess(Boolean.FALSE);
					}

				}
			} catch (FundPriceException e) {
				ErrorDTO error = new ErrorDTO();

				FundPriceExceptionCode errorCode = e.getErrorCode();
				if (errorCode != null) {
					error.setCode(e.getErrorCode().name());
				}
				error.setMessage(e.getMessage());

				response.getErrors().add(error);
				response.setSuccess(Boolean.FALSE);
			}
		}

		return response;
	}

	/**
	 * Build line for the price injection
	 * 
	 * Example of line : FPR,,14.06.2017,CB2,EUR,10.970000,,,,,1,,,,,18061,Z or for
	 * FE when price_basis equal 4
	 * FPR,,14.06.2017,CB2,EUR,,,,,,1,,10.970000,10.970000*VNIentry,10.970000*VNIexit,18061,Z
	 * 
	 * @param priceDate
	 * @param price
	 * @param fundEntity
	 * @return
	 */
	private String buildLine(Date priceDate, BigDecimal price, FundEntity fundEntity) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		int initialDate = 24837;
		java.sql.Date sqlDate = new java.sql.Date(priceDate.getTime());
		int priceDateInt = (int) sqlDate.getTime();

		String middleData;

		if (isFE(fundEntity) && isPriceBasisEqualFour(fundEntity)) {
			middleData = String.join(COMA, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, String.valueOf(1), EMPTY,
					buildMiddleLineForFEFund(price, fundEntity));
		} else {
			middleData = String.join(COMA, price.toString(), EMPTY, EMPTY, EMPTY, EMPTY, String.valueOf(1), EMPTY,
					EMPTY, EMPTY, EMPTY);
		}

		return String.join(COMA, PREFIX, EMPTY, format.format(priceDate), fundEntity.getFdsId().trim(),
				fundEntity.getCurrency().trim(), middleData, String.valueOf(priceDateInt - initialDate), SUFFIX);
	}

	/**
	 * Verified if fund is FE or not.
	 * @param fundEntity
	 * @return true if the fund is FE or False otherwise
	 */
	private Boolean isFE(FundEntity fundEntity) {
		return fundEntity != null && fundEntity.getFundSubType() != null
				&& FundSubType.FE.name().equals(fundEntity.getFundSubType().trim());
	}

	/**
	 * Verified if fund Price basis is equal to four.
	 * @param fundEntity
	 * @return true if fund Price basis is equal to four or False otherwise
	 */
	private Boolean isPriceBasisEqualFour(FundEntity fundEntity) {
		return fundEntity != null && fundEntity.getPriceBasis() != null
				&& fundEntity.getPriceBasis().compareTo(PRICE_BASIS) == 0;
	}

	/**
	 * Calculate price with VNI.
	 * @param price
	 * @param fee
	 * @return price of VNI
	 */
	private String calculateVNI(BigDecimal price, BigDecimal fee) {
		if (price != null && fee != null) {
			BigDecimal vniPrice = price.multiply(fee).setScale(2, RoundingMode.DOWN);
			return vniPrice.toPlainString();
		}
		return EMPTY;
	}
	
	/**
	 * Build csv line for FE fund.
	 * @param price
	 * @param fundEntity
	 * @return CSV middle line
	 */
	private String buildMiddleLineForFEFund(BigDecimal price, FundEntity fundEntity) {
		String entryPriceVNI = calculateVNI(price, fundEntity.getNavEntryFee());
		String exitPriceVNI = calculateVNI(price, fundEntity.getNavExitFee());

		return String.join(COMA, price.toPlainString(), entryPriceVNI, exitPriceVNI);
	}
}
