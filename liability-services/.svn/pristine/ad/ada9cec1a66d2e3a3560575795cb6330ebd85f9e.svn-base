package lu.wealins.liability.services.core.business.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.business.ClientClaimsDetailService;
import lu.wealins.liability.services.core.mapper.ClientClaimsDetailMapper;
import lu.wealins.liability.services.core.persistence.entity.ClientClaimsDetailEntity;
import lu.wealins.liability.services.core.persistence.repository.ClientClaimsDetailRepository;
import lu.wealins.liability.services.core.utils.CalendarUtils;
import lu.wealins.liability.services.core.utils.HistoricManager;
import lu.wealins.common.dto.liability.services.ClientClaimsDetailDTO;

@Service
public class ClientClaimsDetailServiceImpl implements ClientClaimsDetailService {


	private static final Logger log = LoggerFactory.getLogger(ClientClaimsDetailServiceImpl.class);

	private static final String CLIENT_CLAIMS_DETAIL_CANNOT_BE_NULL = "Client claims detail to save cannot be null.";
	private static final String USER_NAME_CANNOT_BE_NULL = "User name cannot be null.";
	private static final String CLIENT_CLAIMS_DETAIL_CLIENT_ID_CANNOT_BE_NULL = "The client Id in the client claim detail cannot be null.";

	private static final String DATE_DEATH_NOTIFIED_CANNOT_BE_IN_THE_FUTURE = "The date death notified cannot be the future.";
	private static final String DATE_OF_DEATH_CANNOT_BE_IN_THE_FUTURE = "The date of death cannot be in the future.";
	private static final String DATE_OF_DEATH_CANNOT_BE_AFTER_DATE_DEATH_NOTIFIED = "The date of death cannot be after the date death notified.";
	

	@Autowired
	private ClientClaimsDetailRepository clientClaimsDetailRepository;
	@Autowired
	private ClientClaimsDetailMapper clientClaimsDetailMapper;
	@Autowired
	private HistoricManager historicManager;

	@Override
	public Collection<ClientClaimsDetailDTO> getClientClaimsDetails(Integer clientId) {
		if (clientId == null) {
			return null;
		}

		List<ClientClaimsDetailEntity> ccd = clientClaimsDetailRepository.findByClientId(clientId);
		return clientClaimsDetailMapper.asClientClaimsDetailDTOs(ccd);
	}


	@Override
	public ClientClaimsDetailDTO save(ClientClaimsDetailDTO clientClaimsDetail, String userName) {
		Assert.notNull(clientClaimsDetail, CLIENT_CLAIMS_DETAIL_CANNOT_BE_NULL);
		Assert.notNull(userName, USER_NAME_CANNOT_BE_NULL);
		Assert.notNull(clientClaimsDetail.getCliId(), CLIENT_CLAIMS_DETAIL_CLIENT_ID_CANNOT_BE_NULL);
		validClientClaimsDetailDTO(clientClaimsDetail);

		ClientClaimsDetailEntity entity = clientClaimsDetailMapper.asClientClaimsDetailEntity(clientClaimsDetail);
		historicManager.historize(entity);
		ClientClaimsDetailEntity savedEntity = clientClaimsDetailRepository.save(entity);
		if (savedEntity != null){
			log.debug("ClientClaimsDetailDTO {} has been saved.", savedEntity.getCcmId());
			return clientClaimsDetailMapper.asClientClaimsDetailDTO(savedEntity);
		} else {
			return null;
		}
	}
	
	private void validClientClaimsDetailDTO (ClientClaimsDetailDTO clientClaimsDetail){
		
		//if dateDeathNotified is set, it cannot be in the future
		Date dateDeathNotified = clientClaimsDetail.getDateDeathNotified();
		if (dateDeathNotified != null && CalendarUtils.isInFuture(dateDeathNotified)) {
			throw new IllegalStateException(DATE_DEATH_NOTIFIED_CANNOT_BE_IN_THE_FUTURE);
		}	
		
		//if dateOfDeath is set, it cannot be in the future
		Date dateOfDeath = clientClaimsDetail.getDateOfDeath();
		if (dateOfDeath != null && CalendarUtils.isInFuture(dateOfDeath)) {
			throw new IllegalStateException(DATE_OF_DEATH_CANNOT_BE_IN_THE_FUTURE);
		}	
		
		//dateOfDeath cannot be after dateDeathNotified
		if (dateOfDeath !=null && dateDeathNotified !=null && dateOfDeath.after(dateDeathNotified)){
			throw new IllegalStateException(DATE_OF_DEATH_CANNOT_BE_AFTER_DATE_DEATH_NOTIFIED);
		}
	}
	
	
	
}
