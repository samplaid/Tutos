
export class StepName {
    public static readonly Registration = <any>'Registration';
    public static readonly Waiting_Dispatch = <any>'Waiting Dispatch';
    public static readonly New_Business  = <any>'New';
    public static readonly Analysis = <any>'Analysis';
    public static readonly Unblocked_Analysis = <any>'Unblocked Analysis';
    public static readonly Unblocked_Validate_Analysis = <any>'Unblocked Validate Analysis';
    public static readonly Unblocked_Acceptance = <any>'Unblocked Acceptance';
    public static readonly Unblocked_Request_To_Client_Partner = <any>'Unblocked Request to Client/Partner';
    public static readonly Scanning = <any>'';
    public static readonly Check_Registration = <any>'Check Registration';
    public static readonly Validate_Analysis = <any>'Validate Analysis';
    public static readonly Complete_Anaylsis = <any>'Complete Analysis';    
    public static readonly Medical_Acceptance = <any>'Medical Acceptance';
    public static readonly Check_Portfolio = <any>'Check Portfolio';
    public static readonly Acceptance = <any>'Acceptance';
    public static readonly Acceptance_Bis = <any>'Acceptance Bis';
    public static readonly Request_To_Client_Partner = <any>'Request to Client/Partner';
    public static readonly Account_Opening_Request = <any>'Account Opening Request';
    public static readonly Awaiting_Account_Opening = <any>'Awaiting Account Opening';
    public static readonly Premium_Transfer_Request = <any>'Premium Transfer Request';
    public static readonly Validate_Input = <any>'Validate Input';
    public static readonly Validate_Bank_Instructions = <any>'Validate Bank Instructions';    
    public static readonly Premium_input_and_nav = <any>'Premium Input and NAV';
    public static readonly Awaiting_Premium = <any>'Awaiting Premium';
    public static readonly Generate_Mandat_de_Gestion = <any>'Generate Mandat de Gestion';
    public static readonly Acceptation_premium = <any>'Acceptation Premium';
    public static readonly Validate_premium_and_nav = <any>'Validate Premium and NAV';
    public static readonly Awaiting_put_in_force = <any>'Awaiting Put in Force';
    public static readonly Awaiting_valuation = <any>'Awaiting Valuation';
    public static readonly Generate_documentation = <any>'Generate Documentation';
    public static readonly Check_documentation = <any>'Check Documentation';
    public static readonly Regularisation_conditional_approval = <any>'Regularisation Conditional approval';
    public static readonly Validate_documentation = <any>'Validate Documentation';
    public static readonly Sending = <any>'Sending';
    public static readonly Follow_up = <any>'Follow-up';
    public static readonly Update_Input = <any>'Update Input';    

    /** For Beneficiary Change workflow */
    public static readonly Validate_Analysis_and_Input = 'Validate Analysis and Input';
    public static readonly Awaiting_Activation = 'Awaiting Activation';
    public static readonly Validate_Additional_Premium = 'Validate Additional Premium'
    public static readonly Review_Documentation = 'Review Documentation'

    /** For Additional Premium workflow */
    public static readonly New = 'New';
    public static readonly Generate_Management_Mandate = 'Generate Management Mandate';

    /** For Broker Change workflow */
    public static readonly Validate_Broker_Change = 'Validate Broker Change';
    public static readonly Awaiting_Activation_Fees = <any>'Awaiting Activation Fees';

    /** For Withdrawal */
    public static readonly Awaiting_Cash_Transfer = 'Awaiting Cash/Transfer';    
    public static readonly Generate_Documentation_Payment = 'Generate Documentation Payment';
    public static readonly Awaiting_Transfer = 'Awaiting Transfer';
    public static readonly Check_Documentation_And_Input_Payment = 'Check Documentation And Input Payment';
    public static readonly Validate_Documentation_And_Payment = 'Validate Documentation And Payment';

