package lu.wealins.common.dto.liability.services.enums;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SendingRuleType {

	MCH, MCHA, MCHC, MCHCA, MCC, MCCA, MA, MAAC, MH1CH2, HM, HMNF, HMA;

	public static Set<SendingRuleType> SEND_A_COPY_GROUP = Stream.of(MCHA, MCHCA, HMA, MCCA, MAAC).collect(Collectors.toSet());
}
