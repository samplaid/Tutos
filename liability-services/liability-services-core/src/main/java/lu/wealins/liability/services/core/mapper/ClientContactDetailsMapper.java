package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tempuri.wssupers.WssupersImport.ImpGrpClc.Row.ImpItmClcClientContactDetails;

import lu.wealins.liability.services.core.persistence.entity.ClientContactDetailEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.ClientContactDetailDTO;

@Mapper(componentModel = "spring", uses = { StringToTrimString.class })
public abstract class ClientContactDetailsMapper {

	@Mappings({
			@Mapping(source = "clientId", target = "cliId"),
			@Mapping(source = "country.ctyId", target = "country"),
			@Mapping(source = "country.ptCode", target = "countryCode")
	})
	public abstract ClientContactDetailDTO asClientContactDetailDTO(ClientContactDetailEntity in);

	public abstract List<ClientContactDetailDTO> asClientContactDetailDTOs(List<ClientContactDetailEntity> in);

	@Mappings({
			@Mapping(source = "dateOfCountryChange", target = "dateOfCountryChange", dateFormat = "yyyyMMdd"),
	})
	public abstract ImpItmClcClientContactDetails asImpItmClcClientContactDetails(ClientContactDetailDTO homeAddress);

}
