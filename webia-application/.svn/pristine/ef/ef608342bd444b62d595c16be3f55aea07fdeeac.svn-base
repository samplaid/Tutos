package lu.wealins.webia.core.mapper;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.AgentLightDTO;
import lu.wealins.common.dto.liability.services.ClientContactDetailDTO;
import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.CountryDTO;
import lu.wealins.common.dto.liability.services.OtherClientDTO;
import lu.wealins.common.dto.liability.services.PolicyHolderDTO;
import lu.wealins.editing.common.webia.Address;
import lu.wealins.editing.common.webia.AssetManager;
import lu.wealins.editing.common.webia.CorrespondenceAddress;
import lu.wealins.editing.common.webia.FollowUp;
import lu.wealins.editing.common.webia.OrderedPerson;
import lu.wealins.editing.common.webia.Person;
import lu.wealins.editing.common.webia.PersonGender;
import lu.wealins.editing.common.webia.PersonLight;
import lu.wealins.editing.common.webia.PolicyHolder;
import lu.wealins.webia.core.service.DocumentService;
import lu.wealins.webia.core.service.LiabilityCountryService;
import lu.wealins.webia.core.service.helper.FollowUpDocumentContentHelper;
import lu.wealins.webia.ws.rest.request.TranscoType;

/**
 * 
 * @author bqv55
 *
 */
@Mapper(componentModel = "spring")
public abstract class PersonMapper {

	@Autowired
	private DocumentService documentGenerationService;

	@Autowired
	private LiabilityCountryService countryService;

	private static final String DEFAULT_TITLE_ID = "MME_MR";

	@Autowired
	private FollowUpDocumentContentHelper followUpHelper;

	@Mappings({
			@Mapping(target = "id", source = "agtId"),
			@Mapping(target = "firstName", source = "firstname"),
			@Mapping(target = "lastName", source = "name"),
			@Mapping(target = "address.zipCode", source = "postcode"),
			@Mapping(target = "address.city", source = "town"),
			@Mapping(target = "address.country", source = "country"),
			@Mapping(target = "address.phone", source = "mobile"),
			@Mapping(target = "address.street", source = "addressLine2"),
			@Mapping(target = "physical", expression = "java(in.getType() == 1)")
	})
	public abstract Person asPerson(AgentDTO in);

	public abstract List<Person> asPersonList(List<AgentDTO> in);

	@Mappings({
			@Mapping(target = "zipCode", source = "postcode"),
			@Mapping(target = "city", source = "town"),
			@Mapping(target = "country", source = "country"),
			@Mapping(target = "phone", source = "telephone"), // mobile or telephone ?
			@Mapping(target = "street", source = "blockAddress")
	})
	public abstract CorrespondenceAddress asCorrespondenceAddress(AgentDTO in); // extends AgentLightDTO
	
	@Mappings({
			@Mapping(target = "zipCode", source = "postcode"),
			@Mapping(target = "city", source = "town"),
			@Mapping(target = "country", source = "country"),
			@Mapping(target = "phone", source = "telephone"),   // mobile or telephone ?
			@Mapping(target = "street", source = "blockAddress")
	})
	public abstract CorrespondenceAddress asCorrespondenceAddress(AgentLightDTO in);

	@Mappings({
			@Mapping(target = "firstName", source = "firstname"),
			@Mapping(target = "lastName", source = "name"),
			@Mapping(target = "physical", expression = "java(true)")
	})
	public abstract PersonLight asPersonLight(AgentDTO in);


	@Mappings({
			@Mapping(target = "firstName", source = "firstname"),
			@Mapping(target = "lastName", source = "name"),
			@Mapping(target = "physical", expression = "java(true)")
	})
	public abstract PersonLight asPersonLight(AgentLightDTO in);

	@Mappings({ @Mapping(target = "email", source = "email") })
	public abstract FollowUp.Destination agentDTOFollowUpDestination(AgentLightDTO in);

	// @Mappings({ @Mapping(target = "email", source = "email") })
	// public abstract FollowUp.Destination agentDTOFollowUpDestination(AgentDTO
	// in);


