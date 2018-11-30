package lu.wealins.liability.services.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import lu.wealins.liability.services.core.persistence.entity.ClientEntity;
import lu.wealins.liability.services.core.persistence.entity.TransactionGroupedByFundNoEntity;
import lu.wealins.liability.services.core.utils.StringToTrimString;
import lu.wealins.common.dto.liability.services.InsuredDTO;
import lu.wealins.common.dto.liability.services.TransactionGroupedByFundDTO;

@Mapper(componentModel = "spring", uses = {StringToTrimString.class})
public interface TransactionGroupedByFundMapper {

	TransactionGroupedByFundDTO asTransactionGroupedByFundDTO(TransactionGroupedByFundNoEntity in);
	
	List<TransactionGroupedByFundDTO> asTransactionGroupedByFundDTOList(List<TransactionGroupedByFundNoEntity> in);
}
