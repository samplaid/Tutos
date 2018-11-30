import { Component, Directive, ElementRef, Input, SimpleChange, OnChanges } from '@angular/core';
import { UserService } from "../";

/*
 * Directive
 * role to hide element if use don't have one of this role
 * ex :  <button  [hasRoles]="['EDIT','DELETE']" [allRoles]="true" />Delete</button>
 */
@Directive({
  selector: '[hasRoles]' 
})
export class RolesDirective implements OnChanges {

  @Input('hasRoles') roles: string[];
  @Input() allRoles: boolean = false;

  private _initialDisplay;

  constructor(private el: ElementRef, private user: UserService) {}

  ngOnInit(){
    this._initialDisplay = this.el.nativeElement.style.display;
    this.checkRoles();
  }

  ngOnChanges(changes: {[propKey: string]: SimpleChange}){
      //this.user.getUserProfile().then(user=> this.checkRoles());
      this.checkRoles();
  }

  private checkRoles(){
      if (this.allRoles){
        if (this.user.hasAllRoles(this.roles))
            this.show();
        else
          this.hide();
      } else {
          if (this.user.hasOneRole(this.roles))
            this.show();
          else
            this.hide();
      }
  }

  private show() {
        this.el.nativeElement.style.display = this._initialDisplay;
  }

  private hide() {
        this.el.nativeElement.style.display = 'none';
  }

}