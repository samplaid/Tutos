package lu.wealins.webia.core.service;

import java.util.Collection;

import lu.wealins.common.dto.webia.services.AppFormDTO;

public interface AppFormPolicyDataService {

	public Collection<AppFormDTO> getAppFormByPolicy(String policyId);
}
