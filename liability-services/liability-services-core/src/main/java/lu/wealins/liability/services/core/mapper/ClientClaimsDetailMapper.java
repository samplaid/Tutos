package lu.wealins.liability.services.core.mapper;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import lu.wealins.liability.services.core.persistence.entity.ClientClaimsDetailEntity;
import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ClientClaimsDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class ClientClaimsDetailMapper {
	
	@Mappings({
			@Mapping(source = "client.cliId", target = "cliId")
	})
	public abstract ClientClaimsDetailDTO asClientClaimsDetailDTO(ClientClaimsDetailEntity claimsDetailEntity);
	
	public abstract Collection<ClientClaimsDetailDTO> asClientClaimsDetailDTOs(Collection<ClientClaimsDetailEntity> claimsDetailEntities);
	

	public abstract ClientClaimsDetailEntity asClientClaimsDetailEntity(ClientClaimsDetailDTO claimsDetailDTO);
	
	public abstract Collection<ClientClaimsDetailEntity> asClientClaimsDetailEntities(Collection<ClientClaimsDetailDTO> claimsDetailDTOs);	
	
	@AfterMapping
	public ClientClaimsDetailEntity afterEntityMapping(ClientClaimsDetailDTO in, @MappingTarget ClientClaimsDetailEntity target) {

		ClientEntity refClient = new ClientEntity();
		refClient.setCliId(in.getCliId());
		target.setClient(refClient);
		target.setCcmId(in.getCliId());
		
		target.setDateOfPtd(setDefaultDate(in.getDateOfPtd()));
		target.setPtdCertification(setDefaultDate(in.getPtdCertification()));
		target.setDatePtdNotified(setDefaultDate(in.getDatePtdNotified()));
		target.setDateCiNotified(setDefaultDate(in.getDateCiNotified()));
		target.setDateOfCi(setDefaultDate(in.getDateOfCi()));
		target.setCiCertification(setDefaultDate(in.getCiCertification()));
		
		return target;
	}
	
	private Date setDefaultDate(Date date){
		if (date == null){
			Date defaultDate = Date.from((LocalDate.of(1753, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			return defaultDate;
		}
		return date;
	}
	
}
