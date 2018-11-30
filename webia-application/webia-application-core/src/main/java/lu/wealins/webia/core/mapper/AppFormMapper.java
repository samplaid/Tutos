package lu.wealins.webia.core.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lu.wealins.common.dto.liability.services.PolicyDTO;
import lu.wealins.common.dto.liability.services.PolicyTransferDTO;
import lu.wealins.common.dto.liability.services.TransferType;
import lu.wealins.common.dto.webia.services.AppFormDTO;@Mapper(componentModel = "spring", uses = { ClientFormMapper.class, PolicyHolderMapper.class, PolicyTransferMapper.class })
public abstract class AppFormMapper extends AbstractAppFormMapper {

	@AfterMapping
	protected AppFormDTO asAppFormDTOAfterMapping(PolicyDTO policy, @MappingTarget AppFormDTO appForm) {

		super.afterMapping(policy, appForm);
		inferTransferFlag(appForm, policy);

		return appForm;
	}

	private void inferTransferFlag(AppFormDTO appForm, PolicyDTO policy) {

		// int coverage = policy.getFirstPolicyCoverages().getTerm().intValue();
		int coverage = 1;

		Collection<PolicyTransferDTO> policyTransfers = policy.getPolicyTransfers().stream().filter(x -> x.getCoverage().intValue() == coverage).collect(Collectors.toList());

		if (!policy.getPolicyTransfers().isEmpty()) {
			if (policyTransfers.stream().allMatch(pt -> TransferType.TRANSFER.equals(pt.getTransferType()))) {
				appForm.setPolicyTransfer(Boolean.TRUE);
			} else {
				appForm.setPolicyTransfer(Boolean.FALSE);
			}

			if (policyTransfers.stream().allMatch(pt -> TransferType.RE_INVEST.equals(pt.getTransferType()))) {
				appForm.setPaymentTransfer(Boolean.TRUE);
			} else {
				appForm.setPaymentTransfer(Boolean.FALSE);
			}
		} else {
			appForm.setPolicyTransfer(Boolean.FALSE);
			appForm.setPaymentTransfer(Boolean.FALSE);
		}
	}
}
