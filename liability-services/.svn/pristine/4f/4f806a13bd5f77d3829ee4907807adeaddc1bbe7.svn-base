package lu.wealins.liability.services.core.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.tempuri.wssupdpst.WssupdpstImport.ImpGrpPst.Row.ImpItmPstPostingSets;

import lu.wealins.common.dto.liability.services.PstPostingSetsDTO;

@Mapper(componentModel = "spring")
public interface PstPostingSetMapper {

	PstPostingSetsDTO asPstPostingSetsDTO(ImpItmPstPostingSets in);

	Collection<PstPostingSetsDTO> asPstPostingSetsDTOs(Collection<ImpItmPstPostingSets> in);
	
	ImpItmPstPostingSets asImpItmPstPostingSet(PstPostingSetsDTO in);
	
	Collection<ImpItmPstPostingSets> asImpItmPstPostingSets(Collection<PstPostingSetsDTO> in);

}
