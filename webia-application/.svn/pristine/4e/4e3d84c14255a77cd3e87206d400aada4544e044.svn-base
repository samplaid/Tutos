package lu.wealins.webia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.enums.StepTypeDTO;
import lu.wealins.webia.core.service.FollowUpDocumentValidationService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;

@Service
public class FollowUpDocumentValidationServiceImpl implements FollowUpDocumentValidationService {

	@Autowired
	FollowUpDocumentContentHelper followUpHelper;

	@Override
	public boolean validateBeforeSentAcceptationContract(AppFormDTO appForm) {
		boolean senTMail = false;
		StepTypeDTO currentStep = StepTypeDTO.getStepType(getStep(appForm));

		if (!isBrokerWealins(appForm) && StepTypeDTO.REGISTRATION.compareTo(currentStep) == 0) {
			senTMail = true;
		}

		return senTMail;
	}

	@Override
	public boolean validateBeforeSentConfirmationFees(AppFormDTO appForm) {
		boolean senTMail = false;
		StepTypeDTO currentStep = StepTypeDTO.getStepType(getStep(appForm));
		if (!isBrokerWealins(appForm) && (StepTypeDTO.AWAITING_ACCOUNT_OPENING.compareTo(currentStep) == 0
				|| StepTypeDTO.ACCOUNT_OPENING_REQUEST.compareTo(currentStep) == 0
				|| StepTypeDTO.PREMIUM_TRANSFER_REQUEST.compareTo(currentStep) == 0)) {
			senTMail = true;
		}
		return senTMail;
	}

	@Override
	public boolean validateBeforeSentPrimeToBroker(AppFormDTO appForm) {
		boolean senTMail = true;
		if (validateBeforeSentPrime(appForm)) {
			if (isBrokerWealins(appForm)) {
				senTMail = false;
			}
		} else {
			senTMail = false;
		}
		return senTMail;
	}

	@Override
	public boolean validateBeforeSentPrime(AppFormDTO appForm) {
		boolean senTMail = true;
		StepTypeDTO currentStep = StepTypeDTO.getStepType(getStep(appForm));
		if (StepTypeDTO.GENERATE_DOCUMENTATION.compareTo(currentStep) != 0) {
			senTMail = false;
		}
		return senTMail;
	}

	protected String getStep(AppFormDTO appFormDTO) {
		return followUpHelper.getStep(appFormDTO);
	}



	@Override
	public boolean validateBeforeSent(AppFormDTO appForm) {
		boolean isAcceptationContract = validateBeforeSentAcceptationContract(appForm);
		boolean isConfirmationFees = validateBeforeSentConfirmationFees(appForm);
		boolean isPrime = validateBeforeSentPrime(appForm);
		return isAcceptationContract || isConfirmationFees || isPrime;
	}

	private boolean isBrokerWealins(AppFormDTO appFormDTO) {
		return followUpHelper.isBrokerWealins(appFormDTO);
	}
}