    /* For Surrender */
    public static readonly Awaiting_Cash_Transfer_fees = 'Awaiting Cash/Transfer/Fees';
    public static readonly Awaiting_Cash_Wealins_Account = 'Awaiting Cash Wealins Account/NAV and date';
    public static readonly Check_Documentation_And_Payment = 'Check Documentation and Payment';    
}

abstract class OrderedStep {

    constructor(public ordinal: number, public stepName: string) {}

    abstract get values(): OrderedStep[];

    /**
     * Returns true if the current step is strictly before the step passed in parameter.
     * @param step the step to compare
     */
    isBefore(step: OrderedStep): boolean {     
        if(!step)   {
            throw 'StepOrder.isBefore() :Step must not be null';            
        }
        return this.ordinal < step.ordinal;
    }

    /**
     * Returns true if the current step is strictly after the step passed in parameter.
     * @param step the step to compare
     */
    isAfter(step: OrderedStep): boolean {     
        if(!step)   {
            throw 'StepOrder.isAfter() :Step must not be null';            
        }
        return this.ordinal > step.ordinal;
    }

    /**
     * Returns true if the current step is before or equal to the step passed in parameter.
     * @param step the step to compare
     */
    isBeforeOrEqual(step: OrderedStep): boolean { 
        return this.isBefore(step) || this.ordinal === step.ordinal;
    }

    /**
     * Returns true if the current step is after or equal to the step passed in parameter.
     * @param step the step to compare
     */
    isAfterOrEqual(step: OrderedStep): boolean { 
        return this.isAfter(step) || this.ordinal === step.ordinal;
    }
}

/** Define the steps and their order for the beneficiary change workflow. */
export class BeneficiaryChangeStep extends OrderedStep {

    constructor(public ordinal: number, public stepName: string){
        super(ordinal, stepName);
    }

    private static order = 0;

    public static Analysis                      = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Analysis);
    public static Validate_Analysis_and_Input   = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Validate_Analysis_and_Input);
    public static Acceptance                    = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Acceptance);
    public static Request_To_Client_Partner     = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Request_To_Client_Partner);
    public static Acceptance_Bis                = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Acceptance_Bis);
    public static Awaiting_Activation           = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Awaiting_Activation);
    public static Generate_Documentation        = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Generate_documentation);   
    public static Check_Documentation           = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Check_documentation);
    public static Sending                       = new BeneficiaryChangeStep(BeneficiaryChangeStep.order++, StepName.Sending);    

    private static readonly STEPS = [BeneficiaryChangeStep.Analysis, BeneficiaryChangeStep.Validate_Analysis_and_Input, BeneficiaryChangeStep.Acceptance,
                                    BeneficiaryChangeStep.Request_To_Client_Partner, BeneficiaryChangeStep.Acceptance_Bis, BeneficiaryChangeStep.Awaiting_Activation,
                                    BeneficiaryChangeStep.Generate_Documentation, BeneficiaryChangeStep.Check_Documentation, BeneficiaryChangeStep.Sending ];

    get values(): BeneficiaryChangeStep[] {
        return BeneficiaryChangeStep.STEPS;
    }

    static toOrderedStep(stepName: string): BeneficiaryChangeStep {  
        let result: BeneficiaryChangeStep = null;

        for (let index = 0; index < this.STEPS.length; index++) {
            if((this.STEPS[index].stepName === stepName)) {
                result = this.STEPS[index];
                break;
            }            
        }

        return result;
    }
}

/** Define the steps and their order for the Additional Premium workflow. */
export class AdditionalPremiumStep extends OrderedStep {

    constructor(public ordinal: number, public stepName: string, public canRecreate: boolean){
        super(ordinal, stepName);
    }

    private static order = 0;

