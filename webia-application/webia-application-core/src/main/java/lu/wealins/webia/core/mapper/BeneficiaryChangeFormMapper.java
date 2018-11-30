package lu.wealins.webia.core.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.BeneficiaryChangeDTO;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryChangeFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;
import lu.wealins.common.dto.webia.services.ClientFormDTO;
import lu.wealins.common.dto.webia.services.PolicyHolderFormDTO;
import lu.wealins.common.dto.liability.services.enums.PolicyBeneficiaryClauseStatus;

@Mapper(componentModel = "spring", uses = { BenefClauseFormMapper.class, BeneficiaryMapper.class, ClientFormMapper.class, PolicyHolderMapper.class })
public abstract class BeneficiaryChangeFormMapper {

	@Autowired
	private BeneficiaryMapper beneficiaryMapper;
	@Autowired
	private PolicyHolderMapper policyHolderMapper;
	@Autowired
	private BenefClauseFormMapper benefClauseFormMapper;
	@Autowired
	private ClientFormMapper clientFormMapper;

	@Mappings({
			@Mapping(source = "lifeBenefClauses", target = "lifeBenefClauseForms"),
			@Mapping(source = "deathBenefClauses", target = "deathBenefClauseForms")
	})
	public abstract BeneficiaryChangeFormDTO asBeneficiaryChangeFormDTO(BeneficiaryChangeDTO in);

	@Mappings({
			@Mapping(source = "lifeBenefClauseForms", target = "lifeBenefClauses"),
			@Mapping(source = "deathBenefClauseForms", target = "deathBenefClauses")
	})
	public abstract BeneficiaryChangeDTO asBeneficiaryChangeDTO(BeneficiaryChangeFormDTO in);

	@AfterMapping
	public BeneficiaryChangeDTO asAppFormDTOAfterMapping(BeneficiaryChangeFormDTO in, @MappingTarget BeneficiaryChangeDTO out) {
		out.getDeathBenefClauses().forEach(x -> x.setStatus(Integer.valueOf(PolicyBeneficiaryClauseStatus.INACTIVE.getValue())));
		out.getLifeBenefClauses().forEach(x -> x.setStatus(Integer.valueOf(PolicyBeneficiaryClauseStatus.INACTIVE.getValue())));
		return out;
	}

	public BeneficiaryChangeFormDTO asBeneficiaryChangeFormDTO(BeneficiaryChangeDTO in, @MappingTarget BeneficiaryChangeFormDTO out) {

		Collection<BeneficiaryFormDTO> deathBeneficiaries = new ArrayList<>();
		in.getDeathBeneficiaries().forEach(x -> deathBeneficiaries.add(beneficiaryMapper.asBeneficiaryFormDTO(x)));
		out.setDeathBeneficiaries(deathBeneficiaries);

		Collection<BeneficiaryFormDTO> lifeBeneficiaries = new ArrayList<>();
		in.getLifeBeneficiaries().forEach(x -> lifeBeneficiaries.add(beneficiaryMapper.asBeneficiaryFormDTO(x)));
		out.setLifeBeneficiaries(lifeBeneficiaries);

		Collection<PolicyHolderFormDTO> policyHolders = new ArrayList<>();
		in.getPolicyHolders().forEach(x -> policyHolders.add(policyHolderMapper.asPolicyHolderFormDTO(x)));
		out.setPolicyHolders(policyHolders);

		Collection<ClientFormDTO> otherClients = new ArrayList<>();
		in.getOtherClients().forEach(x -> otherClients.add(clientFormMapper.asClientFormDTO(x)));
		out.setOtherClients(otherClients);

		Collection<BenefClauseFormDTO> lifeBenefClauses = new ArrayList<>();
		in.getLifeBenefClauses().forEach(x -> lifeBenefClauses.add(benefClauseFormMapper.asBenefClauseFormDTO(x)));
		out.setLifeBenefClauseForms(lifeBenefClauses);

		Collection<BenefClauseFormDTO> deathBenefClauses = new ArrayList<>();
		in.getDeathBenefClauses().forEach(x -> deathBenefClauses.add(benefClauseFormMapper.asBenefClauseFormDTO(x)));
		out.setDeathBenefClauseForms(deathBenefClauses);


		return out;
	}

}
