package lu.wealins.webia.core.mapper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.common.dto.webia.services.SWIFTTransferDTO;
import lu.wealins.common.dto.webia.services.TransferComptaDTO;
import lu.wealins.common.dto.webia.services.TransferDTO;
import lu.wealins.common.dto.webia.services.enums.TransferCd;
import lu.wealins.webia.core.service.WebiaApplicationParameterService;

@Mapper(componentModel = "spring")
public abstract class TransferMapper {
	
	private static final String SURRENDER_CHARGE = "SURRENDER_TRANSFERT_CHARGE";
	private static final String WITHDRAWAL_CHARGE = "WITHDRAWAL_TRANSFERT_CHARGE";
	private static final String PSA = "PSA_";
	private static final String RSA = "RSA_";
	
	@Autowired
	private WebiaApplicationParameterService webiaApplicationParameterService;
	
	public abstract TransferComptaDTO asTransferComptaDTO(TransferDTO in);
	public abstract SWIFTTransferDTO asSWIFTTransferDTO(TransferDTO in);
	public abstract List<SWIFTTransferDTO> asSWIFTTransferDTO(List<TransferDTO> in);
	
	@AfterMapping
	protected SWIFTTransferDTO swiftTransferDTO(TransferDTO in,  @MappingTarget SWIFTTransferDTO target) {
		
		if(target== null) {
			return target;
		}
		
		if(in!=null) {
			if (TransferCd.WITHDRAWAL.getCode().equals(StringUtils.stripToEmpty(in.getTransferCd()))) {
				target.setTransferCd(TransferCd.WITHDRAWAL.getCode() + PSA + StringUtils.stripToEmpty(in.getFdsId()) + StringUtils.stripToEmpty(in.getPolicyOut()));
				target.setCharges(getCharge(WITHDRAWAL_CHARGE));
			} else if (TransferCd.SURRENDER.getCode().equals(StringUtils.stripToEmpty(in.getTransferCd()))) {
				target.setTransferCd(TransferCd.SURRENDER.getCode() + RSA + StringUtils.stripToEmpty(in.getFdsId()) + StringUtils.stripToEmpty(in.getPolicyOut()));
				target.setCharges(getCharge(SURRENDER_CHARGE));
			}else {
				target.setCharges(StringUtils.EMPTY);
				target.setTransferCd(StringUtils.EMPTY);
			}	
		}
		
		return target;
	}
	
	private String getCharge(String chargeCode) {
		List<String> charges = (List<String>) webiaApplicationParameterService
				.getApplicationParameters(chargeCode);
		if(charges != null && !charges.isEmpty()) {
			return StringUtils.stripToEmpty(charges.get(0));
		}
		return StringUtils.EMPTY;
	}

	public abstract TransferComptaDTO asTransferComptaDTO(TransferComptaDTO in);
}
