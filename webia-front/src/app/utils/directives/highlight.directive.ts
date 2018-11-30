import { Directive, OnInit, Input, ElementRef, Renderer, AfterViewInit } from '@angular/core';

@Directive({
    selector: '[highlight]',
})
export class HighLightDirective implements AfterViewInit{   
    @Input('pattern')pattern: string|RegExp;
    @Input('highlight') cssClass:string;

    constructor(private el: ElementRef, private renderer: Renderer) { }

    ngAfterViewInit() {        
        let replacement = this.el.nativeElement.innerHTML.replace(this.pattern, "<span class='"+ this.cssClass +"'>$1</span>");
        this.renderer.setElementProperty(this.el.nativeElement, 'innerHTML', replacement);
    }

}