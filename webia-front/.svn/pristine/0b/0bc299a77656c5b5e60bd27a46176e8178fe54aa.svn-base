import { Observable } from 'rxjs/Observable';
import { StateMode } from './../mode';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MessageService } from "../index";
import { Policy } from "../../_models/index";
import { LoadableComponent } from '../../workflow/components/loadable.component';
import { PolicyService } from '../../policy/services/policy.service';


@Component({
  selector: 'policy-picker',
  template: `
    <div class="relative" [ngClass] = "mode !== stateMode.readonly ? stateClass : ''">
      <div class="input-group" [ngBusy]="subs">

        <ng-container *ngIf="stateClass!='policy-found';else policyFound">
          <input autofocus (input)="initState()" type="text" class="input-sm form-control policy-picker" (keydown.enter)="searchPolicy()"
            [(ngModel)]="policySearchString"
            placeHolder="policy number"
            maxlength="14" [desactive]="mode">
          <span *ngIf="mode !== stateMode.readonly" class="input-sm input-group-addon">
            <i class="fa fa-search clickable" (click)="searchPolicy()"></i>
          </span>
        </ng-container>

        <ng-template #policyFound>
          <div class="search-box form-control input-sm">
                <a [attr.href]="'./#/policy?id='+policySearchString" target="_blank" [attr.title]="'Link the the policy '+policySearchString">
                    <div class="search-caption" [innerHtml]="policySearchString"></div>
                </a>
            </div>
          <span *ngIf="mode !== stateMode.readonly" class="input-sm input-group-addon">
            <i class="fa fa-undo clickable" (click)="reset()"></i>
          </span>
        </ng-template>          
      </div>

      <span *ngIf="helpMsg && mode !== stateMode.readonly" class="help-block">{{helpMsg}}</span>
    </div>
  `,
  styles: [`
    .clickable{
      cursor: pointer;
    }
  `]
})
export class PolicyPicker extends LoadableComponent {

  @Input() policySearchString = "";
  @Input() mode: string;
  @Input() policyId: String;
  @Output() onPolicyChange = new EventEmitter<Policy>();

  readonly stateMode = StateMode;
  helpMsg: string;
  stateClass: string;

  constructor(private policyService: PolicyService,
    private messageService: MessageService) {
    super();
  }

  ngOnInit() {
  }

  /** Perform the search (call the web service) */
  searchPolicy() {

    this.initState();

    // trim the input
    this.policySearchString = this.policySearchString.trim().toUpperCase();

    if (this.policySearchString == '') {
      this.setError("The policy id is required")
      return;
    }

    const policyServiceSub = this.policyService.getOptionalPolicy(this.policySearchString.trim())
      .catch((error: Response) => {
        if (error && error.status == 400) {
          this.setError('Unexisting policy');
        } else {
          this.setError('Could not fetch policy');
        }
        return Observable.of(null);
      }).subscribe(policy => {
        if (policy == null) {
          this.onPolicyChange.emit(null);
        } else if (policy.activeStatus != "ACTIVE") {
          this.onPolicyChange.emit(null);
          this.setWarning('Policy not active, current status: ' + policy.activeStatus);
        } else {
          this.onPolicyChange.emit(policy);
          this.setSuccess(policy.polId);
        }
      });

    this.addSubscription(policyServiceSub);
  }

  reset() {
    this.initState();
    this.policySearchString = '';
    this.onPolicyChange.emit(null);
  }

  private setError(error: string): void {
    this.helpMsg = error;
    this.stateClass = 'has-error';
  }

  private setWarning(error: string): void {
    this.helpMsg = error;
    this.stateClass = 'has-warning';
  }

  private setSuccess(policy: string): void {
    this.helpMsg = '';
    this.stateClass = 'policy-found'; // NB : using this value prevents to use an other variable to detect if a policy was found
  }

  initState(): void {
    this.helpMsg = '';
    this.stateClass = '';
  }
}