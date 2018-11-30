/**
 * 
 */
package lu.wealins.batch.priceinjection;

import java.io.File;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.batch.injection.acl.ACLInjector;

/**
 * @author xqt5q
 *
 */
@ActiveProfiles(value = "unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath:lu/wealins/batch/aclInjection/acl-injection-test-context.xml"
})
@Ignore
public class ACLInjectorTest {

	@Autowired
	private ACLInjector aclInjector;

	public void test() {
		aclInjector.inject(new File("C:\\localTemp\\bil.txt"));
	}

}
