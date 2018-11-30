package lu.wealins.common.dto.webia.services.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.Assert;

public enum StepTypeDTO {

	// The order is very important !!! It is used in the 'isAfter', 'isAfterOrEquals' method
	REGISTRATION("Registration"), WAITING_DISPATCH("Waiting Dispatch"), NEW("New"), ANALYSIS("Analysis"), UNBLOCKED_ANALYSIS("Unblocked Analysis"), SCANNING("Scanning"), CHECK_REGISTRATION(
			"Check Registration"), VALIDATE_ANALYSIS("Validate Analysis"), AWAITING_CASH_TRANSFER(
					"Awaiting Cash/Transfer"), VALIDATE_BANK_INSTRUCTIONS("Validate Bank Instructions"), UNBLOCKED_VALIDATE_ANALYSIS("Unblocked Validate Analysis"), VALIDATE_ANALYSIS_AND_INPUT(
					"Validate Analysis and Input"), VALIDATE_BROKER_CHANGE("Validate Broker Change"), MEDICAL_ACCEPTANCE("Medical Acceptance"), CHECK_PORTFOLIO("Check Portfolio"), ACCEPTANCE(
							"Acceptance"), UNBLOCKED_ACCEPTANCE("Unblocked Acceptance"), ACCEPTANCE_BIS(
											"Acceptance Bis"), AWAITING_CASH_TRANSFER_FEES("Awaiting Cash/Transfer/Fees"), REQUEST_TO_CLIENT_PARTNER(
													"Request to Client/Partner"), COMPLETE_ANALYSIS("Complete Analysis"), UNBLOCKED_REQUEST_TO_CLIENT_PARTNER(
											"Unblocked Request to Client/Partner"), ACCOUNT_OPENING_REQUEST("Account Opening Request"), AWAITING_ACCOUNT_OPENING(
													"Awaiting Account Opening"), PREMIUM_TRANSFER_REQUEST("Premium Transfer Request"), GENERATE_MANAGEMENT_MANDATE(
																			"Generate Management Mandate"), VALIDATE_INPUT("Validate Input"), AWAITING_CASH_WEALINS_ACCOUNT_NAV_AND_DATE(
																					"Awaiting Cash Wealins Account/NAV and date"), AWAITING_PREMIUM("Awaiting Premium"), GENERATE_MANDAT_DE_GESTION(
																	"Generate Mandat de Gestion"), ACCEPTATION_PREMIUM("Acceptation Premium"), PREMIUM_INPUT_AND_NAV(
																			"Premium Input and NAV"), VALIDATE_PREMIUM_AND_NAV("Validate Premium and NAV"), VALIDATE_ADDITIONAL_PREMIUM(
																							"Validate Additional Premium"), AWAITING_PUT_IN_FORCE("Awaiting Put in Force"), AWAITING_VALUATION(
																									"Awaiting Valuation"), AWAITING_ACTIVATION(
																							"Awaiting Activation"), REVIEW_DOCUMENTATION(
																									"Review Documentation"), GENERATE_DOCUMENTATION(
																																							"Generate Documentation"), CHECK_DOCUMENTATION_AND_PAYMENT(
																																									"Check Documentation and Payment"), CHECK_DOCUMENTATION(
																													"Check Documentation"), UPDATE_INPUT(
																															"Update Input"), REGULARISATION_CONDITIONAL_APPROVAL(
																																	"Regularisation Conditional approval"), VALIDATE_DOCUMENTATION(
																																			"Validate Documentation"), SENDING(
																																					"Sending"), FOLLOW_UP(
																																							"Follow-up"), AWAITING_ACTIVATION_FEES(
																																									"Awaiting Activation Fees");