	public abstract List<PersonLight> asPersonLightList(List<AgentDTO> in);

	@Mappings({
			// @Mapping(target = "title", source = "contact.title"),
			@Mapping(target = "firstName", source = "contact.firstname"),
			@Mapping(target = "lastName", source = "contact.name"),
			@Mapping(target = "physical", expression = "java(true)")
	})
	public abstract PersonLight asPersonLight(AgentContactDTO in);

	@Mappings({
			// @Mapping(target = "title", source = "contact.title"),
			@Mapping(target = "firstName", source = "contact.firstname"),
			@Mapping(target = "lastName", source = "contact.name"),
			@Mapping(target = "physical", expression = "java(true)")
	})
	public abstract PersonLight asPersonLight(AgentContactLiteDTO in);

	@Mappings({
			@Mapping(target = "lastName", source = "name"),
			@Mapping(target = "birthDate", source = "dateOfBirth", dateFormat = "yyyy-MM-dd"),
			@Mapping(target = "birthPlace", source = "placeOfBirth"),
			@Mapping(target = "titleId", expression = "java(in.getSex()==null ? in.getSex().toString() : null)"),
			@Mapping(target = "physical", expression = "java(in.getType() == 1)")
	})
	public abstract OrderedPerson asOrderedPerson(ClientDTO in);

	@Mappings({
			@Mapping(target = "lastName", source = "name"),
			@Mapping(target = "birthDate", source = "dateOfBirth", dateFormat = "yyyy-MM-dd"),
			@Mapping(target = "birthPlace", source = "placeOfBirth"),
			@Mapping(target = "titleId", expression = "java(in.getSex()==null ? in.getSex().toString() : null)"),
			@Mapping(target = "physical", expression = "java(in.getType() == 1)"),
			@Mapping(target = "address", expression = "java(this.asAddress(in.getHomeAddress()))"),
	})
	public abstract OrderedPerson asOrderedPerson(OtherClientDTO in);

	public abstract List<OrderedPerson> asOrderedPersonList(List<OtherClientDTO> in);

	@Mappings({
			@Mapping(target = "lastName", source = "name"),
			@Mapping(target = "birthDate", source = "dateOfBirth", dateFormat = "yyyy-MM-dd"),
			@Mapping(target = "birthPlace", source = "placeOfBirth"),
			@Mapping(target = "titleId", expression = "java(in.getSex()==null ? in.getSex().toString() : null)"),
			@Mapping(target = "address", expression = "java(this.asAddress(in.getHomeAddress()))"),
			@Mapping(target = "physical", expression = "java(in.getType() == 1)"),
			@Mapping(target = "usuFructuary", source = "usufructuary"),
	})
	public abstract PolicyHolder asPolicyHolder(PolicyHolderDTO in);

	public abstract List<PolicyHolder> asPolicyHolderList(List<PolicyHolderDTO> in);

	@Mappings({
			@Mapping(target = "assetManagerName", source = "name")
	})
	public abstract AssetManager asAssetManager(AgentDTO in);

	@Mappings({
	})
	public abstract Person asPerson(PolicyHolder in);

	public abstract List<Person> holderAsPersonList(List<PolicyHolder> in);

	@Mappings({
	})
	public abstract PersonLight asPersonLight(PolicyHolder in);

	public abstract List<PersonLight> holderAsPersonLightList(List<PolicyHolder> in);

	@Mappings({
			@Mapping(target = "street", source = "address.street"),
			@Mapping(target = "zipCode", source = "address.zipCode"),
			@Mapping(target = "city", source = "address.city"),
			@Mapping(target = "residence", source = "address.residence"),
			@Mapping(target = "postBox", source = "address.postBox"),
			@Mapping(target = "countryCode", source = "address.countryCode"),
			@Mapping(target = "country", source = "address.country"),
			@Mapping(target = "type", source = "address.type"),
			@Mapping(target = "phone", source = "address.phone"),
			@Mapping(target = "floor", source = "address.floor"),
			@Mapping(target = "agencyName", source = "address.agencyName")
	})
	public abstract CorrespondenceAddress holderAsCorrespondenceAddress(PolicyHolder in);

