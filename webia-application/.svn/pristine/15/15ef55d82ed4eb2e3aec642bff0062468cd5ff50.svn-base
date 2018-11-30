/**
 * 
 */
package lu.wealins.webia.core.service.helper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lu.wealins.common.dto.webia.services.TransferDTO;

@Component
public class TransferIdsHelper {
	
	public String mapDtosToString(Collection<TransferDTO> transfers) {
		return transfers.stream().map(t -> t.getTransferId().toString()).collect(Collectors.joining(","));
	}

	public List<Long> mapStringToIds(String idsString) {
		return Arrays.stream(idsString.split(",")).map(Long::parseLong).collect(Collectors.toList());
	}
}
