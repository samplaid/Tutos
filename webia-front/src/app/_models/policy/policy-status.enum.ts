export class PolicyStatusName {

    public static readonly EMPTY= <any>"";
    public static readonly ACTIVE_SCHEME= <any>"Active (Scheme)";
    public static readonly APPLICATION= <any>"Application";
    public static readonly AVAILABLE= <any>"Available";
    public static readonly C_I_ADMITTED= <any>"C.I. Admitted";
    public static readonly C_I_SETTLEMENT= <any>"C.I. Settlement";
    public static readonly CANCELLED_FROM_INCEPTION= <any>"Cancelled (from inception)";
    public static readonly CANCELLED_NB_ERROR= <any>"Cancelled: N.B. Error";
    public static readonly CLAIM_SETTLEMENT= <any>"Claim Settlement";
    public static readonly DEATH_NOTIFIED= <any>"Death Notified";
    public static readonly DEATH_SETTLEMENT= <any>"Death Settlement";
    public static readonly DECLINED_BY_COMPANY= <any>"Declined (by Company)";
    public static readonly DEFERRED_COMMENCEMENT= <any>"Deferred Commencement";
    public static readonly DEFERRED_MATURITY= <any>"Deferred Maturity";
    public static readonly EXPIRY= <any>"Expiry";
    public static readonly INFORCE= <any>"InForce";
    public static readonly INFORCE_AWAITING_PAYMENT_COLLECTION= <any>"Inforce awaiting payment collection";
    public static readonly LAPSED= <any>"Lapsed";
    public static readonly LAPSED_NO_REINSTATEMENT= <any>"Lapsed (No Reinstatement)";
    public static readonly MATURED= <any>"Matured";
    public static readonly NEW_BUSINESS_ENTRY= <any>"New Business Entry";
    public static readonly NEW_BUSINESS_RECALC= <any>"New Business Recalc";
    public static readonly NOT_PROCEEDED_WITH= <any>"Not Proceeded with";
    public static readonly NOT_PROCEEDED_WITH_BY_CLIENT= <any>"Not Proceeded with (by Client)";
    public static readonly NOT_TAKEN_UP= <any>"Not Taken up";
    public static readonly P_H_I_NOTIFICATION= <any>"P.H.I. Notification";
    public static readonly P_H_I_SETTLEMENT= <any>"P.H.I. Settlement";
    public static readonly P_T_D_ADMITTED= <any>"P.T.D. Admitted";
    public static readonly P_T_D_SETTLEMENT= <any>"P.T.D. Settlement";
    public static readonly PAID_UP= <any>"Paid Up";
    public static readonly PAID_UP_ZERO_SA= <any>"Paid Up (Zero SA)";
    public static readonly PENDING= <any>"Pending";
    public static readonly PENDING_CFI= <any>"Pending CFI";
    public static readonly PENDING_EXPIRY= <any>"Pending Expiry";
    public static readonly PENDING_MATURITY= <any>"Pending Maturity";
    public static readonly PENDING_NOT_TAKEN_UP= <any>"Pending Not Taken up";
    public static readonly PENDING_SURRENDER= <any>"Pending Surrender";
    public static readonly PENDING_TERMINATED= <any>"Pending Terminated";
    public static readonly PENDING_TERMINATION= <any>"Pending Termination";
    public static readonly PENDING_PAYMENT_REQUESTED= <any>"Pending, Payment Requested";
    public static readonly PREMIUM_HOLIDAY= <any>"Premium Holiday";
    public static readonly SURRENDERED= <any>"Surrendered";
    public static readonly SUSPENDED= <any>"Suspended";
    public static readonly TERMINATED= <any>"Terminated";
    public static readonly TERMINATION_AFTER_TRANSFER_OUT= <any>"Termination after Transfer out";
    public static readonly WAIVER_CLAIM= <any>"Waiver Claim";
    public static readonly WAIVER_NOTICE= <any>"Waiver Notice";

    public static ACTIVE_GROUP = [PolicyStatusName.EMPTY, PolicyStatusName.INFORCE, PolicyStatusName.PAID_UP, PolicyStatusName.LAPSED]; 

    public static PENDING_GROUP = [PolicyStatusName.PENDING,PolicyStatusName.NEW_BUSINESS_ENTRY]; 

    public static INACTIVE_GROUP = [PolicyStatusName.SURRENDERED, 
                                    PolicyStatusName.MATURED, 
                                    PolicyStatusName.CANCELLED_FROM_INCEPTION, 
                                    PolicyStatusName.NOT_TAKEN_UP,
                                    PolicyStatusName.DEATH_SETTLEMENT,
                                    PolicyStatusName.DEATH_NOTIFIED,
                                    PolicyStatusName.NOT_PROCEEDED_WITH,
                                    PolicyStatusName.CANCELLED_NB_ERROR,
                                    PolicyStatusName.DECLINED_BY_COMPANY
                                    ]; 


    public static hasStatus(status:string, expeted:PolicyStatusName):Boolean{
        if (Array.isArray(expeted )) {
            return (expeted.indexOf(status) >-1);
        }
        return (status==expeted);
    } 
}