	@Mappings({
			@Mapping(target = "street", source = "blockAddress"),
			@Mapping(target = "zipCode", source = "postcode"),
			@Mapping(target = "city", source = "town"),
			@Mapping(target = "country", source = "country"),
			@Mapping(target = "phone", source = "homePhone"),
	})
	public abstract CorrespondenceAddress clientContactDetailAsCorrespondenceAddress(ClientContactDetailDTO in);

	@Mappings({
			@Mapping(target = "zipCode", source = "postcode"),
			@Mapping(target = "city", source = "town"),
			@Mapping(target = "phone", source = "homePhone"),
			@Mapping(target = "street", source = "blockAddress")
	})
	public abstract Address asAddress(ClientContactDetailDTO in);

	@AfterMapping
	protected PersonLight agentDTOToPersonLightAfterMapping(AgentDTO in, @MappingTarget PersonLight target) {
		if (target.isPhysical()) {
			String titleIdTranscoded = documentGenerationService.getTransco(TranscoType.TITLE, in.getTitle());
			target.setTitleId(titleIdTranscoded != null && !titleIdTranscoded.isEmpty() ? titleIdTranscoded : null);
			target.setTitle(null);
		} else {
			target.setTitleId(null);
		}

		return target;
	}

	@AfterMapping
	protected Person agentDTOToPersonrAfterMapping(AgentDTO in, @MappingTarget Person target) {
		if (target.isPhysical()) {
			String titleIdTranscoded = documentGenerationService.getTransco(TranscoType.TITLE, in.getTitle());
			target.setTitleId(titleIdTranscoded != null && !titleIdTranscoded.isEmpty() ? titleIdTranscoded : DEFAULT_TITLE_ID);
		} else {
			target.setTitleId(DEFAULT_TITLE_ID);
		}

		return target;
	}

	@AfterMapping
	protected Person clientDTOTOPolicyHolderAfterMapping(ClientDTO in, @MappingTarget PolicyHolder target) {
		String sex = in.getSex().toString();

		String genderTransco = documentGenerationService.getTransco(TranscoType.GENDER, sex);
		target.setGender(genderTransco != null && !genderTransco.isEmpty() ? PersonGender.fromValue(genderTransco) : null);

		if (target.isPhysical()) {
			String titleIdTranscoded = documentGenerationService.getTransco(TranscoType.TITLE, sex);
			target.setTitleId(titleIdTranscoded != null && !titleIdTranscoded.isEmpty() ? titleIdTranscoded : DEFAULT_TITLE_ID);
		} else {
			target.setTitleId(DEFAULT_TITLE_ID);
		}

		return target;
	}

	@AfterMapping
	protected OrderedPerson otherClientDTOTOOrderedPersonAfterMapping(OtherClientDTO in, @MappingTarget OrderedPerson target) {
		Integer typeNumber = in.getTypeNumber();
		if (typeNumber != null) {
			target.setOrder(++typeNumber);
		}
		return target;
	}

	@AfterMapping
	protected OrderedPerson clientDTOTOPolicyHolderAfterMapping(ClientDTO in, @MappingTarget OrderedPerson target) {
		String sex = in.getSex().toString();

		String genderTransco = documentGenerationService.getTransco(TranscoType.GENDER, sex);
		target.setGender(genderTransco != null && !genderTransco.isEmpty() ? PersonGender.fromValue(genderTransco) : null);

		if (target.isPhysical()) {
			String titleIdTranscoded = documentGenerationService.getTransco(TranscoType.TITLE, sex);
			target.setTitleId(titleIdTranscoded != null && !titleIdTranscoded.isEmpty() ? titleIdTranscoded : DEFAULT_TITLE_ID);
		} else {
			target.setTitleId(DEFAULT_TITLE_ID);
		}

		return target;
	}

