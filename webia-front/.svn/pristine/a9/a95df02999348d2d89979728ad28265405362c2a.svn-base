import { KEYCLOAK_HTTP_PROVIDER } from './../_guards/keycloak.http';
import { NgModule, APP_INITIALIZER, ModuleWithProviders  } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { CommonModule } from '@angular/common';
import { ModalModule, AlertModule, CollapseModule, BsDatepickerModule, PaginationModule, PopoverModule } from 'ngx-bootstrap';
import {NgBusyModule, BusyConfig, IBusyConfig} from 'ng-busy';

// services
import { MessageService, BsModalContent , NgbdAlertContainer, Alert } from './services/message.service';
export { MessageService, BsModalContent , NgbdAlertContainer, Alert } from './services/message.service';

import { Rootscope } from './services/rootscope'; export { Rootscope } from './services/rootscope';
import { HttpService, HandleErrorOptions } from './services/http.service'; export { HttpService, HandleErrorOptions } from './services/http.service';
import {ApiService} from './services/api.service'; export {ApiService} from './services/api.service';
import { ConfigService } from './services/config.service'; export { ConfigService } from './services/config.service';
import { PaginationService } from './services/pagination.service'; export { PaginationService } from './services/pagination.service';
import {UserService} from './services/user.service'; export {UserService} from './services/user.service';
import { Store } from './services/store.service'; export { Store } from './services/store.service';

//pipes
import {KeysPipe} from './pipes/keys.pipe'; export {KeysPipe} from './pipes/keys.pipe'; 
import {CapitalizePipe} from './pipes/capitalize.pipe'; export {CapitalizePipe} from './pipes/capitalize.pipe';
import {FilterTreePipe} from './pipes/filter-tree.pipe'; export {FilterTreePipe} from './pipes/filter-tree.pipe'; 
import {OrderByPipe} from './pipes/order-by.pipe'; export {OrderByPipe} from './pipes/order-by.pipe'; 
import {PercentageAmountPipe} from './pipes/percentage-amount.pipe'; export {PercentageAmountPipe} from './pipes/percentage-amount.pipe'; 
import {FilterPipe} from './pipes/filter.pipe'; export {FilterPipe} from './pipes/filter.pipe'; 
import {AddressPipe} from './pipes/address.pipe'; export {AddressPipe} from './pipes/address.pipe'; 
import {IbanPipe} from './pipes/iban.pipe'; export {IbanPipe} from './pipes/iban.pipe'; 
import {MatchFundPipe} from './pipes/match-fund.pipe'; export {MatchFundPipe} from './pipes/match-fund.pipe';
import {OptionDetailPipe} from './pipes/option-detail.pipe'; export {OptionDetailPipe} from './pipes/option-detail.pipe'; 
import {MapValuePipe} from './pipes/mapvalue.pipe'; export {MapValuePipe} from './pipes/mapvalue.pipe'; 
import {CurrencyFormatterPipe} from './pipes/currency-formatter.pipe'; export {CurrencyFormatterPipe} from './pipes/currency-formatter.pipe'; 
import {CurrencyPipe} from './pipes/currency.pipe'; export {CurrencyPipe} from './pipes/currency.pipe';
import {ClientDisplayNamePipe} from './pipes/client-display-name.pipe'; export {ClientDisplayNamePipe} from './pipes/client-display-name.pipe'; 
import {WDatePipe} from './pipes/w-date.pipe'; export {WDatePipe} from './pipes/w-date.pipe'; 
import { ReplacePipe } from './pipes/replace.pipe'; export { ReplacePipe } from './pipes/replace.pipe';
import { GroupByPipe } from './pipes/group-by.pipe'; export { GroupByPipe } from './pipes/group-by.pipe';
import { TextOfClausePipe } from './pipes/text-of-clause.pipe'; export { TextOfClausePipe } from './pipes/text-of-clause.pipe';
import { StepNamePipe } from './pipes/step-name.pipe';export { StepNamePipe } from './pipes/step-name.pipe';
import { TransferTypePipe } from './pipes/transfer-type.pipe';export { TransferTypePipe } from './pipes/transfer-type.pipe';



// directives
import {RolesDirective} from './directives/roles.directive'; export {RolesDirective} from './directives/roles.directive'; 
import {CurrencyFormatterDirective} from './directives/currency-formatter.directive'; export {CurrencyFormatterDirective} from './directives/currency-formatter.directive'; 
import {DesactiveDirective} from './directives/desactive.directive'; export {DesactiveDirective} from './directives/desactive.directive'; 
import {AutofocusDirective} from './directives/autofocus.directive'; export {AutofocusDirective} from './directives/autofocus.directive'; 
import {HighLightDirective} from './directives/highlight.directive'; export {HighLightDirective} from './directives/highlight.directive'; 
import {Autosize} from './directives/autosize.directive'; export {Autosize} from './directives/autosize.directive';
import {AdAgentLabelDirective} from './directives/ad-agent-label.directive'; export {AdAgentLabelDirective} from './directives/ad-agent-label.directive'; 



