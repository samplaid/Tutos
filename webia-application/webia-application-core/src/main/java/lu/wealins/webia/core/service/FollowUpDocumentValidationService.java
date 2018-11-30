package lu.wealins.webia.core.service;

import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface FollowUpDocumentValidationService {


	boolean validateBeforeSentAcceptationContract(AppFormDTO appForm);

	boolean validateBeforeSentConfirmationFees(AppFormDTO appForm);

	boolean validateBeforeSentPrimeToBroker(AppFormDTO appForm);

	boolean validateBeforeSentPrime(AppFormDTO appForm);

	boolean validateBeforeSent(AppFormDTO appForm);

}
