package lu.wealins.webia.core.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.DeathCoverageClauseDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpCallMethodCommunications;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpControls;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCpr;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCvg;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPas;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPbc;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPfd;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpPonGeneralNotes;
import lu.wealins.common.dto.liability.services.NewBusinessDTO.ImpValidationUsers;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;
import lu.wealins.common.dto.liability.services.ProductLineDTO;
import lu.wealins.common.dto.liability.services.ProductValueDTO;
import lu.wealins.common.dto.liability.services.enums.AgentCategory;
import lu.wealins.common.dto.liability.services.enums.CliPolRelationshipType;
import lu.wealins.common.dto.liability.services.enums.ControlDefinitionType;
import lu.wealins.common.dto.liability.services.enums.PolicyAgentShareType;
import lu.wealins.common.dto.liability.services.enums.TransferType;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.common.dto.webia.services.InsuredFormDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;
import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.core.service.LiabilityDeathCoverageService;
import lu.wealins.webia.core.service.LiabilityProductLineService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.utils.Constantes;
import lu.wealins.webia.core.utils.PrimitiveShortToBoolean;
import lu.wealins.webia.ws.rest.impl.exception.PolicyCreationException;

@Mapper(componentModel = "spring", uses = { PrimitiveShortToBoolean.class, PolicyTransferMapper.class })
public abstract class NewBusinessMapper {

	private static final String NO_PRODUCT_LINE_HAS_BEEN_FOUND = "No product line has been found.";
	private static final String NO_ASSURED_HAS_BEEN_FOUND = "No assured client has been assigned to the police";

	@Autowired
	private LiabilityProductLineService liabilityProductLineService;
	@Autowired
	private LiabilityDeathCoverageService liabilityDeathCoverageService;
	@Autowired
	private LiabilityProductService liabilityProductService;
	@Autowired
	private BenefClauseFormMapper benefClauseFormMapper;

	private static final String CREATE = "CREATE";
	private static final String MULTIPLIER = "MULTIPLIER";

