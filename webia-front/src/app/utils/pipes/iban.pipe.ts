import { Pipe, PipeTransform } from '@angular/core';

/**
 * Filter the input data to follow the iban format
 * like xxxx xxxx xxxx xxxx xxxx
 */
@Pipe({
    name: 'iban'
})
export class IbanPipe implements PipeTransform {
    
    /**
     * Filter the value to follow the iban format.
     * like xxxx xxxx xxxx xxxx xxxx
     */
    transform(value: any, ...args: any[]): any {        
        if(!value) 
            return value;

        let array = value.replace(/[\s+]/g,'').split("");
        
        for(let i = 0; i < array.length; i++) {
            if(( (i + 1) % 4) == 0) 
                array[i] += ' ';
        }
        
        return array.join().replace(/[,]/g,'') ;        
    }
}