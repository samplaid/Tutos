import { Observable } from 'rxjs/Observable';
import { Component, Directive, ElementRef, Input, SimpleChange, OnChanges } from '@angular/core';
import { StateMode } from "../mode";
import { UserService } from '../services/user.service';

/*
 * Directive
 * mode to disable element if mode is ReadOnly or something else given as parametter
 * ex :  <button [desactive]="mode" [disableFor]="['readOnly','waiting']" />Delete</button>
 * This directive contains the following input:
 * - desactive: contains the state mode like readOnly, waiting, create..
 * - disableFor:  takes an array of the state modes. The desactive uses this input ot check if it is a disable mode.
 * - forRoles: a list of user role the disable mode wiill be applied.
 * - allRoles: a flag that indicates if the directive should check for all roles in 'forRoles' to apply the disable mechanism.
 * - displayMode: The display mode to use. Take only 'hide' or 'disable' as input. If the element is type of 'Button' or 'Div', the dispaly mode will be defaulted to 'hide'.
 * - outerRole: When set to true, the directive will disable the element for the logged user that has not the given roles. Default is false.
 * 
 */
@Directive({
  selector: '[desactive]' 
})
export class DesactiveDirective implements OnChanges {
  
  @Input('desactive') mode: string;
  @Input('disableFor') modes: string[] = [StateMode.readonly, StateMode.waiting];
  @Input('forRoles') roles: string[];
  @Input('allRoles') checkForAllRoles: boolean;
  @Input('displayMode') displayMode: 'hide' | 'disable';
  @Input('outerRole') outerRole: boolean;

  private toHide = ["BUTTON", "DIV"];
  private _initialDisplay;
  private _initialDisabled;

  constructor(private el: ElementRef, private userService: UserService) {
    this.roles = [];
    this.checkForAllRoles = false;
    this.outerRole = false;
    if(this.toHide.indexOf(this.el.nativeElement.nodeName) < 0){
      this.displayMode = 'disable';
    } else {
      this.displayMode = 'hide';
    }
  }

  ngOnInit(){    
    this._initialDisplay = this.el.nativeElement.style.display;
    this._initialDisabled = this.el.nativeElement.disabled;   
    this.check();
  }

  ngOnChanges(changes: {[propKey: string]: SimpleChange}){
    this.check();
  }

  /**
   * This method disable the element idemtified by this directive.
   * The strategy gives prior to the user roles if it is defined in the input of this directive.
   * 
   */
  private check(){
    // give prior to the roles.
    if(this.roles && this.roles.length > 0){
      let canDisabled = false;
      if(this.outerRole) {
        canDisabled = this.userService.hasNotAllRoles(this.roles);
      } else {
        canDisabled = (this.checkForAllRoles) ?  this.userService.hasAllRoles(this.roles) : this.userService.hasOneRole(this.roles);
      }
      
      if(!canDisabled){
        if(this.displayMode === 'hide') {
          this.show();
        } else {
          this.reactivate();
        }
      } else {
        if(this.displayMode === 'hide') {
          this.hide();
        } else {
          this.disable();
        }
      }
    } else {
      let readOnly = this.modes.indexOf(this.mode) > -1;
      if(!readOnly) {
        if(this.displayMode === 'disable') {
          this.reactivate();
        } else {
          this.show();
        }
      } else {
        if(this.displayMode === 'disable') {
          this.disable();
        } else {
          this.hide();
        }
        
      }
    }    
  }

  private reactivate() {
        this.el.nativeElement.disabled = this._initialDisabled;
  }

  private disable() {
    Observable.of({}).delay(1).toPromise().then(()=>this.el.nativeElement.disabled = true);
  }

  private show() {
        this.el.nativeElement.style.display = this._initialDisplay;
  }

  private hide() {
        this.el.nativeElement.style.display = 'none';
  }


}