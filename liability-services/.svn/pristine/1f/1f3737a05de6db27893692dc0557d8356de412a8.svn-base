package lu.wealins.liability.services.core.business.impl.client.account;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lu.wealins.liability.services.core.business.client.account.BankingAccountService;
import lu.wealins.liability.services.core.persistence.entity.BankAccountEntity;
import lu.wealins.liability.services.core.persistence.repository.BankAccountRepository;
import lu.wealins.liability.services.core.utils.DbMetadataPopulator;
import lu.wealins.common.dto.liability.services.account.ClientAccountDTO;

@Service
public class BankingAccountServiceImpl implements BankingAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private DbMetadataPopulator dbMetadataPopulator;
	
	@Transactional
	public void updateClientAccounts(Collection<ClientAccountDTO> accountDtos, Integer clientId) {
		if(!CollectionUtils.isEmpty(accountDtos)) {
			accountDtos.forEach(accountDto -> {
				Long accountId = accountDto.getBkaId();
				//We ensure that the db request filters to take only client related accounts to avoid any account injection.
				BankAccountEntity accountEntity = bankAccountRepository.findByBkaIdAndClientCliId(accountId, clientId);
				update(accountEntity, accountDto);
			});		
		}
	}
	
	private void update(BankAccountEntity accountEntity, ClientAccountDTO accountDto) {
		if(accountEntity.getStatus() != accountDto.getStatus()) {
			accountEntity.setStatus(accountDto.getStatus());
			dbMetadataPopulator.setModificationMetadata(accountEntity);
		}
	}
}
