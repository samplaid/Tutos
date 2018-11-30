import { Directive, ElementRef, Renderer, Input, AfterViewInit, AfterViewChecked } from '@angular/core';

@Directive({
    selector: '[autofocus]'
})
export class AutofocusDirective implements AfterViewInit{
    private _autofocus;

    constructor(private el: ElementRef, private renderer: Renderer){}

    ngOnInit(){}

    ngAfterViewInit(){
        if (this._autofocus || typeof this._autofocus === "undefined")
            this.renderer.invokeElementMethod(this.el.nativeElement, 'focus', []);
    }
   
    @Input() set autofocus(condition: boolean){
        this._autofocus = condition != false;
    }
}