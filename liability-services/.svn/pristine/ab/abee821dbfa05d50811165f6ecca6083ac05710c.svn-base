package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.common.dto.liability.services.AgentContactDTO;
import lu.wealins.common.dto.liability.services.AgentContactLiteDTO;
import lu.wealins.common.dto.liability.services.AgentDTO;
import lu.wealins.common.dto.liability.services.enums.AgentContactStatus;
import lu.wealins.liability.services.core.business.AgentContactService;
import lu.wealins.liability.services.core.business.AgentService;
import lu.wealins.liability.services.core.business.exceptions.AgentCreationException;
import lu.wealins.liability.services.core.business.exceptions.ReportExceptionHelper;
import lu.wealins.liability.services.core.mapper.AgentContactMapper;
import lu.wealins.liability.services.core.persistence.entity.AgentContactEntity;
import lu.wealins.liability.services.core.persistence.repository.AgentContactRepository;
import lu.wealins.liability.services.core.utils.DbMetadataPopulator;
import lu.wealins.liability.services.core.validation.AgentContactValidationService;

@Service
public class AgentContactServiceImpl implements AgentContactService {
	private static final Logger LOG = LoggerFactory.getLogger(AgentContactServiceImpl.class);

	@Autowired
	private AgentContactRepository agentContactRepository;

	@Autowired
	private AgentContactMapper agentContactMapper;

	@Autowired
	private AgentContactValidationService agentContactValidationService;

	@Autowired
	private AgentService agentService;
	
	@Autowired
	private DbMetadataPopulator dbMetadataPopulator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentContactService#save(lu.wealins.common.dto.liability.services.AgentContactDTO)
	 */
	@Override
	public AgentContactDTO save(AgentContactDTO agentContact) {
		boolean contactCreated = false;
		AgentContactDTO newAgentContact = null;
		AgentDTO contactStore = null;

		Assert.notNull(agentContact);
		Assert.hasText(agentContact.getAgentId());
		Assert.notNull(agentContact.getContact());
		Assert.hasText(agentContact.getContactFunction());

		List<AgentContactEntity> agentContacts = agentContactRepository.findByAgentId(agentContact.getAgentId());

		if (!agentContacts.isEmpty()) {
			Collection<AgentContactDTO> agentContactsDto = agentContactMapper.asAgentContactDTOs(agentContacts);
			if(agentContactsDto.stream().noneMatch(x -> agentContact.getAgcId() != null && x.getAgcId().intValue() == agentContact.getAgcId().intValue())) {
				agentContactsDto.add(agentContact);
			} else {
				agentContactsDto = agentContactsDto.stream().map(x -> (x.getAgcId().intValue() == agentContact.getAgcId().intValue()) ? agentContact : x).collect(Collectors.toList());
			}
			agentContactValidationService.validateContactFunction(agentContact.getAgentId(), agentContactsDto);
		}

		try {
			if (StringUtils.isEmpty(agentContact.getContact().getAgtId())) {
				AgentDTO newContact = agentService.create(agentContact.getContact());

				if (newContact != null && !StringUtils.isEmpty(newContact.getAgtId())) {
					contactCreated = true;
					contactStore = agentContact.getContact();
					agentContact.setContact(newContact);
					newAgentContact = saveAgentContact(agentContact);

					if (newAgentContact == null || newAgentContact.getAgcId() == null) {
						agentService.delete(newContact.getAgtId());
					}
				}

			} else {
				agentService.update(agentContact.getContact());
				newAgentContact = saveAgentContact(agentContact);
			}

		} catch (RuntimeException e) {
			if (contactCreated && !StringUtils.isEmpty(agentContact.getContact().getAgtId())) {
				agentService.delete(agentContact.getContact().getAgtId());
				agentContact.setContact(contactStore);
			}

			LOG.error("Cannot save the agent contact: contactId = {0}, agentID = {1}, contactFunction  = {2} : {3}", agentContact.getContact().getAgtId(), agentContact.getAgentId(),
					agentContact.getContactFunction(), e);

			if (e instanceof AgentCreationException) {
				AgentCreationException exc = (AgentCreationException) e;
				ReportExceptionHelper.throwErrors(exc.getErrors().iterator().next(), AgentCreationException.class);
			} else {
				StringBuilder errorsMessageBuilder = new StringBuilder();
				errorsMessageBuilder.append("Verifiy if an agent contact with keys agtId=");
				errorsMessageBuilder.append(agentContact.getAgentId());
				errorsMessageBuilder.append(", contactId=");
				errorsMessageBuilder.append(agentContact.getContact().getAgtId());
				errorsMessageBuilder.append(" and contact function=");
				errorsMessageBuilder.append(agentContact.getContactFunction());
				errorsMessageBuilder.append(" exists already.");
				ReportExceptionHelper.throwErrors(errorsMessageBuilder.toString(), AgentCreationException.class);
			}

		}

		return newAgentContact;
	}

	private AgentContactDTO saveAgentContact(AgentContactDTO agentContact) {
		AgentContactDTO newAgentContact = null;

		// Unique constrain check: composite key agent owner id, contact id and contact function. (keep Lissia DB constrain).
		long count = agentContactRepository.count(agentContact.getAgentId(), agentContact.getContact().getAgtId(), agentContact.getContactFunction(), AgentContactStatus.ACTIVE.getStatus());
		ReportExceptionHelper.throwErrorsIfTrue(count > 1, "An active agent contact with composite key (agentId, contactId, contactFunction) equal to (" + agentContact.getAgentId() + ", "
				+ agentContact.getContact().getAgtId() + ", " + agentContact.getContactFunction() + ") exists already.", AgentCreationException.class);
		AgentContactEntity entity = agentContactMapper.asAgentContactEntity(agentContact);

		if (entity.getAgcId() == null) {
			// Set the agent contact id if null for creation
			int pk = agentContactRepository.getNextId();
			entity.setAgcId(pk + 1);
		}

		if (mapModifyEntityFields(entity)) {
			newAgentContact = agentContactMapper.asAgentContactDTO(agentContactRepository.save(entity));
		}

		return newAgentContact;
	}

	private boolean mapModifyEntityFields(AgentContactEntity target) {
		if (target == null || StringUtils.isEmpty(target.getAgentId()) || target.getContact() == null) {
			return false;
		}

		boolean isMapped = false;
		AgentContactEntity objectRef = null;

		if (target.getAgcId() != null) {
			objectRef = agentContactRepository.findOne(target.getAgcId());
		}

		if (objectRef == null) {
			// Creation
			dbMetadataPopulator.setCreationMetadata(target);
			isMapped = true;
		} else {
			AgentContactDTO objectRefDto = agentContactMapper.asAgentContactDTO(objectRef);
			AgentContactDTO targetDto = agentContactMapper.asAgentContactDTO(target);

			// Only do an update when a change was made.
			if (!objectRefDto.equals(targetDto)) {
				dbMetadataPopulator.setModificationMetadata(target);
				isMapped = true;
			}
		}

		return isMapped;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.liability.services.core.business.AgentContactService#find(java.lang.Long)
	 */
	@Override
	public AgentContactDTO find(Integer agentContactId) {
		if (agentContactId == null) {
			return null;
		}

		return agentContactMapper.asAgentContactDTO(agentContactRepository.findOne(agentContactId.intValue()));
	}

	@Override
	public AgentContactLiteDTO findByAgentIdAndContactID(String agentId, String contactId) {
		return agentContactMapper.asAgentContactLiteDTO(agentContactRepository.findByAgentIdAndContactAgtId(agentId, contactId));
	}
}
