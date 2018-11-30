/**
 * 
 */
package lu.wealins.batch.bloomberg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.batch.injection.bloomberg.SoliamVniParser;
import lu.wealins.rest.model.BatchInjectionControlResponse;

/**
 * 
 * @author lax
 *
 */
@ActiveProfiles(value = "unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:lu/wealins/batch/injection/bloomberg/soliam-vni-parser-test-context.xml"
})
public class SoliamVniParserTest {

	@Autowired
	private SoliamVniParser soliamVniParser;

	@Test
	public void testGBP() {
		BatchInjectionControlResponse responseGBp = soliamVniParser.buildInjectionControlRequest("LU0157929810|0|5|LU0157929810|2096.990000|2018/06/25|GBp|LX|");
		assertEquals("CLE	LU0157929810+GBP	CVF	20.969900	GBP	25/06/2018		INJECT", responseGBp.getLines().get(0));
		
		BatchInjectionControlResponse responseGBp2 = soliamVniParser.buildInjectionControlRequest("LU0157929810|0|5|LU0157929810|12.34|2018/06/25|GBp|LX|");
		assertEquals("CLE	LU0157929810+GBP	CVF	0.1234	GBP	25/06/2018		INJECT", responseGBp2.getLines().get(0));

		BatchInjectionControlResponse responseGBp3 = soliamVniParser.buildInjectionControlRequest("LU0157929810|0|5|LU0157929810|1234.567891|2018/06/25|GBp|LX|");
		assertEquals("CLE	LU0157929810+GBP	CVF	12.34567891	GBP	25/06/2018		INJECT", responseGBp3.getLines().get(0));
		
		BatchInjectionControlResponse responseGBP = soliamVniParser.buildInjectionControlRequest("LU0157929810|0|5|LU0157929810|2096.990000|2018/06/25|GBP|LX|");
		assertEquals("CLE	LU0157929810+GBP	CVF	2096.990000	GBP	25/06/2018		INJECT", responseGBP.getLines().get(0));
		
		BatchInjectionControlResponse responseEUR = soliamVniParser.buildInjectionControlRequest("LU0157929810|0|5|LU0157929810|2096.990000|2018/06/25|EUR|LX|");
		assertEquals("CLE	LU0157929810+EUR	CVF	2096.990000	EUR	25/06/2018		INJECT", responseEUR.getLines().get(0));
	}

}