	@Mappings({
			@Mapping(source = "policyId", target = "impPolicyPolicies.polId"),
			@Mapping(source = "productCd", target = "impPolicyPolicies.product"),
			@Mapping(source = "contractCurrency", target = "impPolicyPolicies.currency"),
			@Mapping(source = "orderByFax", target = "impPolicyPolicies.orderByFax"),
			@Mapping(source = "nonSurrenderClauseDt", target = "impPolicyPolicies.nonSurrenderClauseDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "expectedPremium", target = "impPolicyPolicies.expectedPremium"),
			@Mapping(source = "mailToAgent", target = "impPolicyPolicies.mailToAgent"),
			@Mapping(source = "score", target = "impPolicyPolicies.scoreNewBusiness"),
			@Mapping(source = "scudo", target = "impPolicyPolicies.scudo"),
			@Mapping(source = "mandatoAllincasso", target = "impPolicyPolicies.mandatoAllIncasso"),
			@Mapping(source = "brokerRefContract", target = "impPolicyPolicies.brokerRefContract"),
			@Mapping(source = "noCoolOff", target = "impPolicyPolicies.noCoolOff"),
			@Mapping(source = "sendingRules", target = "impPolicyPolicies.category"),
			@Mapping(source = "countryCd", target = "impPolicyPolicies.issueCountryOfResidence"),
			@Mapping(target = "impPolicyPolicies.dateOfApplication", expression = "java(in.getPaymentDt() != null ? new SimpleDateFormat(lu.wealins.webia.core.utils.Constantes.YYYYMMDD).format(in.getPaymentDt()) : null)"),
			@Mapping(target = "impPolicyPolicies.dateOfCommencement", expression = "java(in.getPaymentDt() != null ? new SimpleDateFormat(lu.wealins.webia.core.utils.Constantes.YYYYMMDD).format(in.getPaymentDt()) : null)"),
			@Mapping(source = "applicationForm", target = "impPolicyPolicies.additionalId"),
			@Mapping(source = "mandate", target = "impPolicyPolicies.exMandate"),
			@Mapping(source = "paymentTransfer", target = "impPolicyPolicies.exPaymentTransfer"),
			@Mapping(source = "policyTransferForms", target = "policyTransfers"),
			@Mapping(source = "broker.entryFeesPct", target = "entryFeesPct"),
			@Mapping(source = "broker.mngtFeesPct", target = "mngtFeesPct")
	})
	public abstract NewBusinessDTO asNewBusinessDTO(AppFormDTO in);

	@Mappings({
			@Mapping(source = "fundId", target = "fund"),
			@Mapping(source = "split", target = "percentage")
	})
	public abstract ImpItmPfdPolicyFundDirections asImpItmPfdPolicyFundDirections(FundFormDTO in);

	@Mappings({
			@Mapping(source = "clientId", target = "client"),
			// @Mapping(target = "type", expression = "java(in.getClientRelationTp() != null ? (short)in.getClientRelationTp() : (short)0)"),
			@Mapping(source = "clientRelationTp", target = "type"),
			@Mapping(source = "split", target = "percentageSplit"),

	})
	public abstract ImpItmCprCliPolRelationships asImpItmCprCliPolRelationships(ClientFormDTO in);

	@Mappings({
			@Mapping(source = "clientId", target = "client")
	})
	public abstract ImpItmPclAssured1PolicyCvgLives asImpItmPclAssured1PolicyCvgLives(ClientFormDTO in);

	@Mappings({
			@Mapping(source = "clientId", target = "client")
	})
	public abstract ImpItmPclAssured2PolicyCvgLives asImpItmPclAssured2PolicyCvgLives(ClientFormDTO in);

	@Mappings({
			@Mapping(target = "modalPremium", expression = "java(in.getPaymentAmt() != null ? in.getPaymentAmt().setScale(2).toPlainString() : in.getExpectedPremium()!=null ? in.getExpectedPremium().setScale(2).toPlainString() : \"0\")"),
			@Mapping(source = "contractCurrency", target = "currency"),
			@Mapping(target = "frequency", constant = "1"),
	})
	public abstract ImpItmPcpPolicyPremiums asImpItmPcpPolicyPremiums(AppFormDTO in);

	@Mappings({
			@Mapping(source = "contractCurrency", target = "currency"),
			@Mapping(target = "initialSumAssured", constant = "0"),
			@Mapping(target = "coverage", constant = "1"),
			@Mapping(target = "soldBasis", constant = "2"),
			@Mapping(target = "multiplier", expression = "java(in.getMultiplier()!=null ? in.getMultiplier().toPlainString() : \"0\")"),
			@Mapping(source = "lives", target = "livesType"),
			@Mapping(source = "term", target = "term"),
			// @Mapping(target = "dateOfApplication", expression = "java(new java.util.Date())", dateFormat = "yyyyMMdd"),
			// @Mapping(target = "dateOfReqCommencement", expression = "java(in.getPaymentDt() != null ? new SimpleDateFormat() in.getPaymentDt() :new java.util.Date())", dateFormat = "yyyyMMdd"),
			// @Mapping(target = "dateCommenced", expression = "java(new java.util.Date())", dateFormat = "yyyyMMdd"),
	})
	public abstract ImpItmPocPolicyCoverages asImpItmPocPolicyCoverages(AppFormDTO in);

	public abstract ImpPonGeneralNotes asImpPonGeneralNotes(AppFormDTO in);

	@AfterMapping
	public NewBusinessDTO afterEntityMapping(AppFormDTO in, @MappingTarget NewBusinessDTO target) {

		ProductLineDTO productLine = findMatchingProductLine(in);
		if (productLine == null) {
			throw new PolicyCreationException(NO_PRODUCT_LINE_HAS_BEEN_FOUND);
		}
		DeathCoverageClauseDTO deathCoverageClause = liabilityDeathCoverageService.getDeathCoverageClause(in.getProductCd(), in.getDeathCoverageTp() + "", in.getDeathCoverageStd());

		if (deathCoverageClause != null && MULTIPLIER.equals(deathCoverageClause.getControlType())) {
			in.setMultiplier(in.getDeathCoveragePct());
		}

		setupValidationUsers(target);
		setupFunds(in, target);
		setupClients(in, target);
		setupUpdateCallFunction(target);
		setupBenefClauses(in, target);
		setupImpGrpCvg(in, target, productLine);
		setupImpGrpPas(in, target, productLine);
		setupImpGrpControls(in, target, productLine, deathCoverageClause);
		setupPolicyTransfers(in, target);

		return target;
	}

	private void setupPolicyTransfers(AppFormDTO in, NewBusinessDTO target) {
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
		return liabilityProductLineService.findMatchingProductLine(in);
	}

	private void setupImpGrpControls(AppFormDTO in, NewBusinessDTO target, ProductLineDTO productLine, DeathCoverageClauseDTO deathCoverageClause) {
		ImpGrpControls impGrpControls = new ImpGrpControls();
		List<NewBusinessDTO.ImpGrpControls.Row> rows = impGrpControls.getRows();

		if (in.getEntryFeesAmt() != null) {
			addControlProductValues(rows, ControlDefinitionType.POLICY_FEE.getValue(), in.getEntryFeesAmt(), productLine);
		} else {
			addControlProductValues(rows, ControlDefinitionType.POLICY_FEE.getValue(), in.getEntryFeesPct(), productLine);
		}
		addControlProductValues(rows, ControlDefinitionType.CONTRACT_MANAGEMENT_FEE.getValue(), in.getMngtFeesPct(), productLine);
		addControlProductValues(rows, ControlDefinitionType.WITHDRAWAL_FEE.getValue(), in.getSurrenderFees(), productLine);

		if (deathCoverageClause != null && deathCoverageClause.getControlType() != null && !deathCoverageClause.getControlType().equals(MULTIPLIER)) {
			if (in.getDeathCoverageAmt() != null) {
				addControlProductValues(rows, deathCoverageClause.getControlType(), in.getDeathCoverageAmt(), productLine);
			} else {
				addControlProductValues(rows, deathCoverageClause.getControlType(),
						liabilityDeathCoverageService.percentToCoverageValue(in.getDeathCoveragePct(), deathCoverageClause.getControlType()), productLine);
			}
		}

		target.setImpGrpControls(impGrpControls);
	}

	private void addControlProductValues(List<NewBusinessDTO.ImpGrpControls.Row> rows, String control, BigDecimal value, ProductLineDTO productLine) {
		Optional<ProductValueDTO> productValue = productLine.getProductValues().stream().filter(pl -> pl.getControl().equals(control)).findFirst();
		// check if it's the default product line value or a new custom value
		if (!productValue.isPresent() || productValue.get().getNumericValue().compareTo(value) != 0) {
			NewBusinessDTO.ImpGrpControls.Row row = new NewBusinessDTO.ImpGrpControls.Row();
			ImpItmControlProductValues impItmControlProductValues = new ImpItmControlProductValues();
			impItmControlProductValues.setControl(control);
			impItmControlProductValues.setNumericValue(value != null ? value.toPlainString() : "0");
			impItmControlProductValues.setProductLine(productLine.getPrlId());
			row.setImpItmControlProductValues(impItmControlProductValues);
			rows.add(row);
		}
	}

	private void setupImpGrpPas(AppFormDTO in, NewBusinessDTO target, ProductLineDTO productLine) {
		ImpGrpPas impGrpPas = new ImpGrpPas();
		ImpItmPrlProductLines impItmPrlProductLines = new ImpItmPrlProductLines();

		impItmPrlProductLines.setPrlId(productLine.getPrlId());

		List<NewBusinessDTO.ImpGrpPas.Row> rows = impGrpPas.getRows();
		PartnerFormDTO broker = in.getBroker();
		addPolicyAgentShareRow(rows, broker, PolicyAgentShareType.INITIAL_COMM_FEE, broker.getEntryFeesPct(), broker.getEntryFeesAmt(), impItmPrlProductLines);
		addPolicyAgentShareRow(rows, broker, PolicyAgentShareType.ADVISOR_FEES, broker.getMngtFeesPct(), null, impItmPrlProductLines);

		PartnerFormDTO subBroker = in.getSubBroker();
		if (subBroker != null) {
			addPolicyAgentShareRow(rows, subBroker, PolicyAgentShareType.INITIAL_COMM_FEE, subBroker.getEntryFeesPct(), subBroker.getEntryFeesAmt(), impItmPrlProductLines);
			addPolicyAgentShareRow(rows, subBroker, PolicyAgentShareType.ADVISOR_FEES, subBroker.getMngtFeesPct(), null, impItmPrlProductLines);
		}

		PartnerFormDTO brokerContact = in.getBrokerContact();
		if (brokerContact != null) {
			addPolicyAgentShareRow(rows, brokerContact, PolicyAgentShareType.INITIAL_COMM_FEE, brokerContact.getEntryFeesPct(), brokerContact.getEntryFeesAmt(), impItmPrlProductLines);
			addPolicyAgentShareRow(rows, brokerContact, PolicyAgentShareType.SALES_REPRESENTATIVE, brokerContact.getMngtFeesPct(), null, impItmPrlProductLines);
		}

		PartnerFormDTO businessIntroducer = in.getBusinessIntroducer();
		if (businessIntroducer != null) {
			addPolicyAgentShareRow(rows, businessIntroducer, PolicyAgentShareType.ADDL_ADVISOR_FEE, businessIntroducer.getEntryFeesPct(), null, impItmPrlProductLines);
		}

		Collection<PartnerFormDTO> countryManagers = in.getCountryManagers();
		countryManagers.forEach(countryManager -> {
			addPolicyAgentShareRow(rows, countryManager, PolicyAgentShareType.FISA_SALES_PERSON, countryManager.getSplit(), null, impItmPrlProductLines);
		});

		target.setImpGrpPas(impGrpPas);
	}

	private void addPolicyAgentShareRow(List<NewBusinessDTO.ImpGrpPas.Row> rows, PartnerFormDTO agent, PolicyAgentShareType policyAgentShareType, BigDecimal percentage, BigDecimal amount,
			ImpItmPrlProductLines productLines) {
		if (agent != null && agent.getPartnerId() != null && policyAgentShareType != null) {
			NewBusinessDTO.ImpGrpPas.Row row = new NewBusinessDTO.ImpGrpPas.Row();
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

	private boolean isCapiProduct(AppFormDTO in) {	
		return liabilityProductService.isCapiProduct(in.getProductCd());
	}

	private void setupImpGrpCvg(AppFormDTO in, NewBusinessDTO target, ProductLineDTO productLine) {
		ImpGrpCvg impGrpCvg = new ImpGrpCvg();
		NewBusinessDTO.ImpGrpCvg.Row row = new NewBusinessDTO.ImpGrpCvg.Row();

		ClientFormDTO firstLifeAssured = in.getInsureds().stream().filter(x -> ClientRelationType.LIFE_ASSURED.getValue().equals(x.getClientRelationTp())).findFirst().orElse(null);
		ClientFormDTO secondLifeAssured = in.getInsureds().stream().filter(x -> ClientRelationType.JNT_LIFE_ASSURED.getValue().equals(x.getClientRelationTp())).findFirst().orElse(null);

		if (firstLifeAssured == null) {
			if (isCapiProduct(in)) {
				firstLifeAssured = createFakeClient();
			} else {
				throw new IllegalStateException("No life assured defined on the coverage.");
			}

		}

		ImpItmPclAssured1PolicyCvgLives impItmPclAssured1PolicyCvgLives = asImpItmPclAssured1PolicyCvgLives(firstLifeAssured);
		row.setImpItmPclAssured1PolicyCvgLives(impItmPclAssured1PolicyCvgLives);
		if (secondLifeAssured != null) {
			ImpItmPclAssured2PolicyCvgLives impItmPclAssured2PolicyCvgLives = asImpItmPclAssured2PolicyCvgLives(secondLifeAssured);
			row.setImpItmPclAssured2PolicyCvgLives(impItmPclAssured2PolicyCvgLives);
		}
		ImpItmPcpPolicyPremiums impItmPcpPolicyPremiums = asImpItmPcpPolicyPremiums(in);
		row.setImpItmPcpPolicyPremiums(impItmPcpPolicyPremiums);
		ImpItmPocPolicyCoverages impItmPocPolicyCoverages = asImpItmPocPolicyCoverages(in);

		Date dateOfEffect = (in.getPaymentDt() != null) ? in.getPaymentDt() : Calendar.getInstance().getTime();
		String policyEffectiveDate = new SimpleDateFormat(Constantes.YYYYMMDD).format(dateOfEffect);
		impItmPocPolicyCoverages.setDateOfApplication(policyEffectiveDate);
		impItmPocPolicyCoverages.setDateCommenced(policyEffectiveDate);
		impItmPocPolicyCoverages.setDateOfReqCommencement(policyEffectiveDate);

		impItmPocPolicyCoverages.setProductLine(productLine.getPrlId());
		row.setImpItmPocPolicyCoverages(impItmPocPolicyCoverages);
		impGrpCvg.getRows().add(row);
		target.setImpGrpCvg(impGrpCvg);
	}

	private ClientFormDTO createFakeClient() {
		ClientFormDTO firstLifeAssured;
		firstLifeAssured = new ClientFormDTO();
		firstLifeAssured.setClientId(Constantes.FAKE_CLIENT_ID);
		firstLifeAssured.setRankNumber(1);
		firstLifeAssured.setClientRelationTp(CliPolRelationshipType.LIFE_ASSURED.getValue());
		return firstLifeAssured;
	}

	private void setupUpdateCallFunction(NewBusinessDTO target) {
		ImpCallMethodCommunications impCallMethodCommunications = new ImpCallMethodCommunications();
		impCallMethodCommunications.setCallFunction(CREATE);
		target.setImpCallMethodCommunications(impCallMethodCommunications);
	}

	private void setupInsured(NewBusinessDTO target, ClientFormDTO insured) {
		Collection<NewBusinessDTO.ImpGrpCpr.Row> rows = new ArrayList<>();
		ImpGrpCpr impGrpCpr = target.getImpGrpCpr();

		if (impGrpCpr == null) {
			impGrpCpr = new ImpGrpCpr();
		}

		int rank = insured.getRankNumber().intValue();
		CliPolRelationshipType cliPolRelationshipType = rank == 1 ? CliPolRelationshipType.LIFE_ASSURED
				: rank == 2 ? CliPolRelationshipType.JNT_LIFE_ASSURED : CliPolRelationshipType.ADDN_LIFE_ASSURED;

		rows.add(createCliPolRelationshipRow(insured, cliPolRelationshipType, rank));
		impGrpCpr.getRows().addAll(rows);
		target.setImpGrpCpr(impGrpCpr);
	}

	private void setupOtherClient(NewBusinessDTO target, ClientFormDTO client, int rank) {
		Collection<NewBusinessDTO.ImpGrpCpr.Row> rows = new ArrayList<>();
		ImpGrpCpr impGrpCpr = target.getImpGrpCpr();
		if (impGrpCpr == null) {
			impGrpCpr = new ImpGrpCpr();
		}
		rows.add(createCliPolRelationshipRow(client, null, rank));
		impGrpCpr.getRows().addAll(rows);
		target.setImpGrpCpr(impGrpCpr);
	}

	private void setupBeneficiary(NewBusinessDTO target, BeneficiaryFormDTO beneficiary, CliPolRelationshipType cliPolRelationshipType) {
		Collection<NewBusinessDTO.ImpGrpCpr.Row> rows = new ArrayList<>();
		ImpGrpCpr impGrpCpr = target.getImpGrpCpr();
		if (impGrpCpr == null) {
			impGrpCpr = new ImpGrpCpr();
		}

		int rank = beneficiary.getRankNumber() == null ? 1 : beneficiary.getRankNumber().intValue();
		NewBusinessDTO.ImpGrpCpr.Row row = createCliPolRelationshipRow(beneficiary, cliPolRelationshipType, rank);

		if (row.getImpItmCprCliPolRelationships() != null) {
			row.getImpItmCprCliPolRelationships().setExEqualParts(BooleanUtils.toString(beneficiary.getIsEqualParts(), "1", "0", null));
		}

		rows.add(row);

		if (BooleanUtils.isTrue(beneficiary.getUsufructuary())) {
			rows.add(createCliPolRelationshipRow(beneficiary, CliPolRelationshipType.BENEFICIARY_USUFRUCTUARY, rank));
		}
		if (BooleanUtils.isTrue(beneficiary.getBareOwner())) {
			rows.add(createCliPolRelationshipRow(beneficiary, CliPolRelationshipType.BENEFICIARY_BARE_OWNER, rank));
		}

		if (BooleanUtils.isTrue(beneficiary.getIrrevocable())) {
			rows.add(createCliPolRelationshipRow(beneficiary, CliPolRelationshipType.IRREVOCABLE_BEN, rank));
		}
		if (BooleanUtils.isTrue(beneficiary.getSeparatePropertyNoRights())) {
			rows.add(createCliPolRelationshipRow(beneficiary, CliPolRelationshipType.SEPARATE_PROPERTY_NO_RIGHTS, rank));
		}
		if (BooleanUtils.isTrue(beneficiary.getSeparatePropertyRights())) {
			rows.add(createCliPolRelationshipRow(beneficiary, CliPolRelationshipType.SEPARATE_PROPERTY_RIGHTS, rank));
		}
		if (BooleanUtils.isTrue(beneficiary.getAcceptant())) {
			rows.add(createCliPolRelationshipRow(beneficiary, CliPolRelationshipType.ACCEPTANT, rank));
		}
		impGrpCpr.getRows().addAll(rows);
		target.setImpGrpCpr(impGrpCpr);
	}

	private void setupPolicyHolder(NewBusinessDTO target, PolicyHolderFormDTO policyHolder) {
		Collection<NewBusinessDTO.ImpGrpCpr.Row> rows = new ArrayList<>();
		ImpGrpCpr impGrpCpr = target.getImpGrpCpr();
		if (impGrpCpr == null) {
			impGrpCpr = new ImpGrpCpr();
		}
		int rank = policyHolder.getRankNumber().intValue();
		CliPolRelationshipType cliPolRelationshipType = rank == 1 ? CliPolRelationshipType.OWNER : rank == 2 ? CliPolRelationshipType.JOINT_OWNER : CliPolRelationshipType.ADDN_OWNER;
		rows.add(createCliPolRelationshipRow(policyHolder, cliPolRelationshipType, rank));

		if (BooleanUtils.isTrue(policyHolder.getBareOwner())) {
			rows.add(createCliPolRelationshipRow(policyHolder, CliPolRelationshipType.BARE_OWNER, rank));
		}
		if (BooleanUtils.isTrue(policyHolder.getUsufructuary())) {
			rows.add(createCliPolRelationshipRow(policyHolder, CliPolRelationshipType.USUFRUCTUARY, rank));
		}
		if (BooleanUtils.isTrue(policyHolder.getDeathSuccessor())) {
			rows.add(createCliPolRelationshipRow(policyHolder, CliPolRelationshipType.SUCCESSION_DEATH, rank));
		}
		if (BooleanUtils.isTrue(policyHolder.getLifeSuccessor())) {
			rows.add(createCliPolRelationshipRow(policyHolder, CliPolRelationshipType.SUCCESSION_LIFE, rank));
		}
		impGrpCpr.getRows().addAll(rows);
		target.setImpGrpCpr(impGrpCpr);
	}

	private NewBusinessDTO.ImpGrpCpr.Row createCliPolRelationshipRow(ClientFormDTO clientForm, CliPolRelationshipType cliPolRelationshipType, int rank) {
		NewBusinessDTO.ImpGrpCpr.Row row = new NewBusinessDTO.ImpGrpCpr.Row();
		ImpItmCprCliPolRelationships impItmCprCliPolRelationships = asImpItmCprCliPolRelationships(clientForm);
		impItmCprCliPolRelationships.setTypeNumber((short) rank); // correspond to the rank

		if (cliPolRelationshipType != null) {
			impItmCprCliPolRelationships.setType((short) cliPolRelationshipType.getValue());
		}

		if (clientForm.getSplit() != null) {
			impItmCprCliPolRelationships.setPercentageSplit(clientForm.getSplit().setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString());
		}

		row.setImpItmCprCliPolRelationships(impItmCprCliPolRelationships);
		return row;
	}

	private void setupBenefClause(BenefClauseFormDTO benefClause, NewBusinessDTO target, String policy) {
		ImpItmPbcPolicyBeneficiaryClauses impItmPbcPolicyBeneficiaryClauses = benefClauseFormMapper.asImpItmPbcPolicyBeneficiaryClauses(benefClause);
		impItmPbcPolicyBeneficiaryClauses.setPolicy(policy);
		ImpGrpPbc impGrpPbc = target.getImpGrpPbc();
		if (impGrpPbc == null) {
			impGrpPbc = new ImpGrpPbc();
		}
		NewBusinessDTO.ImpGrpPbc.Row row = new NewBusinessDTO.ImpGrpPbc.Row();
		row.setImpItmPbcPolicyBeneficiaryClauses(impItmPbcPolicyBeneficiaryClauses);
		impGrpPbc.getRows().add(row);
		target.setImpGrpPbc(impGrpPbc);
	}

	private void setupBenefClauses(AppFormDTO in, NewBusinessDTO target) {
		in.getLifeBenefClauseForms().forEach(x -> setupBenefClause(x, target, in.getPolicyId()));
		in.getDeathBenefClauseForms().forEach(x -> setupBenefClause(x, target, in.getPolicyId()));
	}

	private void setupClients(AppFormDTO in, NewBusinessDTO target) {
		setupPolicyHolders(in, target);
		setupInsureds(in, target);
		setupBeneficiaries(in, target);
		setupOtherClients(in, target);
	}

	private void setupPolicyHolders(AppFormDTO in, NewBusinessDTO target) {
		in.getPolicyHolders().forEach(x -> setupPolicyHolder(target, x));
	}

	private void setupInsureds(AppFormDTO in, NewBusinessDTO target) {
		Collection<InsuredFormDTO> insureds = in.getInsureds();
		if (CollectionUtils.isNotEmpty(insureds)) {
			insureds.forEach(x -> setupInsured(target, x));
		} else {
			if (isCapiProduct(in)) {
				setupInsured(target, createFakeClient());
			} else {
				throw new PolicyCreationException(NO_ASSURED_HAS_BEEN_FOUND);
			}
		}
	}

	private void setupBeneficiaries(AppFormDTO in, NewBusinessDTO target) {
		in.getLifeBeneficiaries().forEach(beneficiary -> setupBeneficiary(target, beneficiary, CliPolRelationshipType.BENEFICIARY_AT_MATURITY));
		in.getDeathBeneficiaries().forEach(beneficiary -> setupBeneficiary(target, beneficiary, CliPolRelationshipType.BENEFICIARY_AT_DEATH));
	}

	private void setupOtherClients(AppFormDTO in, NewBusinessDTO target) {
		int i = 0;
		for (Iterator<ClientFormDTO> iterator = in.getOtherClients().iterator(); iterator.hasNext();) {
			ClientFormDTO insured = iterator.next();
			setupOtherClient(target, insured, i);
			i++;
		}
	}

	private void setupFunds(AppFormDTO in, NewBusinessDTO target) {
		ImpGrpPfd impGrpPfd = new ImpGrpPfd();
		for (FundFormDTO fund : in.getFundForms()) {
			NewBusinessDTO.ImpGrpPfd.Row row = new NewBusinessDTO.ImpGrpPfd.Row();
			row.setImpItmPfdPolicyFundDirections(asImpItmPfdPolicyFundDirections(fund));
			impGrpPfd.getRows().add(row);
		}
		target.setImpGrpPfd(impGrpPfd);
	}

	private void setupValidationUsers(NewBusinessDTO target) {
		ImpValidationUsers user = new ImpValidationUsers();
		user.setLoginId(null);
		user.setPassword(null);
		target.setImpValidationUsers(user);
	}

}
