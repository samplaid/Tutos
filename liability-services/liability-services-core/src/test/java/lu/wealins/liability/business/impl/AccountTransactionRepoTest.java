package lu.wealins.liability.business.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lu.wealins.common.dto.liability.services.AccountTransactionDTO;
import lu.wealins.liability.services.core.business.FundTransactionService;
import lu.wealins.liability.services.core.persistence.entity.AccountTransactionEntity;
import lu.wealins.liability.services.core.persistence.repository.AccountTransactionRepository;
import lu.wealins.liability.services.core.utils.constantes.Constantes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
public class AccountTransactionRepoTest {

	@Autowired
	private AccountTransactionRepository accountTransactionRepository;
	@Autowired
	FundTransactionService fundTransactionService;

	// @Test
	public void test() {
		long count = accountTransactionRepository.count();

		Assert.assertTrue(count > 0);
	}

	// @Test
	@Transactional(readOnly = true)
	public void findAllDistinctAccountTransactionLargeDatas() {
		// Make sure over 100000 records in data base
		long rowNumers = 0;
		AtomicLong counter = new AtomicLong(0);
		rowNumers = accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST).size();
		System.out.println("Rows number: " + rowNumers);
		Assert.assertTrue(rowNumers > 100000);

		accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST).forEach(t -> counter.incrementAndGet());
		Assert.assertEquals(rowNumers, counter.get());

	}

	// @Test
	@Transactional(readOnly = true)
	public void findAllDistinctAccountTransactionWithParamaters() {
		long count = 0;
		List<AccountTransactionEntity> stream = accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST);
		count = stream.size();
		System.out.println("Rows number: " + count);
		Assert.assertTrue(count > 0);
		System.out.println("Rows number: " + count);
	}

	 @Test
	@Transactional(readOnly = true)
	public void findAllDistinctAccountTransactionByPagination() {
		long count = 0;
		int PAGE_SIZE = 20000;
		Pageable page = new PageRequest(0, PAGE_SIZE);
		Page<AccountTransactionEntity> result = null;
		do {
			if (result != null)
				page = result.nextPageable();
			result = accountTransactionRepository.findAllDistinctAccountTransaction(Constantes.EVENT_TYPE_LIST, page);
			count = result.getNumberOfElements();
			System.out.println("Rows number: " + count);
			Assert.assertTrue(count <= PAGE_SIZE);
			System.out.println("Rows number: " + count);

		} while (result.hasNext());
	}

	public void setup() throws Exception {
		if (Files.notExists(Paths.get("c://temp/java.txt")))
			Files.createFile(Paths.get("c://temp/java.txt"));
	}

	// @Test
	@Transactional(readOnly = true)
	public void serializeFindAllDistinctAccountTransactionByPagination() throws Exception {
		this.setup();
		long count = 0;
		int PAGE_SIZE = 1000;
		Pageable page = new PageRequest(0, PAGE_SIZE);
		List<AccountTransactionDTO> result = null;
		long startTime = System.currentTimeMillis();
		result = fundTransactionService.getAllNoExportedTransactionsWithClosedPostingSet(0, PAGE_SIZE).getContent();
		long endTime = System.currentTimeMillis();

		System.out.println("Running time: " + (endTime - startTime) + " ms");
		// serialize the List
		startTime = System.currentTimeMillis();
		try (
				OutputStream file = Files.newOutputStream(Paths.get("c://temp/java.txt"));) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(file, result.stream().filter(t -> t.getTransaction0() == 5033406).collect(Collectors.toList()));

		} catch (IOException ex) {
			fail("Error writing file");
		}
		endTime = System.currentTimeMillis();
		System.out.println("Running time: " + (endTime - startTime) + " ms");
		assertTrue(true);
	}

	@Test
	public void f() {
		final String LISS_DATE_FORMAT = "yyyyMMdd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(LISS_DATE_FORMAT);
		System.out.println(dateFormat.format(new Date()));
	}
}
