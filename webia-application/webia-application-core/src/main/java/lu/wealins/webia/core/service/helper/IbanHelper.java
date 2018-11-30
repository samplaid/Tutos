/**
 * 
 */
package lu.wealins.webia.core.service.helper;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class IbanHelper {
	
	public String formatIban(String iban) {
		if (StringUtils.isBlank(iban)) {
			return null;
		}
		final AtomicInteger counter = new AtomicInteger(0);
		return iban.chars()
				.mapToObj(i -> String.valueOf((char) i).toUpperCase())
				.collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 4, Collectors.joining()))
				.values()
				.stream()
				.collect(Collectors.joining(" "));
	}
}
