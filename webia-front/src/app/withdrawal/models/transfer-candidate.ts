export interface TransferCandidate {
    fundId: string;
    fundName: string;
    fundSubType: string;
    libDonOrd: string;
    bic: string;
    iban: string;
    currency?: string;
}
