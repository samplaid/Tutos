package lu.wealins.liability.services.core.business;

import lu.wealins.liability.services.core.persistence.entity.ClientEntity;

public interface ClientNameService {

	String generate(ClientEntity c);
	
}
