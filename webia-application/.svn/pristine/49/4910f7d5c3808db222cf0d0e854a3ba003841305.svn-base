package lu.wealins.webia.core.utils;

import org.springframework.stereotype.Component;

@Component
public class PrimitiveShortToBoolean {

	public Boolean asBoolean(short i) {
		return (i != 0);
	}
	
	public short asPrimitiveShort(Boolean b) {
		return (short) (b == Boolean.TRUE ? 1 : 0);
	}

	public short asPrimitiveShort(Integer i) {
		return i != null ? i.shortValue() : (short) 0;
	}

}
