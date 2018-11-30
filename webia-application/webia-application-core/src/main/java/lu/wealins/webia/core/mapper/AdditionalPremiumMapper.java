package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpControls;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpControls.Row.ImpItmControlProductValues;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpPas;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpPas.Row.ImpItmPrlProductLines;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpPfd;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpPocPolicyCoverages;
import lu.wealins.common.dto.liability.services.AdditionalPremiumDTO.ImpValidationUsers;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;
import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.common.dto.liability.services.enums.TransferType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.LiabilityProductLineService;
import lu.wealins.webia.core.service.LiabilityProductValueService;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.core.utils.PrimitiveShortToBoolean;
import lu.wealins.webia.ws.rest.impl.exception.PolicyCreationException;

@Mapper(componentModel = "spring", uses = { PrimitiveShortToBoolean.class, PolicyTransferMapper.class })
public abstract class AdditionalPremiumMapper {

	private static final String NO_PRODUCT_LINE_HAS_BEEN_FOUND = "No product line has been found.";

	@Autowired
	private LiabilityProductLineService liabilityProductLineService;

	@Autowired
	private LiabilityProductValueService productValueService;

	@Mappings({
			@Mapping(source = "policyId", target = "impPolPolicies.polId"),
			@Mapping(target = "impPcpPolicyPremiums.modalPremium", expression = "java(in.getPaymentAmt().setScale(2).toPlainString())"),
	})
	public abstract AdditionalPremiumDTO asAdditionalPremiumDTO(AppFormDTO in);

	@Mappings({
			@Mapping(source = "fundId", target = "fund"),
			@Mapping(source = "split", target = "percentage")
	})
	public abstract ImpItmPfdPolicyFundDirections asImpItmPfdPolicyFundDirections(FundFormDTO in);



	@AfterMapping
	public AdditionalPremiumDTO afterEntityMapping(AppFormDTO in, @MappingTarget AdditionalPremiumDTO target) {

		ProductLineDTO productLine = findMatchingProductLine(in);
		if (productLine == null) {
			throw new PolicyCreationException(NO_PRODUCT_LINE_HAS_BEEN_FOUND);
		}

		setupValidationUsers(target);
		setupFunds(in, target);
		setupPolicyTransfers(in, target);
		setupImpPocPolicyCoverages(in, target, productLine);
		setupImpGrpPas(in, target, productLine);
		setupImpGrpControls(in, target, productLine);

		return target;
	}

	private void setupImpGrpControls(AppFormDTO in, AdditionalPremiumDTO target, ProductLineDTO productLine) {
		ImpGrpControls impGrpControls = new ImpGrpControls();
		List<AdditionalPremiumDTO.ImpGrpControls.Row> rows = impGrpControls.getRows();

		if (in.getEntryFeesAmt() != null) {
			addControlProductValues(rows, ControlDefinitionType.POLICY_FEE.getValue(), in.getEntryFeesAmt(), productLine);
		} else {
			addControlProductValues(rows, ControlDefinitionType.POLICY_FEE.getValue(), in.getEntryFeesPct(), productLine);
		}

		// We put the same C12RAT and C13RAT used on the first coverage.
		ProductValueDTO contractManagementFees = productValueService.getContractManagementFees(in.getPolicyId());
		if (contractManagementFees != null) {
			addControlProductValues(rows, ControlDefinitionType.CONTRACT_MANAGEMENT_FEE.getValue(), contractManagementFees.getNumericValue(), productLine);
		}

		ProductValueDTO c13RatFees = productValueService.getC13RatFees(in.getPolicyId());
		if (c13RatFees != null) {
			addControlProductValues(rows, ControlDefinitionType.C13RAT.getValue(), c13RatFees.getNumericValue(), productLine);
		}

		target.setImpGrpControls(impGrpControls);
	}

	private void addControlProductValues(List<AdditionalPremiumDTO.ImpGrpControls.Row> rows, String control, BigDecimal value, ProductLineDTO productLine) {
		Optional<ProductValueDTO> productValue = productLine.getProductValues().stream().filter(pl -> pl.getControl().equals(control)).findFirst();
		// check if it's the default product line value or a new custom value
		if (!productValue.isPresent() || productValue.get().getNumericValue().compareTo(value) != 0) {
			AdditionalPremiumDTO.ImpGrpControls.Row row = new AdditionalPremiumDTO.ImpGrpControls.Row();
			ImpItmControlProductValues impItmControlProductValues = new ImpItmControlProductValues();
			impItmControlProductValues.setControl(control);
			impItmControlProductValues.setNumericValue(value != null ? value.toPlainString() : "0");
			impItmControlProductValues.setProductLine(productLine.getPrlId());
			row.setImpItmControlProductValues(impItmControlProductValues);
			rows.add(row);
		}
	}

