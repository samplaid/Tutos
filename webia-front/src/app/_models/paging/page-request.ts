
/**
 * Allows a class to became pageable.
 */
export class PageRequest {
    
    constructor(public page: number, public size: number){}
    
    /**  Returns the offset to be taken according to the underlying page and page size.*/
    offset?: number = 0;

}