    public static New                           = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.New, false);
    public static Registration                  = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Registration, false);
    public static Waiting_Dispatch              = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Waiting_Dispatch, false);
    public static Analysis                      = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Analysis, false);
    public static Validate_Analysis             = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Validate_Analysis, false);
    public static Acceptance                    = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Acceptance, false);
    public static Request_To_Client_Partner     = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Request_To_Client_Partner, true);
    public static Acceptance_Bis                = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Acceptance_Bis, false);
    public static Account_Opening_Request       = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Account_Opening_Request, false);
    public static Awaiting_Account_Opening      = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Awaiting_Account_Opening, false);
    public static Premium_Transfer_Request      = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Premium_Transfer_Request, false);
    public static Generate_Management_Mandate   = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Generate_Management_Mandate, false);
    public static Awaiting_Premium              = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Awaiting_Premium, false);
    public static Premium_input_and_nav         = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Premium_input_and_nav, false);
    public static Validate_Additional_Premium   = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Validate_Additional_Premium, false);
    public static Awaiting_valuation            = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Awaiting_valuation, false);
    public static Review_Documentation          = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Review_Documentation, false);
    public static Generate_documentation        = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Generate_documentation, false);
    public static Check_documentation           = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Check_documentation, true);
    public static Validate_documentation        = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Validate_documentation, false);
    public static Sending                       = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Sending, false);
    public static Follow_up                     = new AdditionalPremiumStep(AdditionalPremiumStep.order++, StepName.Follow_up, false);

    private static readonly STEPS = [AdditionalPremiumStep.New, AdditionalPremiumStep.Registration, AdditionalPremiumStep.Waiting_Dispatch, AdditionalPremiumStep.Analysis,
                                    AdditionalPremiumStep.Validate_Analysis, AdditionalPremiumStep.Acceptance, AdditionalPremiumStep.Request_To_Client_Partner,
                                    AdditionalPremiumStep.Acceptance_Bis, AdditionalPremiumStep.Account_Opening_Request, AdditionalPremiumStep.Awaiting_Account_Opening,
                                    AdditionalPremiumStep.Premium_Transfer_Request, AdditionalPremiumStep.Generate_Management_Mandate, AdditionalPremiumStep.Awaiting_Premium,
                                    AdditionalPremiumStep.Premium_input_and_nav, AdditionalPremiumStep.Validate_Additional_Premium, AdditionalPremiumStep.Awaiting_valuation,
                                    AdditionalPremiumStep.Review_Documentation, AdditionalPremiumStep.Generate_documentation, AdditionalPremiumStep.Check_documentation,
                                    AdditionalPremiumStep.Validate_documentation, AdditionalPremiumStep.Sending, AdditionalPremiumStep.Follow_up];

    get values(): AdditionalPremiumStep[] {
        return AdditionalPremiumStep.STEPS;
    }

    static toOrderedStep(stepName: string): AdditionalPremiumStep {  
        let result: AdditionalPremiumStep = null;

        for (let index = 0; index < this.STEPS.length; index++) {
            if((this.STEPS[index].stepName === stepName)) {
                result = this.STEPS[index];
                break;
            }            
        }

        return result;
    }

    isRecreatable(): boolean {
        return this.canRecreate;
    }
}

/**
 * ATTENTION: The order of the enum is important !!!
 */
export class SubscriptionStep extends OrderedStep {
    private static order = 0;

