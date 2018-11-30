export interface Pricing {
    navEntryFee?: number;
    navExitFee?: number;
    fwdPriceReportDays?: number;
    pricingDelay?: number;
    pricingDay?: number;
    pricingFrequency?: number;
    pricingDayOfMonth?:number;

    // if something else
    [others: string]: any;
}