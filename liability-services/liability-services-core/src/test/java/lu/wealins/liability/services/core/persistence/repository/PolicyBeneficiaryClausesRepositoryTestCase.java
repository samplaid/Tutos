package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/mock/dev/coreContextWithNoWebService.xml" })
@ActiveProfiles("dev-test")
public class PolicyBeneficiaryClausesRepositoryTestCase {

	@Autowired
	private PolicyBeneficiaryClausesRepository repository;
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Test
	public void testLoad() {

		List<PolicyBeneficiaryClauseEntity> list = em.createQuery("SELECT b FROM PolicyBeneficiaryClauseEntity b")
				.setMaxResults(1).getResultList();

		if (!list.isEmpty()) {
			PolicyBeneficiaryClauseEntity e = repository.findOne(list.iterator().next().getPbcId());
			Assert.assertNotNull(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByPolicyAndStatus() {

		List<String> list = em
				.createNativeQuery("SELECT policy FROM POLICY_BENEFICIARY_CLAUSES WHERE status = 1 and type like 'Death'")
				.setMaxResults(1).getResultList();

		if (!list.isEmpty()) {

			List<PolicyBeneficiaryClauseEntity> clauses = repository
					.findAllByPolicyAndTypeIgnoreCaseAndStatusOrderByRankAsc(list.iterator().next(), "DeaTh", 1);

			Assert.assertNotNull(clauses);
			Assert.assertFalse(clauses.isEmpty());
		}
	}
}
