package lu.wealins.liability.services.core.mapper.factory;

import org.mapstruct.Mapper;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;

@Mapper(componentModel = "spring")
public class ClientFactory {

	public PolicyHolderDTO createPolicyHolderDTO() {
		return new PolicyHolderDTO();
	}

	public ClientDTO createClientDTO() {
		return new ClientDTO();
	}

	public ClientLiteDTO createClientLiteDTO() {
		return new ClientLiteDTO();
	}

	public InsuredDTO createInsuredDTO() {
		return new InsuredDTO();
	}

}
