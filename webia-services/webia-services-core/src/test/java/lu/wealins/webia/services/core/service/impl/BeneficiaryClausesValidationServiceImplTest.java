package lu.wealins.webia.services.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lu.wealins.webia.services.core.service.validations.beneficiarychangeform.BeneficiaryClausesValidationService;
import lu.wealins.webia.services.core.service.validations.beneficiarychangeform.impl.BeneficiaryClausesValidationServiceImpl;
import lu.wealins.common.dto.webia.services.BenefClauseFormDTO;
import lu.wealins.common.dto.webia.services.BeneficiaryFormDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/coreContext.xml" })
@ActiveProfiles("dev-test")
@Transactional
public class BeneficiaryClausesValidationServiceImplTest {
	

	
	@Autowired
	BeneficiaryClausesValidationService beneficiaryClausesValidationService;

	
	@Test
	public void validateClausesRulesNegativeRank(){
		
		
		List<BeneficiaryFormDTO> beneficiaryList = new ArrayList<>();
		List<BenefClauseFormDTO> clauses = new ArrayList<>();
		
		BeneficiaryFormDTO dto1 = new BeneficiaryFormDTO();
		dto1.setRankNumber(-1);
		beneficiaryList.add(dto1);
		
		BenefClauseFormDTO benefClause = new BenefClauseFormDTO();
		benefClause.setRankNumber(null);
		clauses.add(benefClause);
		
		List<String> errors = beneficiaryClausesValidationService.validateClausesRules(beneficiaryList, clauses);
		
		Assert.assertTrue(errors.contains(BeneficiaryClausesValidationServiceImpl.NEGATIVE_RANK_IS_NOT_ALLOWED));
		
	}
	
	@Test
	public void validaterulesRankEmptyClauseRankSequence(){
	
		
		List<BeneficiaryFormDTO> beneficiaryList = new ArrayList<>();
		List<BenefClauseFormDTO> clauses = new ArrayList<>();
		
		BeneficiaryFormDTO dto1 = new BeneficiaryFormDTO();
		dto1.setRankNumber(0);
		beneficiaryList.add(dto1);
		
		clauses = new ArrayList<>();
		BenefClauseFormDTO benefClause1 = new BenefClauseFormDTO();
		benefClause1.setRankNumber(1);
		BenefClauseFormDTO benefClause2 = new BenefClauseFormDTO();
		benefClause2.setClauseTp("Free");
		benefClause2.setRankNumber(3);;
		clauses.add(benefClause1);
		clauses.add(benefClause2);
		List<String> errors =  beneficiaryClausesValidationService.validateClausesRules(beneficiaryList, clauses);
				
		Assert.assertTrue(errors.contains(BeneficiaryClausesValidationServiceImpl.BENEF_CLAUSE_SEQUENCE_RANK));
		   
	}
	
	@Test
	public void validaterulesRankEmptyClauseRankPresence(){
		
		List<BeneficiaryFormDTO> beneficiaryList = new ArrayList<>();
		List<BenefClauseFormDTO> clauses = new ArrayList<>();
		
		BeneficiaryFormDTO dto1 = new BeneficiaryFormDTO();
		dto1.setRankNumber(1);
		beneficiaryList.add(dto1);
		
		BenefClauseFormDTO benefClause1 = new BenefClauseFormDTO();
		clauses.add(benefClause1);
		
		List<String> errors =  beneficiaryClausesValidationService.validateClausesRules(beneficiaryList, clauses);
				
		Assert.assertTrue(errors.contains(BeneficiaryClausesValidationServiceImpl.CLAUSE_RANK_REQUIRED));
		   
	}
	

}
