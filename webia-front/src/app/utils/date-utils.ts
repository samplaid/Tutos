export abstract class DateUtils {

    /**
     * The value of this constant is '1800'.
     */
    public static readonly WEALINS_NULL_YEAR: number= 1800;
    /**
     * The value of this constant is '01/01/1753'.
     */
    public static readonly WEALINS_NULL_DATE_SHORT_DATE ="01/01/1753";
    /**
     * The value of this constant is '01-01-1753'.
     */
    public static readonly WEALINS_NULL_DATE_DD_MM_YYYY_HYPHEN ="01-01-1753";
    /**
     * The value of this constant is '1753/01/01'.
     */
    public static readonly WEALINS_NULL_DATE_YYYY_DD_MM_BAR ="1753/01/01";
    /**
     * The value of this constant is '1753-01-01'.
     */
    public static readonly WEALINS_NULL_DATE_ISO_DATE ="1753-01-01";
    /**
     * The value of this constant is like a full date.
     */
    public static readonly WEALINS_NULL_DATE:Date = new Date("1753-01-01");

    private static pad(num, size) {
        var s = num + "";
        while (s.length < size) s = "0" + s;
        return s;
    }

    private static _towDigit(value){
      return ('00'+value).substr(-2);
    }

    /**
     * Format the date to the international standard format yyyy-mm-dd (ISODate)
     * @param date the date to be formatted
     */
    static formatToIsoDate(date:Date): string{
        if(date){
            return date.getFullYear()+'-'+ DateUtils._towDigit(date.getMonth()+1)+'-'+ DateUtils._towDigit(date.getDate());
        }
        return '';
    }
    /**
     * Format the date in parameter to the format 'DD<sep>MM<sep>YYYY'.
     * @param date  the date to format
     * @param sep the separator
     */
    static formatToddMMyyyy(date: Date, sep:string): string {        
        return DateUtils.pad(date.getDate(), 2) + sep + DateUtils.pad((date.getMonth() + 1), 2) + sep + DateUtils.pad(date.getFullYear(), 4);
    }
   
}