    public static Registration =  new SubscriptionStep(SubscriptionStep.order++, StepName.Registration, false);
    public static Waiting_Dispatch =  new SubscriptionStep(SubscriptionStep.order++, StepName.Waiting_Dispatch, false);
    public static New_Business =  new SubscriptionStep(SubscriptionStep.order++, StepName.New_Business, false);
    public static Unblocked_Analysis =  new SubscriptionStep(SubscriptionStep.order++, StepName.Unblocked_Analysis, false);
    public static Unblocked_Validate_Analysis =  new SubscriptionStep(SubscriptionStep.order++, StepName.Unblocked_Validate_Analysis, false);
    public static Unblocked_Acceptance =  new SubscriptionStep(SubscriptionStep.order++, StepName.Unblocked_Acceptance, false);
    public static Unblocked_Request_To_Client_Partner =  new SubscriptionStep(SubscriptionStep.order++, StepName.Unblocked_Request_To_Client_Partner, false);
    public static Analysis =  new SubscriptionStep(SubscriptionStep.order++, StepName.Analysis, false);;
    public static Scanning =  new SubscriptionStep(SubscriptionStep.order++, StepName.Scanning, false);
    public static Check_Registration =  new SubscriptionStep(SubscriptionStep.order++, StepName.Check_Registration, false);
    public static Validate_Analysis =  new SubscriptionStep(SubscriptionStep.order++, StepName.Validate_Analysis, false);
    public static Medical_Acceptance =  new SubscriptionStep(SubscriptionStep.order++, StepName.Medical_Acceptance, false);
    public static Check_Portfolio =  new SubscriptionStep(SubscriptionStep.order++, StepName.Check_Portfolio, false);
    public static Acceptance =  new SubscriptionStep(SubscriptionStep.order++, StepName.Acceptance, false);
    public static Acceptance_Bis =  new SubscriptionStep(SubscriptionStep.order++, StepName.Acceptance_Bis, false);
    public static Request_To_Client_Partner =  new SubscriptionStep(SubscriptionStep.order++, StepName.Request_To_Client_Partner, false);
    public static Account_Opening_Request =  new SubscriptionStep(SubscriptionStep.order++, StepName.Account_Opening_Request, false);
    public static Awaiting_Account_Opening =  new SubscriptionStep(SubscriptionStep.order++, StepName.Awaiting_Account_Opening, false);
    public static Premium_Transfer_Request =  new SubscriptionStep(SubscriptionStep.order++, StepName.Premium_Transfer_Request, false);
    public static Validate_Input =  new SubscriptionStep(SubscriptionStep.order++, StepName.Validate_Input, false);
    public static Awaiting_Premium =  new SubscriptionStep(SubscriptionStep.order++, StepName.Awaiting_Premium, false);
    public static Generate_Mandat_de_Gestion =  new SubscriptionStep(SubscriptionStep.order++, StepName.Generate_Mandat_de_Gestion, false);
    public static Acceptation_premium =  new SubscriptionStep(SubscriptionStep.order++, StepName.Acceptation_premium, false);
    public static Premium_input_and_nav =  new SubscriptionStep(SubscriptionStep.order++, StepName.Premium_input_and_nav, false);  
    public static Validate_premium_and_nav =  new SubscriptionStep(SubscriptionStep.order++, StepName.Validate_premium_and_nav, false);
    public static Awaiting_valuation =  new SubscriptionStep(SubscriptionStep.order++, StepName.Awaiting_valuation, false);
    public static Generate_documentation =  new SubscriptionStep(SubscriptionStep.order++, StepName.Generate_documentation, false);
    public static Check_documentation =  new SubscriptionStep(SubscriptionStep.order++, StepName.Check_documentation, true);
    public static Update_Input =  new SubscriptionStep(SubscriptionStep.order++, StepName.Update_Input, true);
    public static Regularisation_conditional_approval =  new SubscriptionStep(SubscriptionStep.order++, StepName.Regularisation_conditional_approval, false);
    public static Validate_documentation =  new SubscriptionStep(SubscriptionStep.order++, StepName.Validate_documentation, false);
    public static Sending =  new SubscriptionStep(SubscriptionStep.order++, StepName.Sending, true);
    public static Follow_up =  new SubscriptionStep(SubscriptionStep.order++, StepName.Follow_up, false);
    
