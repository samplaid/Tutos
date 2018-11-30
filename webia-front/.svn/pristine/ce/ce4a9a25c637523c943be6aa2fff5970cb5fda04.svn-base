import { Injectable, HostListener } from '@angular/core';
import { ConfigService } from './config.service';

/**
 * This file should be used to store all your Rest esbEndPoint URL
 * use {0},{1},{2}.... to define parameters. ex : getUserEvent: '/web/user/{0}/event/{1}',
 */
const API = {
    getCurrencies: '{esbEndPoint}/currency-all',  //'http://10.36.0.42:9090/pia/currency-all'    //'{esbEndPoint}/currency-all',  
    getCountries: '{esbEndPoint}/liability/country/all',
    getCountry: '{esbEndPoint}/liability/country/{0}',
    getProductLight: '{esbEndPoint}/liability/product/allLights',
    getProduct: '{esbEndPoint}/liability/product/{0}',            // {productCd}   
    getBenefClauseStd: '{esbEndPoint}/webia/benefClauseStd/{0}',   // {productCd}
    getBenefClauseStdTranslate: '{esbEndPoint}/webia/benefClauseStd/{0}/{1}',   // {productCd}/{langCd}
    getApplicationParameter: '{esbEndPoint}/webia/applicationParameter/{0}',   // {code}
    getCodeLabel: '{esbEndPoint}/webia/codeLabel/{0}',    //{type_Cd}
    getDeathCoverageClauses: '{webiaEndPoint}/deathCoverage/{0}',   // {productCd}
    getPolicyDeathCoverage: '{webiaEndPoint}/deathCoverage/policy?id={0}',   // {polId}

    //Check data
    acceptanceDecision: '{esbEndPoint}/webia/checkData/acceptanceDecision?workflowItemId={0}',
    
    // uopt detail
    getTitles:'{esbEndPoint}/liability/uoptDetail/titles',
    getSendingRules: '{esbEndPoint}/liability/uoptDetail/sendingRules',
    getCircularLetters: '{esbEndPoint}/uoptDetail-circularLetters',
    getFundClassifications: '{esbEndPoint}/uoptDetail-fundClassifications',
    getRiskClasses: '{esbEndPoint}/uoptDetail-riskClasses',
    getRiskProfiles: '{esbEndPoint}/uoptDetail-riskProfiles',
    getRiskCurrencies: '{esbEndPoint}/uoptDetail-riskCurrencies',
    getInvestmentCategories: '{esbEndPoint}/uoptDetail-investmentCategories',
    getAlternativeFunds: '{esbEndPoint}/uoptDetail-alternativeFunds',
    getTypePOAs: '{esbEndPoint}/uoptDetail-typePOAs',
    getClientProfiles:'{esbEndPoint}/liability/uoptDetail/clientProfiles',
    getClientComplianceRisks:'{esbEndPoint}/liability/uoptDetail/clientComplianceRisks',
    getClientActivitySectors: '{esbEndPoint}/liability/uoptDetail/clientActivitySectors',
    getClientProfessions:'{esbEndPoint}/liability/uoptDetail/clientProfessions',    
    getEntityType:'{esbEndPoint}/liability/uoptDetail/entityType',    
    getTypeOfAgentContact: '{esbEndPoint}/liability/uoptDetail/typeOfAgentContact',
    getCrsStatus: '{esbEndPoint}/liability/uoptDetail/crsStatus',
    getCrsExactStatus: '{esbEndPoint}/liability/uoptDetail/crsExactStatus',
    getTypeOfControl: '{esbEndPoint}/liability/uoptDetail/typeOfControl',
    getAgentTitle: '{esbEndPoint}/liability/uoptDetail/agentTitle',
    getDeathCauses: '{esbEndPoint}/liability/uoptDetail/deathCauses',
    getDetailForKeyValue: '{esbEndPoint}/liability/uoptDetail/forKeyValue?keyValue={0}',

    // option details
    getMaritalStatus:'{esbEndPoint}/liability/optionDetail/maritalStatus',
    getLanguages:'{esbEndPoint}/liability/optionDetail/languages',
    getAllLives: '{esbEndPoint}/liability/optionDetail/lives',
    getLives: '{esbEndPoint}/liability/optionDetail/lives/{0}',     // {productCd}    
    getCPRRoles: '{webiaEndPoint}/optionDetail/cpr-roles',
    getClientRolesByProduct: '{webiaEndPoint}/optionDetail/cpr-roles/product?id={0}&productCapi={1}&yearTerm={2}',
    getPricingFrequencies: '{esbEndPoint}/liability/optionDetail/pricingFrequencies',
    getCtxPricingFrequencies: '{webiaEndPoint}/optionDetail/ctxPricingFrequencies',
    getPaymentModes: '{esbEndPoint}/liability/optionDetail/paymentModes',
    getAccountStatus: '{esbEndPoint}/liability/optionDetail/accountStatus',
    
    //ExchangeRate
    getAmountInCurrency: '{esbEndPoint}/liability/exchangeRate/convert?amount={0}&fromCurrency={1}&toCurrency={2}&date={3}',

    // agent
    createAgent: '{webiaEndPoint}/agent/create',
    updateAgent: '{webiaEndPoint}/agent/update',
    searchAgent: '{webiaEndPoint}/agent/search',
    searchFinAdvisor: '{esbEndPoint}/liability/agent/searchFinAdvisor',
    getAgent: '{webiaEndPoint}/agent/{0}',              // {agtId}
    getAgentLite: '{esbEndPoint}/liability/agent/lite/one/{0}',    
    getAgentFoyer: '{webiaEndPoint}/agent/wealinsBroker',
    getWealinsAssetManager: '{webiaEndPoint}/agent/wealinsAssetManager', 
    getAllAgentCategories: '{esbEndPoint}/liability/agentCategory',
    getSubBroker : '{esbEndPoint}/liability/agent/subBroker/{0}',  //{agtId}
    getCommunicationAgents : '{esbEndPoint}/liability/agent/communicationAgents?brokerId={0}&subBrokerId={1}&policyId={2}',
    searchAgentHierarchy:'{esbEndPoint}/liability/agentHierarchy/search',
    findAgentHierarchyByCriteria:'{esbEndPoint}/liability/agentHierarchy/findByCriteria',
    addAgentHierarchy: '{esbEndPoint}/liability/agent/addAgh',
    addAgentContact: '{esbEndPoint}/liability/agent/addAco',
    addAgentBankAccount: '{esbEndPoint}/liability/agent/addAgb',
    findByCriteria: '{esbEndPoint}/liability/agent/findByCriteria',
    findAgentContact:'{webiaEndPoint}/agentContact/find?id={0}',
    saveAgentContact:'{webiaEndPoint}/agentContact/save',
    getAssetManagerStrategies: '{esbEndPoint}/liability/assetManagerStrategy/assetManager/{0}',
    saveStrategy: '{webiaEndPoint}/assetManagerStrategy/save',
    searchAccountRootPatternExample : '{esbEndPoint}/liability/agent/accountRootPatternExample?depositBank={0}', // Search for an account root pattern for the given deposit bank

    // fundService
    loadFund: '{webiaEndPoint}/fund/one?id={0}',           // {fundId}
    updateFund: '{webiaEndPoint}/fund/update',
    createFund: '{webiaEndPoint}/fund/create',
    searchFund: '{esbEndPoint}/liability/fund/search',
    advanceSearch: '{esbEndPoint}/liability/fund/advanceSearch',
    searchFundValuation: '{esbEndPoint}/liability/fund/valuation',
    searchOperationEntry: '{esbEndPoint}/webia/appFormEntry/?fundId={0}&clientId={1}&page={2}&size={3}&excludedPolicies={4}&status={5}',
    getOperationForPartner: '{webiaEndPoint}/operation/forPartner?partnerId={0}&partnerCategory={1}',
    getOperationForPolicy: '{webiaEndPoint}/operation/forPolicy?policyId={0}',
    searchLastFundPricesBefore: '{esbEndPoint}/liability/fundPrice/search',
    validateFund : '{esbEndPoint}/liability/fund/validateFund',
    validateFunds : '{esbEndPoint}/liability/fund/validateFunds',
    canAddFIDorFASFundValuationAmount : '{esbEndPoint}/liability/fund/canAddFIDorFASFundValuationAmount?fdsId={0}&valuationDate={1}&priceType={2}',

    // step workflow service
    loadWorkflowStep: '{webiaEndPoint}/step/?workflowItemId={0}',     //  {workflowItemId}
    initBeneficiaryChangeForm: '{webiaEndPoint}/liabilityBeneficiaryChangeForm/initBeneficiaryChange?policyId={0}&workflowItemId={1}',
    workflowSimulation: '{webiaEndPoint}/simulation/workflowDocument?workflowItemId={0}&documentType={1}',
    existingDocument: '{webiaEndPoint}/simulation/existingDocument?editingRequestId={0}',
    simulationValidation: '{webiaEndPoint}/simulationValidation?workflowItemId={0}&documentType={1}',
    createEnoughCashEditings: '{webiaEndPoint}/withdrawal/createEnoughCashEditings?workflowItemId={0}',
    createFaxSurrenderDepositBankEditings: '{webiaEndPoint}/surrender/createFaxDepositBankEditings?workflowItemId={0}',
    initAdditionalPremiumForm: '{webiaEndPoint}/additionalPremium/initAdditionalPremium?policyId={0}&workflowItemId={1}',
    initBrokerChangeForm: '{webiaEndPoint}/brokerChangeForm/init?policyId={0}&workflowItemId={1}',
    initWithdrawalForm: '{webiaEndPoint}/withdrawal/init?policyId={0}&workflowItemId={1}',
    initSurrenderForm:  '{webiaEndPoint}/surrender/init?policyId={0}&workflowItemId={1}',
    getSurrenderDetails:  '{webiaEndPoint}/surrender/details?workflowItemId={0}',
    recreateAdditionalPremium: '{webiaEndPoint}/additionalPremium/recreateAdditionalPremium?workflowItemId={0}',
    canGenerateAcceptanceDocument: '{webiaEndPoint}/step/canGenerateAcceptanceDocument?workflowItemId={0}',
    
    // step service
    loadStep: '{webiaEndPoint}/step/?workflowItemId={0}&stepWorkflow={1}&userToken={2}',                        //  {workflowItemId}/{stepName}
    loadStepsByWorkflowItemTypeId: '{webiaEndPoint}/step/stepsByWorkflowItemTypeId?workflowItemTypeId={0}',     //  {workflowItemTypeId}
    loadCheckStep: '{webiaEndPoint}/checkSteps?workflowItemId={0}&stepWorkflow={1}',                       //  {workflowItemId}/{stepName}
    //getCommentsHistory: '{esbEndPoint}/webia/checkStep/commentsHistory?workflowItemId={0}&workflowItemTypeId={1}',
    updateStep: '{webiaEndPoint}/step/update',
    completeStep: '{webiaEndPoint}/step/complete',
    previousStep: '{webiaEndPoint}/step/previous',
    abortStep: '{webiaEndPoint}/step/abort',
    getRejectData: '{esbEndPoint}/webia/rejectData/load?workflowItemId={0}',
    getRejectDataByStep: '{esbEndPoint}/webia/rejectData/loadByStepId?workflowItemId={0}&stepId={1}',
    solveActivation: '{webiaEndPoint}/clientRoleActivation/solveActivation/countryCode?countryCode={0}',
    solvePolicyHolderRoleActivation: '{webiaEndPoint}/clientRoleActivation/phActivation/countryCode?countryCode={0}&productCapi={1}&yearTerm={2}',
    recreatePolicy: '{webiaEndPoint}/subscription/recreate',
    getStepComments: '{webiaEndPoint}/stepComment/workflowItemId/{0}',  // {workflowItemId}  
    addStepComment: '{webiaEndPoint}/stepComment/add',

    //Workflow
    getWorkflow: '{webiaEndPoint}/workflow/{0}/actions',   // {workflowItemId}
    getWorkflowUsers : '{webiaEndPoint}/workflowGroup/cps',
    getWorkflowUserByUserId : '{esbEndPoint}/webia/workflowUser/{0}',    
    getWorkflowUserWithToken : '{esbEndPoint}/webia/workflowUser/token/{0}',
    getWorkFlowStatus : '{webiaEndPoint}/workflow/generalInformation/{0}',

    // Client   
    getClient: '{esbEndPoint}/liability/client/{0}',        //  {cliId}
    getClientLight: '{esbEndPoint}/liability/client/lite?id={0}',        //  {cliId}    
    matchClient:'{esbEndPoint}/liability/client/match',
    searchClient: '{esbEndPoint}/liability/client/search',
    create:'{esbEndPoint}/liability/client/create',
    update:'{esbEndPoint}/liability/client/update',
    canClientDeathBeNotified:'{esbEndPoint}/liability/client/canClientDeathBeNotified?id={0}',  //  {cliId}    
    getClientClaimsDetail: '{esbEndPoint}/liability/clientClaimsDetail/{0}',   //    {{cliId}}
    saveClientClaimsDetail: '{esbEndPoint}/liability/clientClaimsDetail/save',
    notifyDeath: '{esbEndPoint}/liability/clientClaimsDetail/notifyDeath',
    getClientRoles: '{esbEndPoint}/liability/clientLinkedPerson/{0}',   // {cliId}

    // Policy
    getPolicy:'{esbEndPoint}/liability/policy?id={0}',  // {polI}
    getPolicyLight:'{esbEndPoint}/liability/policy/policyLight?id={0}',  // {polI}
    searchPolicies:'{esbEndPoint}/liability/policy/search',  // {polI}
    getRolesByPolicies:'{webiaEndPoint}/policy/rolesByPolicies/{0}',  // {CliId}  => get policies with its roles for a client Id
    getPolicyValuation:'{esbEndPoint}/liability/policy/valuation?id={0}&currency={1}&date={2}',  // {polI}, {currency like 'EUR'},{date yyyy-MM-dd} 
    getPolicyClauses: '{webiaEndPoint}/policy/clauses?id={0}&productCd={1}&lang={2}', // // {polId}, {productCd}, {lang}
    isPolicyExist: '{esbEndPoint}/liability/policy/exist?id={0}', // // {polId}
    getBrokerPolicies:'{esbEndPoint}/liability/policy/brokerPolicies', 
    getBrokerValuation :'{esbEndPoint}/liability/policy/brokerValuation?id={0}&currency={1}',  // {agtId}, {currency like 'EUR'}
    getPoliciesTransactions : '{webiaEndPoint}/policy/transactions?policyId={0}',
    getDeceasedInsureds : '{esbEndPoint}/liability/policy/deceasedInsureds?policyId={0}',
    getDeceasedHolders : '{esbEndPoint}/liability/policy/deceasedHolders?policyId={0}',

    // Transfer
    initFormDataForWithdrawal: '{webiaEndPoint}/transfer/withdrawal/initFormData?workflowItemId={0}',
    initFormDataForSurrender: '{webiaEndPoint}/transfer/surrender/initFormData?workflowItemId={0}',
    getPayments: '{webiaEndPoint}/transfer/compta',
    validatePayment: '{webiaEndPoint}/transfer/{0}/accept',
    refusePayment: '{webiaEndPoint}/transfer/{0}/refuse',
    executeSepa: '{webiaEndPoint}/transfer/executeSepa',
    executeFax: '{webiaEndPoint}/transfer/executeFax',
    executeCsv:'{webiaEndPoint}/transfer/executeCsv',

    //Fees
    getEntryFees:'{esbEndPoint}/liability/productValue/policyFee?policyId={0}',  // {policyId}

    //Transactions
    getTransactionsByPolicy : '{esbEndPoint}/liability/transaction/policyTransactionsHistory?id={0}',  // {polI}
    getPolicyTransfers: '{esbEndPoint}/liability/policytransfer?policy={0}', // {polI}
    getPolicyTransactionsDetails : '{esbEndPoint}/liability/transaction/policyTransactionsDetails',  // 
   
    //events
    getPolicyChanges : '{esbEndPoint}/liability/policyChange/all?policyId={0}',  // {polI}
    getBeneficiaryChange : '{webiaEndPoint}/liabilityBeneficiaryChangeForm/getBeneficiaryChange?policyId={0}&workflowItemId={1}&productCd={2}&lang={3}',  
    getBrokerChange : '{esbEndPoint}/liability/brokerChange/load?workflowItemId={0}',
    getBrokerChangeBefore : '{esbEndPoint}/liability/brokerChange/loadBefore?workflowItemId={0}',
    
    //configuration
    getUserProfile: '{esbEndPoint}/getUserContext',

    // editing
    createEditingRequest: '{esbEndPoint}/editing/createRequest',
    applyManagementMandateDocRequest: '{webiaEndPoint}/wiaEditing/mgtMandateRequest',
    acceptanceReportRequest: '{webiaEndPoint}/wiaEditing/acceptanceReportRequest',
    souscriptionFollowUpRequest:'{esbEndPoint}/generateSouscriptionFollowUp',
    additionalPremiumDocumentationRequest: '{webiaEndPoint}/additionalPremium/generateDocumentation?workflowItemId={0}',
    brokerChangeDocumentationRequest: '{webiaEndPoint}/brokerChange/generateDocumentation?workflowItemId={0}',
    surrenderReportRequest: '{esbEndPoint}/generateSurrender',
	workflowDocumentRequest: '{webiaEndPoint}/wiaEditing/workflowDocument?workflowItemId={0}&documentType={1}',
    documentGeneration : '{webiaEndPoint}/documentGeneration/generate',
	
    // Commission
    reconciliationSearch: '{webiaEndPoint}/commission/reconciliation/search?type={0}&text={1}&page={2}&size={3}',
    reconciliationSearchGroup: '{webiaEndPoint}/commission/reconciliation/searchGroup?type={0}&text={1}&page={2}&size={3}',
    validateReconciled: '{webiaEndPoint}/commission/reconciliation/validate/reconciled',
    doneValidated: '{webiaEndPoint}/commission/reconciliation/done/validated',
    generateStatementComJob: '{webiaEndPoint}/documentCommission/generateStatementCom'    
}

