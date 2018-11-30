package lu.wealins.liability.services.ws.rest.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lu.wealins.liability.services.core.business.CliPolRelationshipService;
import lu.wealins.liability.services.core.business.ClientClaimsDetailService;
import lu.wealins.liability.services.core.utils.SecurityContextUtils;
import lu.wealins.liability.services.ws.rest.ClientClaimsDetailRESTService;
import lu.wealins.common.dto.liability.services.ClientClaimsDetailDTO;

@Component
public class ClientClaimsDetailRESTServiceImpl implements ClientClaimsDetailRESTService {

	private static final String DATE_OF_DEATH_ALREADY_EXISTS = "A date of death already exists for this client.";
	
	@Autowired
	private ClientClaimsDetailService clientClaimsDetailService;
	@Autowired
	private SecurityContextUtils securityContextUtils;

	@Autowired
	private CliPolRelationshipService cliPolRelationshipService;


	@Override
	public Collection<ClientClaimsDetailDTO> getClientClaimsDetails(SecurityContext context, Integer id) {
		return clientClaimsDetailService.getClientClaimsDetails(id);
	}

	@Override
	public ClientClaimsDetailDTO save(SecurityContext context, ClientClaimsDetailDTO clientClaimsDetail) {

		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(clientClaimsDetail);

		String userName = securityContextUtils.getPreferredUsername(context);
		clientClaimsDetail.setStatus(1);
		if (!StringUtils.hasText(clientClaimsDetail.getCreatedProcess())){
			clientClaimsDetail.setCreatedProcess("WEBIA");
		}
		return clientClaimsDetailService.save(clientClaimsDetail, userName);
	}
	
	@Override
	public ClientClaimsDetailDTO notifyDeath(SecurityContext context, ClientClaimsDetailDTO clientClaimsDetail) {
		Assert.notNull(context);
		Assert.notNull(context.getUserPrincipal());
		Assert.notNull(clientClaimsDetail);
		Assert.notNull(clientClaimsDetail.getDateDeathNotified());
		Assert.notNull(clientClaimsDetail.getDateOfDeath());
		
		Collection<ClientClaimsDetailDTO> clientClaimsDetails = clientClaimsDetailService.getClientClaimsDetails(clientClaimsDetail.getCliId());
		boolean hasAlreadyDeathClaim = clientClaimsDetails.stream().anyMatch(ccd -> ccd!=null && ccd.getDateOfDeath()!=null && LocalDateTime.ofInstant(ccd.getDateOfDeath().toInstant(), ZoneId.systemDefault()).getYear()>1900 );
		if (hasAlreadyDeathClaim){
			throw new IllegalStateException(DATE_OF_DEATH_ALREADY_EXISTS);
		}

		ClientClaimsDetailDTO claim = save(context, clientClaimsDetail);
		cliPolRelationshipService.closeClientPolicyRelationShip(claim.getCliId(), clientClaimsDetail.getDateDeathNotified());

		return claim;
	}
	


}
