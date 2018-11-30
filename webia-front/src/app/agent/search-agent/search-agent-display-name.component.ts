import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Store } from '../../utils';
import { StoreName } from '../../utils/services/store-name';

@Component({
  template:
    ` 
    <div class="search-caption" [attr.title]="title">
      <a *ngIf="displayMode == 'POP_UP'" class="clickable"> {{ label }} - <small><i>{{ agtId }} </i></small></a>
      <a *ngIf="displayMode == 'LINK'" href="./#/agent/ {{ agtId }}" target="_blank">{{ label }} - <small><i>{{ agtId }} </i></small></a>
      {{ catName }}
    </div>    
  `
})
export class SearchAgentDisplayNameComponent implements OnInit {

  catName: string;

  @Input()
  label: string;

  @Input()
  showLabel: boolean;

  @Input()
  title: string;

  @Input()
  displayMode: string = 'POP_UP | LINK';

  @Input()
  link: boolean;

  @Input()
  agtId: string;

  @Input() 
  set categoryCode(code: string) {
    this.catName = '';
    this.store.select(StoreName.AGENT_CAT_LIST).subscribe((cats: any[]) => {
      let cat = cats.find(cat => cat && cat.acaId === code);
      this.catName = (cat && cat.name) ? '[ ' + cat.name + ' ]': '';
    });
  }

  constructor(private store: Store) { }

  ngOnInit() {
  }

}