@Injectable()
export class ApiService {

    constructor(private config: ConfigService) {}

    getURL(apiName, ...args) {
        let esbEndPoint = this.config.getProps('esbEndPoint');
        let webiaEndPoint = this.config.getProps('webiaEndPoint') || "/webia-application/rest";
        let URL = API[apiName];
        if (!URL) {
            console.error("ERROR: No URL pattern found for " + apiName + " !!");
            return null;
        }
        URL = URL.replace("{esbEndPoint}", esbEndPoint);
        URL = URL.replace("{webiaEndPoint}", webiaEndPoint);

        args.forEach((val, i) => {
            if(val == null) {
                let arg = this.getArgumentName(URL, i);
               // console.warn("argument " + arg + " is null.");
                URL = URL.replace('&' + arg + "={" + i + "}","");
                URL = URL.replace(arg + "={" + i + "}","");

            } else if(Array.isArray(val) && val.length > 0) {
                let arg = this.getArgumentName(URL, i);

                let temp = '';
                val.forEach(e => temp = temp + '&' + arg + '=' + e);
                URL = URL.replace('&' + arg + "={" + i + "}", temp);
                URL = URL.replace(arg + "={" + i + "}", temp);
            } else {    
                if(val == 0){
                    URL = URL.replace("{" + i + "}", val); 
                }  else{
                    URL = URL.replace("{" + i + "}", val||''); 
            }
              
            }
        });
        
        return URL;
    }

    getArgumentName(url, position) {
        var allArgs = url.split('?').pop();
        var argsWithValue = allArgs.split('&');
    
        var argWithValue = argsWithValue.find(function(element) {
            return element.includes("{" + position + "}");
        });

        return argWithValue.split('=')[0];
    }

}