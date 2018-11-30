package lu.wealins.common.collection;

import java.util.Collection;

public final class CollectionUtils {

	private CollectionUtils() {
	}

	public static <T> T extractSingletonOrNull(Collection<T> c) {

		if (c == null || c.size() == 0) {
			return null;
		}
		if (c.size() > 1) {
			throw new IllegalArgumentException("Collection contains more than one element.");
		}

		return c.iterator().next();
	}
}