	public static Set<StepTypeDTO> CPS1_GROUP = Stream.of(NEW, UNBLOCKED_ANALYSIS, ANALYSIS, MEDICAL_ACCEPTANCE, UNBLOCKED_REQUEST_TO_CLIENT_PARTNER, REQUEST_TO_CLIENT_PARTNER, ACCOUNT_OPENING_REQUEST, AWAITING_ACCOUNT_OPENING, PREMIUM_TRANSFER_REQUEST,
					GENERATE_MANDAT_DE_GESTION, AWAITING_PREMIUM, PREMIUM_INPUT_AND_NAV, CHECK_DOCUMENTATION, REGULARISATION_CONDITIONAL_APPROVAL, SENDING)
			.collect(Collectors.toSet());
	public static Set<StepTypeDTO> ADDITIONAL_PREMIUM_CPS1_GROUP = Stream.of(ANALYSIS, REQUEST_TO_CLIENT_PARTNER, ACCOUNT_OPENING_REQUEST, AWAITING_ACCOUNT_OPENING, PREMIUM_TRANSFER_REQUEST,
			GENERATE_MANAGEMENT_MANDATE, AWAITING_PREMIUM, PREMIUM_INPUT_AND_NAV, CHECK_DOCUMENTATION, SENDING)
			.collect(Collectors.toSet());
	public static Set<StepTypeDTO> CPS2_GROUP = Stream
			.of(VALIDATE_ANALYSIS, VALIDATE_ANALYSIS_AND_INPUT, VALIDATE_INPUT, VALIDATE_PREMIUM_AND_NAV, REVIEW_DOCUMENTATION, VALIDATE_DOCUMENTATION)
			.collect(Collectors.toSet());

	public static Set<StepTypeDTO> WITHDRAWAL_CPS1_GROUP = Stream.of(NEW, ANALYSIS, REQUEST_TO_CLIENT_PARTNER, COMPLETE_ANALYSIS, AWAITING_CASH_TRANSFER, CHECK_DOCUMENTATION, SENDING)
			.collect(Collectors.toSet());

	public static Set<StepTypeDTO> WITHDRAWAL_CPS2_GROUP = Stream.of(VALIDATE_ANALYSIS, VALIDATE_INPUT, REVIEW_DOCUMENTATION, VALIDATE_DOCUMENTATION).collect(Collectors.toSet());

	public static Set<StepTypeDTO> SURRENDER_CPS1_GROUP = Stream
			.of(NEW, ANALYSIS, REQUEST_TO_CLIENT_PARTNER, AWAITING_CASH_TRANSFER_FEES, AWAITING_CASH_WEALINS_ACCOUNT_NAV_AND_DATE, CHECK_DOCUMENTATION_AND_PAYMENT, COMPLETE_ANALYSIS, SENDING)
			.collect(Collectors.toSet());

	public static Set<StepTypeDTO> SURRENDER_CPS2_GROUP = Stream.of(VALIDATE_ANALYSIS, VALIDATE_INPUT, VALIDATE_BANK_INSTRUCTIONS, REVIEW_DOCUMENTATION, VALIDATE_DOCUMENTATION)
			.collect(Collectors.toSet());

	private static final String STEP_TYPE_CANNOT_BE_NULL = "Step type cannot be null.";
	private String value;

	private StepTypeDTO(String value) {
		this.value = value;
	}

	public String getvalue() {
		return value;
	}

	public boolean isAfter(StepTypeDTO stepType) {
		Assert.notNull(stepType);

		return ordinal() > stepType.ordinal();
	}

	public boolean isAfterOrEquals(StepTypeDTO stepType) {
		Assert.notNull(stepType);

		return ordinal() >= stepType.ordinal();
	}

	public static StepTypeDTO getStepType(String stepType) {
		Assert.notNull(stepType, STEP_TYPE_CANNOT_BE_NULL);
		for (StepTypeDTO s : values()) {
			if(s.getvalue().equals(stepType)) {
				return s;
			}
		}
		
		throw new IllegalArgumentException("Unknow step type " + stepType + ".");
	}

	public static StepTypeDTO getByOrdinal(int ordinal) {
		for (StepTypeDTO s : values()) {
			if (s.ordinal() == ordinal) {
				return s;
			}
		}

		throw new IllegalArgumentException("Step not found having ordinal " + ordinal + ".");
	}

}
