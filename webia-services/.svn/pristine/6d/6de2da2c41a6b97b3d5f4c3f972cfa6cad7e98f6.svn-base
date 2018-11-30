package lu.wealins.webia.services.core.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.common.dto.webia.services.enums.ClientRelationType;
import lu.wealins.webia.services.core.mapper.ClientFormMapper;
import lu.wealins.webia.services.core.persistence.entity.ClientFormEntity;
import lu.wealins.webia.services.core.persistence.repository.ClientFormRepository;
import lu.wealins.webia.services.core.predicates.ClientRelationTypeAndClientIdPredicate;
import lu.wealins.webia.services.core.service.ApplicationParameterService;
import lu.wealins.webia.services.core.service.ClientFormService;
import lu.wealins.common.dto.webia.services.ClientFormDTO;

@Service
public class ClientFormServiceImpl implements ClientFormService {

	private static final String FORM_ID_CANNOT_BE_NULL2 = "Form id cannot be null.";
	private static final String RELATION_TYPE_CANNOT_BE_NULL = "Relation type cannot be null";
	private static final String CLIENT_ID_CANNOT_BE_NULL = "Client id cannot be null";
	private static final String FORM_ID_CANNOT_BE_NULL = "Form id cannot be null";
	private static final String EXEMPT_CPR_TYPE = "EXEMPT_CPR_TYPE";

	@Autowired
	private ClientFormRepository clientFormRepository;
	@Autowired
	private ClientFormMapper clientFormMapper;
	@Autowired
	private ApplicationParameterService applicationParameterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ClientFormService#hasRelation(java.lang.Integer, java.lang.Integer, lu.wealins.common.dto.webia.services.enums.ClientRelationType)
	 */
	@Override
	public Boolean hasRelation(Integer formId, Integer clientId, ClientRelationType relationTYpe) {
		Assert.notNull(formId, FORM_ID_CANNOT_BE_NULL);
		Assert.notNull(clientId, CLIENT_ID_CANNOT_BE_NULL);
		Assert.notNull(relationTYpe, RELATION_TYPE_CANNOT_BE_NULL);

		Collection<ClientFormEntity> clientForms = clientFormRepository.findByFormIdAndClientId(formId, clientId);

		ClientFormEntity usufructuaryPolRelationship = IterableUtils.find(clientForms,
				new ClientRelationTypeAndClientIdPredicate(relationTYpe, clientId.intValue()));

		return BooleanUtils.toBooleanObject(usufructuaryPolRelationship != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ClientFormService#getOtherClients(java.util.Collection)
	 */
	@Override
	public Collection<ClientFormDTO> getOtherClients(Collection<ClientFormEntity> clientForms) {
		List<Integer> excludedRoles = applicationParameterService.getIntegerValues(EXEMPT_CPR_TYPE);
		excludedRoles.addAll(ClientRelationType.CLIENTS_EXCLUDED_RELATIONS);

		Collection<ClientFormEntity> filteredClientForms = clientForms.stream().filter(role -> !excludedRoles.contains(role.getClientRelationTp())).collect(Collectors.toList());

		return clientFormMapper.asClientFormDTOs(filteredClientForms);
	}

	@Override
	public Collection<ClientFormEntity> updateOtherClients(Collection<ClientFormDTO> otherClients, Integer formId) {

		otherClients.forEach(x -> x.setFormId(formId));
		Collection<ClientFormEntity> clientFormEntities = clientFormMapper.asClientFormEntities(otherClients);

		return clientFormRepository.save(clientFormEntities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.services.core.service.ClientFormService#deleteWithFormId(java.lang.Integer)
	 */
	@Override
	public void deleteWithFormId(Integer formId) {
		Assert.notNull(formId, FORM_ID_CANNOT_BE_NULL2);
		// Remove all
		clientFormRepository.deleteWithFormId(formId);
	}


}
