
export class CheckType {
    static yesNo: CheckType = new CheckType('YesNo');
    static yesNoNa: CheckType = new CheckType('YesNoNa');
    static text: CheckType = new CheckType('Text');
    static date: CheckType = new CheckType('Date');
    static number: CheckType = new CheckType('Number');
    static amount: CheckType = new CheckType('Amount');

    private static readonly array: CheckType[] = [ 
        CheckType.yesNo, 
        CheckType.yesNoNa, 
        CheckType.text, 
        CheckType.date, 
        CheckType.number, 
        CheckType.amount, 
    ];

    constructor( private checkTypeValue: string){
    }

    get value(){
        return this.checkTypeValue;
    }

    static get values(){
        return CheckType.array;
    }

}