// component
import {TreeView} from './components/tree-view.component'; export {TreeView} from './components/tree-view.component';
import {MyDatepicker, InputDatepicker} from './components/datepicker.component'; export {MyDatepicker, InputDatepicker} from './components/datepicker.component';
import {PercentAmountComponent} from './components/percent-amount.component'; export {PercentAmountComponent} from './components/percent-amount.component';
import { WNumberComponent } from './components/w-number.component'; export { WNumberComponent } from './components/w-number.component';
import { WebiaBusyComponent } from './components/webia-busy.component'; export { WebiaBusyComponent } from './components/webia-busy.component';
import { TooltipModule } from 'ngx-bootstrap/tooltip';

// Static, Abstract, Enum and Other Classes
export { AbstractSearchCriteria } from './search/search-criteria';
export { StateMode } from './mode';
export { DateUtils } from './date-utils';
export { User } from './services/user.service';

export const busyConfig = { message: 'Loading', template: WebiaBusyComponent, backdrop: true, delay: 0, minDuration: 300, wrapperClass: 'loader' };

var services = [MessageService, Rootscope,HttpService,ApiService, PaginationService, UserService, Store, DocumentService ];

var Components_Directives_Pipes = [
  BsModalContent , 
  NgbdAlertContainer, 
  WebiaBusyComponent,
          
  CapitalizePipe, 
  FilterTreePipe, 
  OrderByPipe, 
  PercentageAmountPipe, 
  FilterPipe,
  AddressPipe, 
  OptionDetailPipe, 
  IbanPipe, 
  MatchFundPipe, 
  MapValuePipe, 
  CurrencyPipe, 
  CurrencyFormatterPipe, 
  KeysPipe,
  ClientDisplayNamePipe,
  WDatePipe,
  RolesDirective, 
  CurrencyFormatterDirective, 
  DesactiveDirective, 
  AutofocusDirective,
  HighLightDirective, 
  AdAgentLabelDirective, 
  Autosize,
  TreeView, 
  MyDatepicker,
  InputDatepicker, 
  PercentAmountComponent, 
  WNumberComponent, 
  ReplacePipe, 
  GroupByPipe, 
  TextOfClausePipe, 
  StepNamePipe, 
  TransferTypePipe,
  TransferStatusComponent,
  TransactionDetailsMainComponent,
  TransactionHistoryDetailsComponent,
  PopinDirective];

const sharedModules = [
    FormsModule,
    HttpModule,
    CommonModule,
    ModalModule.forRoot(),
    AlertModule.forRoot(),
    CollapseModule.forRoot(),
    BsDatepickerModule.forRoot(),
    PaginationModule.forRoot(),
    PopoverModule.forRoot(),
    NgBusyModule.forRoot(busyConfig),
    TooltipModule.forRoot()
  ]

export function ConfigFactory(){
  return ConfigService.getInstance('./config.json');
}

/**
 * Definie Local 
 */
import { defineLocale } from 'ngx-bootstrap/chronos';
import { enGbLocale, frLocale } from 'ngx-bootstrap/locale';
import { ModalRejectStep } from "./components/modal-reject-step.component";
import { PopinDirective } from './directives/popin.directive';import { TransferStatusComponent } from './components/transfer-status.component';import { DocumentService } from './services/document.service';
import { TransactionDetailsMainComponent } from '@policy/components/transaction-details/transaction-details-main.component';
import { TransactionHistoryDetailsComponent } from '@policy/components/transaction-details/transactions-history-details.component';
defineLocale('fr', frLocale); 
defineLocale('en', enGbLocale); 

@NgModule({
  declarations: [...Components_Directives_Pipes, ModalRejectStep],
  imports: [...sharedModules],
  entryComponents: [BsModalContent, NgbdAlertContainer, WebiaBusyComponent, ModalRejectStep],
  exports:[...Components_Directives_Pipes, FormsModule, HttpModule, CommonModule, WNumberComponent], 
  providers: [
    {provide: ConfigService, useFactory: ConfigFactory},
    KEYCLOAK_HTTP_PROVIDER
  ]
})
export class UtilsModule {
  static forRoot():ModuleWithProviders {
          return {
              ngModule: UtilsModule,
              providers: [...services]   //TODO : add guard here 
          };
      }
}

