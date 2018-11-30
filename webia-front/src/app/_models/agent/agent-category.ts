export class AgentCategory {
    public acaId: string;
    public name: string;
    public status: number;
    public registrationType: number;
    public reportCategory: string;

    [other: string]: any;
}

export class AgentCategoryEnum {
    public static readonly SUB_MASTER_AGENT_IMPULS = '1';
    public static readonly SUB_MASTER_AGENT_KRAUSE = '2';
    public static readonly SUB_MASTER_AGENT_OEMKE = '3';
    public static readonly SUB_MASTER_AGENT_METZNER = '4';
    public static readonly SUB_MASTER_AGENT_ABR_WIRT = '5';
    public static readonly SUB_MASTER_AGENT_WILDE = '6';
    public static readonly SUB_MASTER_AGENT_HARTWIG = '7';
    public static readonly SUB_MASTER_AGENT_PM_FINANCE = '8';
    public static readonly SUB_MASTER_AGENT_HIGH_SOCIETY = '9';
    public static readonly SUB_MASTER_AGENT_ALEXANDER_POHLE = '10';
    public static readonly SUB_MASTER_AGENT_ADAMSKI = '11';
    public static readonly SUB_MASTER_AGENT_BITNER = '12';
    public static readonly SUB_MASTER_AGENT_VKM = '13';
    public static readonly SUB_MASTER_AGENT_WERTFINANZ = '14';
    public static readonly SUB_MASTER_AGENT_AKZENTE = '15';
    public static readonly SUB_MASTER_AGENT_ALLGUT = '16';
    public static readonly SUB_MASTER_AGENT_ACIK = '17';
    public static readonly SUB_MASTER_AGENT_THIEMEL = '18';
    public static readonly SUB_MASTER_AGENT_SENKPIEL = '19';
    public static readonly SUB_MASTER_AGENT_ZITZER = '20';
    public static readonly SUB_MASTER_AGENT_MS = '21';
    public static readonly SUB_MASTER_AGENT_ABC = '22';
    public static readonly SUB_MASTER_AGENT_VOLK = '23';
    public static readonly SUB_MASTER_AGENT_SCHWEDLER = '24';
    public static readonly SUB_MASTER_AGENT_FIRST_CLASS_INVEST = '25';
    public static readonly SUB_MASTER_AGENT_BÖTTGER = '26';
    public static readonly SUB_MASTER_AGENT_WASCHKOWSKI = '27';
    public static readonly SUB_MASTER_AGENT_P_P = '28';
    public static readonly SUB_MASTER_AGENT_JONELEIT = '29';
    public static readonly ASSET_MANAGER = 'AM';
    public static readonly BROKER = 'BK';
    public static readonly DEPOSIT_BANK = 'DB';
    public static readonly DISTRIBUTION_COMPANY = 'DC';
    public static readonly FINANCIAL_ADVISOR = 'FA'; // Replaced by PRESTATION_SERVICE_INVESTMT and INDEPENDENT_FINACIAL_INTERMDIARY
    public static readonly PRESTATION_SERVICE_INVESTMT = 'PSI';
    public static readonly INDEPENDENT_FINACIAL_INTERMDIARY = 'IFI';
    public static readonly WEALINS_SALES_PERSON = 'FS';
    public static readonly INTRODUCER = 'IN';
    public static readonly KEY_ACCOUNT_MANAGER = 'KA';
    public static readonly MASTER_AGENT = 'MA';
    public static readonly PERSON_CONTACT = 'PR';
    public static readonly SUB_BROKER = 'SB';
    public static readonly SUB_MASTER_AGENT_BEWAS_OR_BEDNARSKI = 'SM';
    public static readonly AGENTS_FICTIFS_ACQ_AMORT = 'XX';

}