    private static readonly STEPS = [   SubscriptionStep.Registration,
                                        SubscriptionStep.Waiting_Dispatch,
                                        SubscriptionStep.New_Business,
                                        SubscriptionStep.Unblocked_Analysis,
                                        SubscriptionStep.Unblocked_Validate_Analysis,
                                        SubscriptionStep.Unblocked_Acceptance,
                                        SubscriptionStep.Unblocked_Request_To_Client_Partner,
                                        SubscriptionStep.Analysis,
                                        SubscriptionStep.Scanning,
                                        SubscriptionStep.Check_Registration,
                                        SubscriptionStep.Validate_Analysis,
                                        SubscriptionStep.Medical_Acceptance,
                                        SubscriptionStep.Check_Portfolio,
                                        SubscriptionStep.Acceptance,
                                        SubscriptionStep.Acceptance_Bis,
                                        SubscriptionStep.Request_To_Client_Partner,
                                        SubscriptionStep.Account_Opening_Request,
                                        SubscriptionStep.Awaiting_Account_Opening,
                                        SubscriptionStep.Premium_Transfer_Request,
                                        SubscriptionStep.Validate_Input,
                                        SubscriptionStep.Awaiting_Premium,
                                        SubscriptionStep.Generate_Mandat_de_Gestion,
                                        SubscriptionStep.Acceptation_premium,
                                        SubscriptionStep.Premium_input_and_nav,  
                                        SubscriptionStep.Validate_premium_and_nav,
                                        SubscriptionStep.Awaiting_valuation,
                                        SubscriptionStep.Generate_documentation,
                                        SubscriptionStep.Check_documentation,
                                        SubscriptionStep.Update_Input,
                                        SubscriptionStep.Regularisation_conditional_approval,
                                        SubscriptionStep.Validate_documentation,
                                        SubscriptionStep.Sending,
                                        SubscriptionStep.Follow_up];

    constructor(public ordinal: number, public stepName: string, public canRecreate: boolean){
        super(ordinal, stepName);
    }

    get values(): SubscriptionStep[]{
        return SubscriptionStep.STEPS;
    }

    static toOrderedStep(stepName: string): SubscriptionStep {  
        let result: SubscriptionStep = null;

        for (let index = 0; index < this.STEPS.length; index++) {
            if((this.STEPS[index].stepName === stepName)) {
                result = this.STEPS[index];
                break;
            }            
        }

        return result;
    }

    isRecreatable(): boolean {
        return this.canRecreate;
    }
}

/** Define the steps and their order for the Broker Change workflow. */
export class BrokerChangeStep extends OrderedStep {

    constructor(public ordinal: number, public stepName: string){
        super(ordinal, stepName);
    }

    private static order = 0;

    public static Analysis                      = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Analysis);    
    public static Validate_Broker_Change        = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Validate_Broker_Change);
    public static Acceptance                    = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Acceptance);
    public static Request_To_Client_Partner     = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Request_To_Client_Partner);
    public static Acceptance_Bis                = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Acceptance_Bis);
    public static Awaiting_Activation           = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Awaiting_Activation);
    public static Generate_documentation        = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Generate_documentation);
    public static Check_documentation           = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Check_documentation);
    public static Sending                       = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Sending);
    public static Awaiting_Activation_Fees      = new BrokerChangeStep(BrokerChangeStep.order++, StepName.Awaiting_Activation_Fees);
   
    private static readonly STEPS = [BrokerChangeStep.Analysis, BrokerChangeStep.Validate_Broker_Change, BrokerChangeStep.Acceptance,
                                    BrokerChangeStep.Request_To_Client_Partner, BrokerChangeStep.Acceptance_Bis, BrokerChangeStep.Awaiting_Activation,
                                    BrokerChangeStep.Generate_documentation, BrokerChangeStep.Check_documentation, BrokerChangeStep.Sending, BrokerChangeStep.Awaiting_Activation_Fees];

    get values(): BrokerChangeStep[] {
        return BrokerChangeStep.STEPS;
    }

    static toOrderedStep(stepName: string): BrokerChangeStep {  
        let result: BrokerChangeStep = null;

        for (let index = 0; index < this.STEPS.length; index++) {
            if((this.STEPS[index].stepName === stepName)) {
                result = this.STEPS[index];
                break;
            }            
        }

        return result;
    }
}

/** Define the steps and their order for the Withdrawal workflow. */
export class WithdrawalStep extends OrderedStep {

    constructor(public ordinal: number, public stepName: string){
        super(ordinal, stepName);
    }

