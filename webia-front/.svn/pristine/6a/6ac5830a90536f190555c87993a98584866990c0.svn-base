import { Component, OnInit, Input, EventEmitter, Output, SimpleChanges, OnChanges } from '@angular/core';
import { AmStrategyListService } from './am-strategy-list.service';
import { StateMode } from '../../../utils/mode';
import { AmStrategyListOption } from './am-strategy-list-option';
import { Observable } from 'rxjs/Observable';
import { UoptDetails } from '../../../_models/index';
import { Subscription } from 'rxjs';


@Component({
  selector: 'am-strategy-list',
  templateUrl: './am-strategy-list.component.html',
  styleUrls: ['./am-strategy-list.component.css'],
  providers: [AmStrategyListService]
})
export class AmStrategyListComponent implements OnInit, OnChanges {   
  
  agtId: string;
  classes: string = 'text-grey-darken';
  keyValue: string;

  amStrategies: UoptDetails[];
  defaultStrategies: UoptDetails[];

  @Output()
  modelIdChange: EventEmitter<string> = new EventEmitter<string>();
  
  @Input() mode: string = StateMode.readonly;
  @Input() required: boolean;
  @Input() option: AmStrategyListOption;
  @Input() 
  set assetManagerId(agtId: string) {
    this.agtId = agtId;    
  }
 
  @Input() set modelId(keyValue: string) {   
    this.keyValue = keyValue;
    this.modelIdChange.emit(this.keyValue);
  }

  get modelId(): string{
    return this.keyValue;
  }

  constructor(private amStrategyListService: AmStrategyListService) { }

  ngOnChanges(changes: SimpleChanges): void { 
    let amStrategies$ = this.loadAmStrategies();   
    let defaultStrategies$ = this.loadDefaultStrategies();
    let mergedList$ = this.mergeStrategies(amStrategies$, defaultStrategies$);

    if(this.option && this.option.config) {
      if(this.option.config.treatBlueList$) {
        amStrategies$ = this.option.config.treatBlueList$(amStrategies$);
      }
      if(this.option.config.treatOtherList$) {        
        defaultStrategies$ = this.option.config.treatOtherList$(defaultStrategies$);
      }       
    }

    amStrategies$.subscribe(items => this.amStrategies = items);
    defaultStrategies$.subscribe(items => this.defaultStrategies = items);

    this.setSelectedStrategyCss(mergedList$);
  }

  ngOnInit() {
  }

  loadDefaultStrategies(): Observable<UoptDetails[]> {   
    return this.amStrategyListService.loadDefaultStrategies(this.modelId, this.option);
  }

  loadAmStrategies(): Observable<UoptDetails[]> {
    if(this.agtId) {
      return this.amStrategyListService.loadStrategies(this.agtId, this.modelId, this.option);
    } else {
      return Observable.of(new Array<UoptDetails>());
    }    
  }

  mergeStrategies(amStrategies$: Observable<UoptDetails[]>, defaultStrategies$: Observable<UoptDetails[]>) {
    return Observable.merge(amStrategies$.flatMap(items => items), defaultStrategies$.flatMap(items => items));
  }

  setSelectedStrategyCss(mergedList$: Observable<UoptDetails>): void {
    const textGreyDarken = 'text-grey-darken';
    const textSelectDanger = 'text-select-danger';
    const enabled = (keyValue, item) => this.amStrategyListService.isStrategyEnabled(keyValue, item);
    mergedList$.filter(item => !enabled(this.keyValue, item))
    .take(1)
    .subscribe(item => this.classes = !enabled(this.keyValue, item) ? textSelectDanger : textGreyDarken);
  }

}
