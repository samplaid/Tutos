/**
 * Enumerates the fund type
 */
export class FundType {
    public static readonly FID: FundType = new FundType('FID', 'FID');
    public static readonly FAS: FundType = new FundType('FAS', 'FAS');
    public static readonly FE: FundType = new FundType('FE', 'External funds');
    public static readonly FIC: FundType = new FundType('FIC', 'FIC');

    private static readonly _LIST: FundType[] = [FundType.FID, FundType.FAS, FundType.FE, FundType.FIC];

    private constructor(public key: string, public value: string) { }

    static get enums(): FundType[] {
        return FundType._LIST;
    }

    static get values(): string[] {
        return this._LIST.map(fundType => fundType.value);
    }

    static get keys(): string[] {
        return this._LIST.map(fundType => fundType.key);
    }

    static valueOf(key: string): string {
        return FundType.enums.find(fundType => { return fundType.key == key }).value;
    }

    compareTo(other: FundType): number {
        if (!other)
            return -1;
        return this.value.localeCompare(other.value)
    }

}