    private static order = 0;

    public static New                                   = new WithdrawalStep(WithdrawalStep.order++, StepName.New);
    public static Analysis                              = new WithdrawalStep(WithdrawalStep.order++, StepName.Analysis);
    public static Validate_Analysis                     = new WithdrawalStep(WithdrawalStep.order++, StepName.Validate_Analysis);
    public static Complete_Anaylsis                     = new WithdrawalStep(WithdrawalStep.order++, StepName.Complete_Anaylsis);
    public static Acceptance                            = new WithdrawalStep(WithdrawalStep.order++, StepName.Acceptance);
    public static Request_To_Client_Partner             = new WithdrawalStep(WithdrawalStep.order++, StepName.Request_To_Client_Partner);
    public static Acceptance_Bis                        = new WithdrawalStep(WithdrawalStep.order++, StepName.Acceptance_Bis);
    public static Awaiting_Cash_Transfer                = new WithdrawalStep(WithdrawalStep.order++, StepName.Awaiting_Cash_Transfer);
    public static Awaiting_Transfer                     = new WithdrawalStep(WithdrawalStep.order++, StepName.Awaiting_Transfer);
    public static Validate_Input                        = new WithdrawalStep(WithdrawalStep.order++, StepName.Validate_Input);
    public static Generate_Documentation_Payment        = new WithdrawalStep(WithdrawalStep.order++, StepName.Generate_Documentation_Payment);
    public static Awaiting_put_in_force                 = new WithdrawalStep(WithdrawalStep.order++, StepName.Awaiting_put_in_force);
    public static Awaiting_valuation                    = new WithdrawalStep(WithdrawalStep.order++, StepName.Awaiting_valuation);
    public static Review_Documentation                  = new WithdrawalStep(WithdrawalStep.order++, StepName.Review_Documentation);
    public static Generate_documentation                = new WithdrawalStep(WithdrawalStep.order++, StepName.Generate_documentation);
    public static Check_Documentation_And_Input_Payment = new WithdrawalStep(WithdrawalStep.order++, StepName.Check_Documentation_And_Input_Payment);
    public static Validate_Documentation_And_Payment    = new WithdrawalStep(WithdrawalStep.order++, StepName.Validate_Documentation_And_Payment);
    public static Check_Documentation                   = new WithdrawalStep(WithdrawalStep.order++, StepName.Check_documentation);
    public static Validate_documentation                = new WithdrawalStep(WithdrawalStep.order++, StepName.Validate_documentation);
    public static Sending                               = new WithdrawalStep(WithdrawalStep.order++, StepName.Sending);
    public static Follow_up                             = new WithdrawalStep(WithdrawalStep.order++, StepName.Follow_up);
   
    private static readonly STEPS = [WithdrawalStep.New, WithdrawalStep.Analysis, WithdrawalStep.Validate_Analysis, WithdrawalStep.Complete_Anaylsis, WithdrawalStep.Acceptance,
                                    WithdrawalStep.Request_To_Client_Partner, WithdrawalStep.Acceptance_Bis, WithdrawalStep.Awaiting_Cash_Transfer,
                                    WithdrawalStep.Awaiting_Transfer, WithdrawalStep.Validate_Input, WithdrawalStep.Generate_Documentation_Payment,
                                    WithdrawalStep.Awaiting_put_in_force, WithdrawalStep.Awaiting_valuation, WithdrawalStep.Review_Documentation, WithdrawalStep.Generate_documentation,
                                    WithdrawalStep.Check_Documentation_And_Input_Payment, WithdrawalStep.Check_Documentation,
                                    WithdrawalStep.Validate_documentation, WithdrawalStep.Sending, WithdrawalStep.Follow_up];

    get values(): WithdrawalStep[] {
        return WithdrawalStep.STEPS;
    }