	private void setupPolicyTransfers(AppFormDTO in, AdditionalPremiumDTO target) {
		Collection<PolicyTransferDTO> policyTransfers = target.getPolicyTransfers();
		Integer one = Integer.valueOf(1);

		// same behavior as ImpItmPocPolicyCoverages --> coverage == 1
		policyTransfers.forEach(x -> x.setCoverage(one));
		policyTransfers.forEach(x -> x.setFkPoliciespolId(in.getPolicyId()));
		policyTransfers.forEach(x -> x.setPolicy(in.getPolicyId()));

		if (BooleanUtils.isTrue(in.getPolicyTransfer())) {
			policyTransfers.forEach(x -> x.setTransferType(TransferType.TRANSFER));
		}

		if (BooleanUtils.isTrue(in.getPaymentTransfer())) {
			policyTransfers.forEach(x -> x.setTransferType(TransferType.RE_INVEST));
		}
	}

	private ProductLineDTO findMatchingProductLine(AppFormDTO in) {
		return liabilityProductLineService.findMatchingAdditionalProductLine(in);
	}

	private void setupFunds(AppFormDTO in, AdditionalPremiumDTO target) {
		ImpGrpPfd impGrpPfd = new ImpGrpPfd();
		for (FundFormDTO fund : in.getFundForms()) {
			AdditionalPremiumDTO.ImpGrpPfd.Row row = new AdditionalPremiumDTO.ImpGrpPfd.Row();
			row.setImpItmPfdPolicyFundDirections(asImpItmPfdPolicyFundDirections(fund));
			impGrpPfd.getRows().add(row);
		}
		target.setImpGrpPfd(impGrpPfd);
	}

	private void setupValidationUsers(AdditionalPremiumDTO target) {
		ImpValidationUsers user = new ImpValidationUsers();
		user.setLoginId(null);
		user.setPassword(null);
		target.setImpValidationUsers(user);
	}

	private void setupImpPocPolicyCoverages(AppFormDTO in, AdditionalPremiumDTO target, ProductLineDTO productLine) {
		ImpPocPolicyCoverages impPocPolicyCoverages = new ImpPocPolicyCoverages();

		Date dateOfEffect = (in.getPaymentDt() != null) ? in.getPaymentDt() : Calendar.getInstance().getTime();
		String policyEffectiveDate = new SimpleDateFormat(Constantes.YYYYMMDD).format(dateOfEffect);
		impPocPolicyCoverages.setDateOfApplication(policyEffectiveDate);
		impPocPolicyCoverages.setDateCommenced(policyEffectiveDate);
		impPocPolicyCoverages.setDateOfReqCommencement(policyEffectiveDate);

		impPocPolicyCoverages.setProductLine(productLine.getPrlId());
		target.setImpPocPolicyCoverages(impPocPolicyCoverages);

	}

	private void setupImpGrpPas(AppFormDTO in, AdditionalPremiumDTO target, ProductLineDTO productLine) {
		ImpGrpPas impGrpPas = new ImpGrpPas();
		ImpItmPrlProductLines impItmPrlProductLines = new ImpItmPrlProductLines();

		impItmPrlProductLines.setPrlId(productLine.getPrlId());

		List<AdditionalPremiumDTO.ImpGrpPas.Row> rows = impGrpPas.getRows();
		PartnerFormDTO broker = in.getBroker();
		if (broker != null) {
			addPolicyAgentShareRow(rows, broker, PolicyAgentShareType.INITIAL_COMM_FEE, broker.getEntryFeesPct(), broker.getEntryFeesAmt(), impItmPrlProductLines);
		}

		target.setImpGrpPas(impGrpPas);
	}

	private void addPolicyAgentShareRow(List<AdditionalPremiumDTO.ImpGrpPas.Row> rows, PartnerFormDTO agent, PolicyAgentShareType policyAgentShareType, BigDecimal percentage, BigDecimal amount,
			ImpItmPrlProductLines productLines) {
		if (agent != null && agent.getPartnerId() != null && policyAgentShareType != null) {
			AdditionalPremiumDTO.ImpGrpPas.Row row = new AdditionalPremiumDTO.ImpGrpPas.Row();
			row.setImpItmPrlProductLines(productLines);
			ImpItmPasPolicyAgentShares policyAgentShares = new ImpItmPasPolicyAgentShares();
			policyAgentShares.setAgent(agent.getPartnerId());
			policyAgentShares.setPartnerAuthorized((short) (BooleanUtils.isNotTrue(agent.getPartnerAuthorized()) ? 0 : 1));
			if (amount != null) {
				policyAgentShares.setSpecificIce(amount.toPlainString());
			} else {
				policyAgentShares.setPercentage(percentage != null ? percentage.toPlainString() : "0");
			}
			policyAgentShares.setType((short) policyAgentShareType.getType());

			boolean isBroker = AgentCategory.BROKER.getCategory().equalsIgnoreCase(agent.getPartnerCategory());
			policyAgentShares.setPrimaryAgent((short) BooleanUtils.toInteger(isBroker));

			row.setImpItmPasPolicyAgentShares(policyAgentShares);
			rows.add(row);
		}
	}

}
