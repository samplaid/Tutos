package lu.wealins.webia.core.service.validations.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.FundLiteDTO;
import lu.wealins.common.dto.webia.services.PartnerFormDTO;
import lu.wealins.webia.core.service.validations.FundValidationService;

@Service
public class FundValidationServiceImpl implements FundValidationService {

	@Override
	public void validateExternalFunds(Collection<FundLiteDTO> funds, PartnerFormDTO broker, List<String> errors) {
		if (!CollectionUtils.isEmpty(funds)) {
			funds.forEach(fund -> {
				if (fund.getFundSubType().equals("FE")) {
					if (StringUtils.isNotBlank(fund.getBroker()) && broker != null && !fund.getBroker().equals(broker.getPartnerId())) {
						errors.add("The FE " + fund.getName() + " has a different broker than the broker attached to the policy. A switch is necessary");
					}
				}

			});
		}
	}

}
