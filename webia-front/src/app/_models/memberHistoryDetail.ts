export class MemberHistoryDetail {

    mehId: number;
    memId: string;
    dateValidFrom?: Date;
    category?: string;
    salary?: number;

    /// Allow to add other field  dynamically
    [other: string]: any;
}