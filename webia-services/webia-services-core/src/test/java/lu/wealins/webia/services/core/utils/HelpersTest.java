package lu.wealins.webia.services.core.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HelpersTest {

	@Test
	public void testRemoveNoAlphaNumCharacter() {
		String original = "191-0000169/0";
		String replacement = Helpers.removeNoAlphaNumCharacter(original);
		assertTrue(replacement != original && !replacement.contains("-"));

	}

}