	@AfterMapping
	protected PersonLight agentContactDTOTOPersonLightAfterMapping(AgentContactDTO in, @MappingTarget PersonLight target) {
		return agentDTOToPersonLightAfterMapping(in.getContact(), target);
	}
	

	@AfterMapping
	protected PersonLight AgentContactLiteDTOTOPersonLightAfterMapping(AgentContactLiteDTO in,
			@MappingTarget PersonLight target) {
		return agentLightDTOTOPersonLightAfterMapping(in.getContact(), target);
	}

	@AfterMapping
	protected PersonLight agentLightDTOTOPersonLightAfterMapping(AgentLightDTO in, @MappingTarget PersonLight target) {
		if (target.isPhysical()) {
			String titleIdTranscoded = documentGenerationService.getTransco(TranscoType.TITLE, in.getTitle());
			target.setTitleId(titleIdTranscoded != null && !titleIdTranscoded.isEmpty() ? titleIdTranscoded : null);
			target.setTitle(null);
		} else {
			target.setTitleId(DEFAULT_TITLE_ID);
		}
		return target;
	}

	@AfterMapping
	protected CorrespondenceAddress countryAfterMapping(PolicyHolder in, @MappingTarget CorrespondenceAddress target) {
		if (in.getAddress() != null) {
			String country = in.getAddress().getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.setCountry(country);
		}
		return target;
	}

	@AfterMapping
	protected CorrespondenceAddress countryAfterMapping(ClientContactDetailDTO in, @MappingTarget CorrespondenceAddress target) {
		if (in.getCountry() != null && !in.getCountry().isEmpty()) {
			String country = in.getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.setCountry(country);
		}
		return target;
	}

	@AfterMapping
	protected Address addressCountryAfterMapping(ClientContactDetailDTO in, @MappingTarget CorrespondenceAddress target) {
		if (in.getCountry() != null && !in.getCountry().isEmpty()) {
			String country = in.getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.setCountry(country);
		}
		return target;
	}

	@AfterMapping
	protected CorrespondenceAddress addressCountryAfterMapping(AgentDTO in, @MappingTarget CorrespondenceAddress target) {
		if (in.getCountry() != null && !in.getCountry().isEmpty()) {
			String country = in.getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.setCountry(country);
		}
		return target;
	}
	
	@AfterMapping
	protected CorrespondenceAddress addressCountryAfterMapping(AgentLightDTO in, @MappingTarget CorrespondenceAddress target) {
		if (in.getCountry() != null && !in.getCountry().isEmpty()) {
			String country = in.getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.setCountry(country);
		}
		return target;
	}


	@AfterMapping
	protected Person countryAfterMapping(AgentDTO in, @MappingTarget Person target) {
		if (in.getCountry() != null && !in.getCountry().isEmpty()) {
			String country = in.getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.getAddress().setCountry(country);
		}
		return target;
	}

	@AfterMapping
	protected Address addressCountryAfterMapping(ClientContactDetailDTO in, @MappingTarget Address target) {
		if (in.getCountry() != null && !in.getCountry().isEmpty()) {
			String country = in.getCountry();

			CountryDTO countryDTO = countryService.getCountry(country);
			if (countryDTO != null) {
				country = countryDTO.getName();
			}
			target.setCountry(country);
		}
		return target;
	}

	@AfterMapping
	protected FollowUp.Destination agentDTOFollowUpDestinationAfterMapping(AgentLightDTO in,
			@MappingTarget FollowUp.Destination target) {
		if (in != null) {
			String language = getAgentLanguage(in);
			PersonLight personLigth = asPersonLight(in);
			target.setLanguage(language);
			target.setPerson(personLigth);
			return target;
		}
		return target;

	}

	private String getAgentLanguage(AgentLightDTO in) {
		return followUpHelper.getAgentLanguage(in);
	}

	// @AfterMapping
	// protected FollowUp.Destination
	// agentDTOFollowUpDestinationAfterMapping(AgentDTO in,
	// @MappingTarget FollowUp.Destination target) {
	// return agentDTOFollowUpDestinationAfterMapping(in, target);
	//
	// }

}