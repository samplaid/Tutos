package lu.wealins.liability.services.core.persistence.repository; 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.common.dto.liability.services.enums.ContactType;
import lu.wealins.liability.services.core.persistence.entity.ClientContactDetailEntity;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;

public interface ClientContactDetailRepository extends JpaRepository<ClientContactDetailEntity, String> {
	
	
	public List<ClientContactDetailEntity> findByClient(ClientEntity client);
	
	public ClientContactDetailEntity findByClientAndContactType(ClientEntity client, ContactType correspType);
	
}
