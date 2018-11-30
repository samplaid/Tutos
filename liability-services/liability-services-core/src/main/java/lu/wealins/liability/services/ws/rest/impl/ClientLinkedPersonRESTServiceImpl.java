package lu.wealins.liability.services.ws.rest.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.wealins.common.dto.liability.services.ClientLinkedPersonDTO;
import lu.wealins.liability.services.core.business.ClientLinkedPersonService;
import lu.wealins.liability.services.ws.rest.ClientLinkedPersonRESTService;

@Component
public class ClientLinkedPersonRESTServiceImpl implements ClientLinkedPersonRESTService {

	@Autowired
	private ClientLinkedPersonService clientLinkedPersonService;

	@Override
	public List<ClientLinkedPersonDTO> getClientLinkedPerson(SecurityContext context, Long id) {
		return clientLinkedPersonService.getClientLinkedPerson(id);
	}

}