    static toOrderedStep(stepName: string): WithdrawalStep {  
        let result: WithdrawalStep = null;

        for (let index = 0; index < this.STEPS.length; index++) {
            if((this.STEPS[index].stepName === stepName)) {
                result = this.STEPS[index];
                break;
            }            
        }

        return result;
    }
}

//TODO : refactor this enums. toooooo much useless code and duplication.
export class SurrenderStep extends OrderedStep {

    constructor(public ordinal: number, public stepName: string){
        super(ordinal, stepName);
    }

    private static order = 0;

    public static New                                   = new SurrenderStep(SurrenderStep.order++, StepName.New);
    public static Analysis                              = new SurrenderStep(SurrenderStep.order++, StepName.Analysis);
    public static Validate_Analysis                     = new SurrenderStep(SurrenderStep.order++, StepName.Validate_Analysis);    
    public static Acceptance                            = new SurrenderStep(SurrenderStep.order++, StepName.Acceptance);
    public static Request_To_Client_Partner             = new SurrenderStep(SurrenderStep.order++, StepName.Request_To_Client_Partner);
    public static Acceptance_Bis                        = new SurrenderStep(SurrenderStep.order++, StepName.Acceptance_Bis);
    public static Awaiting_Cash_Transfer_fees           = new SurrenderStep(SurrenderStep.order++, StepName.Awaiting_Cash_Transfer_fees);
    public static Validate_Input                        = new SurrenderStep(SurrenderStep.order++, StepName.Validate_Input);
    public static Validate_Bank_Instructions            = new SurrenderStep(SurrenderStep.order++, StepName.Validate_Bank_Instructions);
    public static Awaiting_Cash_Wealins_Account         = new SurrenderStep(SurrenderStep.order++, StepName.Awaiting_Cash_Wealins_Account); 
    public static Awaiting_put_in_force                 = new SurrenderStep(SurrenderStep.order++, StepName.Awaiting_put_in_force);       
    public static Awaiting_valuation                    = new SurrenderStep(SurrenderStep.order++, StepName.Awaiting_valuation);
    public static Review_Documentation                  = new SurrenderStep(SurrenderStep.order++, StepName.Review_Documentation);
    public static Generate_documentation                = new SurrenderStep(SurrenderStep.order++, StepName.Generate_documentation);
    public static Check_Documentation_And_Payment       = new SurrenderStep(SurrenderStep.order++, StepName.Check_Documentation_And_Payment);
    public static Complete_Anaylsis                     = new SurrenderStep(SurrenderStep.order++, StepName.Complete_Anaylsis);
    public static Validate_documentation                = new SurrenderStep(SurrenderStep.order++, StepName.Validate_documentation);
    public static Sending                               = new SurrenderStep(SurrenderStep.order++, StepName.Sending);
   
    private static readonly STEPS = [SurrenderStep.New, SurrenderStep.Analysis, SurrenderStep.Validate_Analysis, SurrenderStep.Acceptance, SurrenderStep.Request_To_Client_Partner,
                                    SurrenderStep.Acceptance_Bis, SurrenderStep.Awaiting_Cash_Transfer_fees, SurrenderStep.Validate_Bank_Instructions, SurrenderStep.Validate_Input,
                                    SurrenderStep.Awaiting_Cash_Wealins_Account, SurrenderStep.Awaiting_put_in_force, SurrenderStep.Awaiting_valuation,
                                    SurrenderStep.Review_Documentation, SurrenderStep.Generate_documentation, SurrenderStep.Check_Documentation_And_Payment, SurrenderStep.Check_Documentation_And_Payment,
                                    SurrenderStep.Complete_Anaylsis, SurrenderStep.Validate_documentation, SurrenderStep.Sending];

    get values(): SurrenderStep[] {
        return SurrenderStep.STEPS;
    }

    static toOrderedStep(stepName: string): SurrenderStep {  
        let result: SurrenderStep = null;

        for (let index = 0; index < this.STEPS.length; index++) {
            if((this.STEPS[index].stepName === stepName)) {
                result = this.STEPS[index];
                break;
            }            
        }

        return result;
    }
}