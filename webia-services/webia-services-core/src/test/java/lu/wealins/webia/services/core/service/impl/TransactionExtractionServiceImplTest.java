package lu.wealins.webia.services.core.service.impl;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.wealins.webia.services.core.components.PstIdWrapper;
import lu.wealins.webia.services.core.mapper.TransactionMapper;
import lu.wealins.webia.services.core.persistence.entity.TransactionEntity;
import lu.wealins.webia.services.core.service.TransactionExtractionService;
import lu.wealins.common.dto.webia.services.TransactionDTO;

/**
 * 
 * @author xqv99
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
public class TransactionExtractionServiceImplTest {
	@Autowired
	TransactionExtractionService transactionExtractionServiceImpl;

	private Collection<TransactionDTO> transactionDTOs;

	//@Before
	public void setup() throws SQLException {
		/*
		 * Make sure java.txt file exists If it doesn't exists, run serializeFindAllDistinctAccountTransactionByPagination in lu.wealins.liability.business.impl.AccountTransactionRepoTest
		 */
		try (InputStream file = Files.newInputStream(Paths.get("c://temp/java.txt"));) {
			ObjectMapper mapper = new ObjectMapper();
			// deserialize the List
			transactionDTOs = (List<TransactionDTO>) mapper.readValue(file, new TypeReference<List<TransactionDTO>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error read file");
		}
	}

	// @Test
	public void testProcessAccountTransactionsIntoSAPAccounting() {
		assertNotNull(transactionDTOs);
		Collection<PstIdWrapper> postids = transactionExtractionServiceImpl.processAccountTransactionsIntoSAPAccounting(transactionDTOs);
		assertNotNull(postids);
	}
	@Test
	@Transactional
	public void testRemoveTransactionsFromSAPAccounting() {
		List<Long> ids = Arrays.asList(18617L,18618L);
		Long nbr= transactionExtractionServiceImpl.removeTransactionsFromSAPAccounting(ids);
		assertNotNull(nbr);
	}
	
	
}
