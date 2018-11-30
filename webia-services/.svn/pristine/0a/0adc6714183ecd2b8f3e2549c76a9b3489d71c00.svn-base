package lu.wealins.webia.services.core.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.common.dto.webia.services.ExtractOrderResponse;
import lu.wealins.common.dto.webia.services.PageResult;
import lu.wealins.common.dto.webia.services.SapAccountingDTO;
import lu.wealins.webia.services.core.service.ExtractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
@Ignore
public class ExtractServiceImplTest {
	@Value("${sapAccountingInjectionSuccessFile}")
	private String sapAccountingInjectionSuccessFile;
	@Autowired
	private ExtractService extractservice;

	@Test
	public void testExtractSapAccounting() {
		PageResult<SapAccountingDTO> result = null;

		result = extractservice.extractSapAccounting("DALI", 0, 1, 0L);

		assertNotNull(result);
	}

	//@Test
	public void testAllExtractOrder() {
		ExtractOrderResponse extractOrder = extractservice.extractOrder();
		assertNotNull(extractOrder);
		assertTrue(extractOrder.getDaliEstimatedOrder().size() > 0);
		assertTrue(extractOrder.getDaliValorizedOrder().size() > 0);
		assertTrue(extractOrder.getLissiaEstimatedOrder().size() > 0);
		assertTrue(extractOrder.getLissiaValorizedOrder().size() > 0);

	}

	@Test
	public void checkExtractionExistWithinFile() {
		System.out.println("TEST INFO :" + sapAccountingInjectionSuccessFile);
		assertNotNull(sapAccountingInjectionSuccessFile);
		// Delete file
		Path path = Paths.get(sapAccountingInjectionSuccessFile);
		try {

			deleteDirectory(path);

			PageResult<SapAccountingDTO> result = extractservice.extractSapAccounting("DALI", 0, 1, 0L);
			
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void deleteDirectory(Path rootPath) {
		try {
			// Delete files
			Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS)
					.map(Path::toFile)
					.forEach(File::delete);
			// delete root directory
			Files.deleteIfExists(rootPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
