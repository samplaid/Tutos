package lu.wealins.common.dto.webia.services.enums;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TransferCd {

	BROKER("BROKER"), WITHDRAWAL("WITHDRAWAL"), ADMINL_FEE("ADMIN_FEE"), SURRENDER("SURRENDER");

	public final static Set<TransferCd> COMPTA_GROUP = Stream.of(WITHDRAWAL, ADMINL_FEE, SURRENDER).collect(Collectors.toCollection(HashSet::new));

	private TransferCd(String code) {
		this.code = code;
	}

	private final String code;

	public String getCode() {
		return code;
	}

	public static boolean isInComptaGroup(String code) {
		return COMPTA_GROUP.stream().anyMatch(x -> x.getCode().equalsIgnoreCase(code));
	}
}
