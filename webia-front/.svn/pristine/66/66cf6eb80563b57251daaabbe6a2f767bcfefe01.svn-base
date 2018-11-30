import { SortDirection } from "./sort-direction.enum";
import { Column } from '../models/column';

export class ColumnSorter {

  constructor(private columnSortBy: Column) { }

  sort<T>(data: T[], comparator?: (col, a, b) => number): T[] {
    let sortedData: T[] = Object.assign([], data);

    if (this.columnSortBy) {      
      if (this.columnSortBy.sort !== SortDirection.NONE) {
        if(comparator) {          
          sortedData = sortedData.sort((a, b) => comparator(this.columnSortBy, a, b));
        } else {
          sortedData = sortedData.sort(this.internalComparator);
        }
      }
    }
    return sortedData;
  }

  internalComparator(obj1: any, obj2: any): number {
    if (obj1[this.columnSortBy.name] > obj2[this.columnSortBy.name]) {
      if (this.columnSortBy.sort === SortDirection.DESC) {
        return -1;
      } else if (this.columnSortBy.sort === SortDirection.ASC) {
        return 1;
      } else if (this.columnSortBy.sort === SortDirection.NONE) {
        return 0;
      }
    } else if (obj1[this.columnSortBy.name] < obj2[this.columnSortBy.name]) {
      if (this.columnSortBy.sort === SortDirection.DESC) {
        return 1;
      } else if (this.columnSortBy.sort === SortDirection.ASC) {
        return -1;
      } else if (this.columnSortBy.sort === SortDirection.NONE) {
        return 0;
      }
    }
    return 0;
  }

}
