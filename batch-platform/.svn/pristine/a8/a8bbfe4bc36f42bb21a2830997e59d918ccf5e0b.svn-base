/**
 * 
 */
package lu.wealins.batch.priceinjection;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import lu.wealins.batch.injection.price.PriceInjectionParser;

/**
 * @author xqv66
 *
 */
// @ActiveProfiles(value = "unit-test")
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "classpath:lu/wealins/batch/injection/price/price-injection-test-context.xml"
// })
public class PriceInjectionParserTest {

	@Autowired
	private PriceInjectionParser priceInjectionParser;

	// @Test
	public void test() {
		priceInjectionParser.parse(new File("C:\\temp\\test.csv"));
	}

}
