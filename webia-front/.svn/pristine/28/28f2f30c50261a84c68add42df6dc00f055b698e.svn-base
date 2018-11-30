import { ElementRef, HostListener, Directive, AfterContentChecked} from '@angular/core';

@Directive({
    selector: 'textarea[autosize]'
})

export class Autosize implements AfterContentChecked {
 
 @HostListener('input',['$event.target'])
  onInput(textArea: HTMLTextAreaElement): void {
    this.adjust();
  }

  constructor(public element: ElementRef){}


  ngAfterContentChecked(): void{
    this.adjust();
  }

  
  adjust(): void{
    if (this.element.nativeElement.style.height != this.element.nativeElement.scrollHeight+'px'){
        this.element.nativeElement.style.overflow = 'hidden';
        this.element.nativeElement.style.height = 'auto';
        this.element.nativeElement.style.height = this.element.nativeElement.scrollHeight + "px";
    }
  }
}
