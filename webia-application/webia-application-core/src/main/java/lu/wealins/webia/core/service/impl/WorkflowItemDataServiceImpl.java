package lu.wealins.webia.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.common.dto.liability.services.MetadataDTO;
import lu.wealins.common.dto.liability.services.PolicyLightDTO;
import lu.wealins.common.dto.liability.services.ProductDTO;
import lu.wealins.common.dto.liability.services.WorkflowItemDataDTO;
import lu.wealins.common.dto.liability.services.enums.Metadata;
import lu.wealins.common.dto.webia.services.AppFormDTO;
import lu.wealins.common.dto.webia.services.FundFormDTO;
import lu.wealins.webia.core.service.LiabilityClientService;
import lu.wealins.webia.core.service.LiabilityPolicyService;
import lu.wealins.webia.core.service.LiabilityProductService;
import lu.wealins.webia.core.service.MetadataService;
import lu.wealins.webia.core.service.WorkflowItemDataService;
import lu.wealins.webia.core.service.helper.metadata.CpsMetadataHelper;

@Component
public class WorkflowItemDataServiceImpl implements WorkflowItemDataService {

	private static final String PRODUCT_CODE_CANNOT_BE_NULL = "Product Code cannot be null.";
	private static final String APP_FORM_DATA_CANNOT_BE_NULL = "App form data cannot be null.";
	private static final String USR_ID_CANNOT_BE_NULL = "User id cannot be null.";

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private LiabilityProductService productService;
	
	@Autowired
	private CpsMetadataHelper cpsMetadataHelper;

	@Autowired
	private AbstractFundFormService<FundFormDTO> fundFormService;

	@Autowired
	private LiabilityPolicyService policyService;

	@Autowired
	private LiabilityClientService clientService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.AppFormWorkflowItemService#createRegistrationWorkflowItemData(lu.wealins.webia.ws.rest.dto.AppFormDTO, java.lang.String)
	 */
	@Override
	@SuppressWarnings("boxing")
	public WorkflowItemDataDTO createCommonMetadata(AppFormDTO formData, String usrId) {

		Assert.notNull(formData, APP_FORM_DATA_CANNOT_BE_NULL);
		Assert.notNull(usrId, USR_ID_CANNOT_BE_NULL);
		Assert.notNull(formData.getProductCd(), PRODUCT_CODE_CANNOT_BE_NULL);

		MetadataDTO policyIdMetadata = metadataService.createMetadata(Metadata.POLICY_ID.getMetadata(), formData.getPolicyId());
		MetadataDTO productCdMetadata = metadataService.createMetadata(Metadata.PRODUCT_CD.getMetadata(), formData.getProductCd());
		MetadataDTO clientNameMetadata = metadataService.createMetadata(Metadata.CLIENT_NAME.getMetadata(), formData.getClientName());

		MetadataDTO countryManagerMetadata = null;
		ProductDTO product = productService.getProduct(formData.getProductCd());
		Assert.notNull(product, "Product " + formData.getProductCd() + " cannot be retrieved.");
		MetadataDTO nlCountryMetadata = metadataService.createMetadata(Metadata.NL_COUNTRY.getMetadata(), product.getNlCountry());

		if (CollectionUtils.isNotEmpty(formData.getCountryManagers())) {
			String agentId = getFirstCountryManagerAgentId(formData);
			countryManagerMetadata = metadataService.createMetadata(Metadata.COUNTRY_MANAGER.getMetadata(), agentId);
		}

		Long workflowItemId = new Long(formData.getWorkflowItemId());

		return metadataService.createWorkflowItemData(workflowItemId, usrId, nlCountryMetadata, policyIdMetadata, productCdMetadata, clientNameMetadata, countryManagerMetadata);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.WorkflowItemDataService#createCommonMetadata(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkflowItemDataDTO createCommonMetadata(String policyNumber, Long workflowItemId, String userId) {
		if (policyNumber != null) {
			Assert.notNull(workflowItemId);
			PolicyLightDTO policyLightDTO = policyService.getPolicyLight(policyNumber);

			MetadataDTO policyIdMetadata = metadataService.createMetadata(Metadata.POLICY_ID.getMetadata(), policyNumber);
			MetadataDTO productdMetadata = metadataService.createMetadata(Metadata.PRODUCT_CD.getMetadata(), policyLightDTO.getPrdId());
			MetadataDTO nlCountrydMetadata = metadataService.createMetadata(Metadata.NL_COUNTRY.getMetadata(), policyLightDTO.getNlCountry());
			MetadataDTO ClientMetadata = metadataService.createMetadata(Metadata.CLIENT_NAME.getMetadata(), clientService.getClientNames(policyNumber));

			return metadataService.createWorkflowItemData(workflowItemId, userId, policyIdMetadata, productdMetadata, nlCountrydMetadata, ClientMetadata);
		}

		return null;
	}

	private String getFirstCountryManagerAgentId(AppFormDTO subscription) {
		if (CollectionUtils.isEmpty(subscription.getCountryManagers())) {
			return null;
		}

		return subscription.getCountryManagers().iterator().next().getPartnerId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.AppFormWorkflowItemService#createDispatchWorkflowItemData(lu.wealins.webia.ws.rest.dto.AppFormDTO, java.lang.String)
	 */
	@Override
	@SuppressWarnings("boxing")
	public WorkflowItemDataDTO createDispatchWorkflowItemData(AppFormDTO subscription, String usrId) {
	
		List<MetadataDTO> cpsMetadata = cpsMetadataHelper.createFirstCpsMetadata(subscription.getFirstCpsUser());
		cpsMetadata.addAll(cpsMetadataHelper.createSecondCpsMetadata(subscription.getSecondCpsUser()));
		Long workflowItemId = new Long(subscription.getWorkflowItemId());
		return metadataService.createWorkflowItemData(workflowItemId, cpsMetadata, usrId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.webia.core.service.AppFormWorkflowItemService#createAnalysisWorkflowItemData(lu.wealins.webia.ws.rest.dto.AppFormDTO, java.lang.String)
	 */
	@Override
	@SuppressWarnings("boxing")
	public WorkflowItemDataDTO createAnalysisWorkflowItemData(AppFormDTO appForm, String usrId) {

		List<MetadataDTO> metadataList = new ArrayList<>();
		Long workflowItemId = new Long(appForm.getWorkflowItemId());
		Collection<FundFormDTO> fundForms = appForm.getFundForms();
		String isNewFidMetadata = Boolean.valueOf(fundFormService.hasNewFid(fundForms)).toString();
		String iNewFasMetadata = Boolean.valueOf(fundFormService.hasNewFas(fundForms)).toString();
		String iNewFidFasMetadata = Boolean.valueOf(fundFormService.hasFidOrFas(fundForms)).toString();
		
		metadataList.add(metadataService.createMetadata(Metadata.CLIENT_NAME.getMetadata(), appForm.getClientName()));
		metadataList.add(metadataService.createMetadata(Metadata.IS_NEW_FID.getMetadata(), isNewFidMetadata));
		metadataList.add(metadataService.createMetadata(Metadata.IS_NEW_FAS.getMetadata(), iNewFasMetadata));
		metadataList.add(metadataService.createMetadata(Metadata.FID_FAS.getMetadata(), iNewFidFasMetadata));
		metadataList.addAll(cpsMetadataHelper.createSecondCpsMetadata(appForm.getSecondCpsUser()));
		
		return metadataService.createWorkflowItemData(workflowItemId, metadataList, usrId);
	}
}
