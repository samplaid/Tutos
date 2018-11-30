package lu.wealins.liability.services.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.tempuri.wssupers.WssupersImport.ImpCliClients;

import lu.wealins.liability.services.core.business.ClientNameService;
import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.mapper.factory.ClientFactory;
import lu.wealins.liability.services.core.persistence.entity.ClientContactDetailEntity;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.liability.services.core.persistence.entity.ClientLinkedPersonEntity;
import lu.wealins.liability.services.core.persistence.entity.GeneralNoteEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLiteDTO;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.common.dto.liability.services.account.ClientAccountDTO;
import lu.wealins.common.dto.liability.services.enums.ClientLinkedPersonType;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class, ClientClaimsDetailMapper.class, ClientFactory.class })
public abstract class ClientMapper {

	private static final String FALSE = "0";
	private static final String TRUE = "1";

	@Autowired
	private ClientNameService clientNameService;
	@Autowired
	private ClientContactDetailsMapper clientContactDetailsMapper;
	@Autowired
	private GeneralNoteMapper generalNoteMapper;
	@Autowired
	private ClientLinkedPersonMapper clientLinkedPersonMapper;
	@Autowired
	private ClientAccountMapper clientAccountMapper;
	@Autowired
	private ClientService clientService;
	
	@Mappings({
		@Mapping(target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
	})
	@BeanMapping(resultType = ClientLiteDTO.class)
	public abstract ClientLiteDTO asClientLiteDTO(ClientEntity in);

	public abstract void asClientLiteDTO(ClientEntity in, @MappingTarget ClientLiteDTO target);

	@AfterMapping
	protected void afterMapping(ClientEntity in, @MappingTarget ClientLiteDTO out) {
		out.setDead(new Boolean(clientService.isDead(in)));
	}

	@Mappings({
			@Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyyMMdd"),
			@Mapping(source = "idExpiryDate", target = "idExpiryDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "dateOfRevision", target = "dateOfRevision", dateFormat = "yyyyMMdd"),
			@Mapping(source = "dateOfSelfCert", target = "dateOfSelfCert", dateFormat = "yyyyMMdd"),
			@Mapping(source = "crsDateOfLastDec", target = "crsDateOfLastDec", dateFormat = "yyyyMMdd"),
			@Mapping(source = "healthDecDate", target = "healthDecDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "kycDate", target = "kycDate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "gdprStartdate", target = "gdprStartdate", dateFormat = "yyyyMMdd"),
			@Mapping(source = "gdprEnddate", target = "gdprEnddate", dateFormat = "yyyyMMdd")
	})
	public abstract ImpCliClients asImpCliClients(ClientDTO in);

	public List<ClientLiteDTO> asClientLiteDTOs(List<ClientEntity> in) {
		List<ClientLiteDTO> clients = new ArrayList<>();

		in.forEach(x -> clients.add(asClientLiteDTO(x)));

		return clients;
	}

	@BeanMapping(resultType = ClientDTO.class)
	public abstract ClientDTO asClientDTO(ClientEntity in);

	public abstract void asClientDTO(ClientEntity in, @MappingTarget ClientDTO target);

	@BeanMapping(resultType = PolicyHolderDTO.class)
	public abstract PolicyHolderDTO asPolicyHolderDTO(ClientEntity in);

	@BeanMapping(resultType = InsuredDTO.class)
	public abstract InsuredDTO asInsuredDTO(ClientEntity in);

	@AfterMapping
	protected void afterImpCliClientsMap(ClientDTO in, @MappingTarget ImpCliClients target) {
		target.setExceptRisk(BooleanUtils.toString(BooleanUtils.toBoolean(in.getExceptRisk()), TRUE, FALSE));
		target.setExceptActivityRisk(BooleanUtils.toString(BooleanUtils.toBoolean(in.getExceptActivityRisk()), TRUE, FALSE));
	}

	@SuppressWarnings("boxing")
	@AfterMapping
	protected <T extends ClientDTO> T clientLinkedPersons(ClientEntity in, @MappingTarget T target) {
		List<ClientLinkedPersonEntity> personsRepresentingCompany = in.getClientLinkedPersons().stream()
				.filter(x -> x.getStatus() != null && x.getStatus() == 1 && x.getType() != null && ClientLinkedPersonType.DIRECTOR.name().equalsIgnoreCase(x.getType().trim()))
				.collect(Collectors.toList());
		target.setPersonsRepresentingCompany(clientLinkedPersonMapper.asClientLinkedPersonDTOs(personsRepresentingCompany));

		List<ClientLinkedPersonEntity> controlingPersons = in.getClientLinkedPersons().stream()
				.filter(x -> x.getStatus() != null && x.getStatus() == 1 && x.getType() != null && ClientLinkedPersonType.CTRL.name().equalsIgnoreCase(x.getType().trim()))
				.collect(Collectors.toList());
		target.setControllingPersons(clientLinkedPersonMapper.asClientLinkedPersonDTOs(controlingPersons));

		return target;
	}
	
	@AfterMapping
	protected <T extends ClientDTO> T bankAccounts(ClientEntity in, @MappingTarget T target) {
		
		Collection<ClientAccountDTO> clientAccounts = clientAccountMapper.asClientAccountsDto(in.getBankAccounts());
		target.setClientAccounts(clientAccounts);

		return target;
	}

	@AfterMapping
	protected ClientLiteDTO clientAddress(ClientEntity in, @MappingTarget ClientLiteDTO target) {
		
		if (in.getClientContactDetails() == null || in.getClientContactDetails().isEmpty()) {
			return target;
		}
		
		for (ClientContactDetailEntity e : in.getClientContactDetails()) {
			// fetch the right contact detail entry.
			if (e.getContactType().trim().equalsIgnoreCase(in.getCorrespContactType().trim())
					&& e.getStatus() == 1) {
				
				target.setAddressLine1((e.getAddressLine1()!=null)? e.getAddressLine1().trim():"");
				target.setAddressLine2((e.getAddressLine2()!=null)? e.getAddressLine2().trim():"");
				target.setAddressLine3((e.getAddressLine3()!=null)? e.getAddressLine3().trim():"");
				target.setAddressLine4((e.getAddressLine4()!=null)? e.getAddressLine4().trim():"");			
				target.setTown( (e.getTown()!=null) ? e.getTown().trim() : "");
				target.setPostCode( (e.getTown()!=null) ?  e.getPostcode().trim(): "");
				target.setCountry( (e.getCountry()!=null && e.getCountry().getCtyId()!=null) ? e.getCountry().getCtyId().trim() : "");

				break;
			}
		}
		
		return target;
	}
	
	@AfterMapping
	protected ClientLiteDTO displayName(ClientEntity in, @MappingTarget ClientLiteDTO target) {
		
		// normalize the fund name to display
		target.setDisplayName(clientNameService.generate(in));
		
		return target;
	}

	@AfterMapping
	protected void setDeath(ClientEntity in, @MappingTarget ClientLiteDTO target) {
		target.setDead(new Boolean(clientService.isDead(in)));
	}

	/*
	 * Post processing methods of clientDTO.
	 * 	
	 * 	- home address
	 *  - correspondence address
	 *  - id number
	 *  - client note
	 *  - display name  
	 * 
	 */
	@AfterMapping
	protected <T extends ClientDTO> T addresses(ClientEntity in, @MappingTarget T target) {
		
		if (in.getClientContactDetails() == null || in.getClientContactDetails().isEmpty()) {
			return target;
		}
		
		for (ClientContactDetailEntity e : in.getClientContactDetails()) {
			
			if (e.getContactType().trim().equalsIgnoreCase("CORRESP") && e.getStatus() == 1) {
				target.setHomeAddress(clientContactDetailsMapper.asClientContactDetailDTO(e));
				
			} else if (e.getContactType().trim().equalsIgnoreCase("CORRESP1") && e.getStatus() == 1) {
				target.setCorrespondenceAddress(clientContactDetailsMapper.asClientContactDetailDTO(e));
			}
		}
		return target;
	}

	@AfterMapping
	protected <T extends ClientDTO> T notes(ClientEntity in, @MappingTarget T target) {
		
		if (in.getGeneralNotes() == null || in.getGeneralNotes().isEmpty()) {
			return target;
		}
		
		for (GeneralNoteEntity e : in.getGeneralNotes()) {
			
			if (e.getType() != null && e.getType() == 2) {
				target.setIdNumber(generalNoteMapper.asGeneralNoteDTO(e));
				
			} else if (e.getType() != null && e.getType() == 1) {
				target.setClientNote(generalNoteMapper.asGeneralNoteDTO(e));
			}
		}
		return target;
	}
	
	@AfterMapping
	protected <T extends ClientDTO> T displayName(ClientEntity in, @MappingTarget T target) {
		
		// normalize the fund name to display
		target.setDisplayName(clientNameService.generate(in));
		
		return target;
	}

}
