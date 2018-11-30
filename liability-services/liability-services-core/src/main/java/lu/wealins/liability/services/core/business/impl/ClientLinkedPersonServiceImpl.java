package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.wealins.common.dto.liability.services.ClientDTO;
import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;
import lu.wealins.common.dto.liability.services.UoptDetailDTO;
import lu.wealins.liability.services.core.business.ClientLinkedPersonService;
import lu.wealins.liability.services.core.business.ClientService;
import lu.wealins.liability.services.core.business.UoptDetailService;
import lu.wealins.liability.services.core.mapper.ClientLinkedPersonMapper;
import lu.wealins.liability.services.core.persistence.repository.ClientLinkedPersonRepository;

@Service
public class ClientLinkedPersonServiceImpl implements ClientLinkedPersonService {

	private static final Logger log = LoggerFactory.getLogger(ClientLinkedPersonServiceImpl.class);

	private static final String CTRL_TYPE = "CTRL";

	@Autowired
	private ClientLinkedPersonRepository clientLinkedPersonRepository;

	@Autowired
	private UoptDetailService uoptDetailService;

	@Autowired
	private ClientLinkedPersonMapper clientLinkedPersonMapper;

	@Autowired
	private ClientService clientService;

	@Override
	public List<ClientLinkedPersonDTO> getClientLinkedPerson(Long clientId) {
		List<ClientLinkedPersonDTO> list = new ArrayList<ClientLinkedPersonDTO>();
		Collection<ClientLinkedPersonDTO> collection = clientLinkedPersonMapper.asClientLinkedPersonDTOs(clientLinkedPersonRepository.findForClient(clientId.intValue()));
		if (collection != null && !collection.isEmpty()) {
			ArrayList<ClientLinkedPersonDTO> tempList = new ArrayList<ClientLinkedPersonDTO>(collection);
			for (ClientLinkedPersonDTO dto : tempList) {

				ClientDTO entity = clientService.getClient(dto.getEntity());
				if (entity != null) {
					dto.setEntityLabel(entity.getName());
				}

				if (dto.getType().trim().equals(CTRL_TYPE)) {
					UoptDetailDTO detail = uoptDetailService.getUoptDetailEntityForKeyValue(dto.getSubType());
					if (detail != null) {
						dto.setLabel(detail.getDescription());
					}
					list.add(dto);
				} else {
					UoptDetailDTO detail = uoptDetailService.getUoptDetailEntityForKeyValue(dto.getType());
					if (detail != null) {
						dto.setLabel(detail.getDescription());
					}
					list.add(dto);
				}
			}
		}

		return list;
	}

}
