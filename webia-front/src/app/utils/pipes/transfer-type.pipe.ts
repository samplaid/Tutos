import { transferTypeReinvestCode, transferTypeReinvestLabel, transferTypeTransferCode, transferTypeTransferLabel } from './../../_models/constant';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'transferType' })
export class TransferTypePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) {
      return "";
    }
    switch (value) {
      case transferTypeReinvestCode:
        return transferTypeReinvestLabel;
      case transferTypeTransferCode:
        return transferTypeTransferLabel;
      default:
        return value;
    }
  }
}