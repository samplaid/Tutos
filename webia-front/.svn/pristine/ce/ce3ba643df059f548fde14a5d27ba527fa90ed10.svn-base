import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'tree-view',
  template: `
    <ul>
        <li *ngFor="let node of treeData;" (click)="toggle($event, node)" [ngClass]="{'noChild' : !node[childName] ||!node[childName].length, 'Expand' : node.isExpand, 'link' : node.nodeRef}">
                <div class="nodeLabel" (click)="selectNode(node)"><a >{{getLabel(node)}}</a></div>
                <tree-view [data]="node[childName]" (onSelectNode)="selectNode($event)" [getLabel]="getLabel" [labelName]="_labelName" [childName]="childName" [collapse]="!node.isExpand"></tree-view>
        </li>
    </ul>
  `,
  styles : [`
    ul {  
        margin: 0;  
        padding: 0 0 0 3em;
        list-style: none;  
    } 
    li:not(.noChild) {
        cursor : pointer;
    }
    li.noChild {
        color : #9B8E68;
        cursor : default;
        display: inline;
    }
    li {
        list-style: none;
    }
     li>div>a{
        color: black;
        text-decoration: none;
    }   
    li>div>a:hover{
        color: #7d7253;
    }
    li>.nodeLabel {
        line-height: 1.7em;
        display: inline-block;
        padding: 0px 20px 0px 0px;
        min-width: 50px;
        margin-left: 1.5em;
   -moz-transition: all 0.2s;
-webkit-transition: all 0.2s;
        transition: all 0.2s;
    }
    li>.nodeLabel:hover {
        display: inline-block;
        padding: 0px 10px 0px 10px;
        background-color: rgba(255,255,255,0.2);
    }
    li:before {   
        content: "";  
        // border-color: transparent #008eef;  
        // border-style: solid;  
        // border-width: 0.35em 0 0.35em 0.45em;  
        display: block;  
        position: relative;
        //background-image: url('assets/img/folder.png');  height: 13px; width: 15px; top: 1.2em; left: -1.5em;
        //background-image: url('assets/img/books_icon.png'); height: 25px; width: 22px; top: 1.3em; left: -2em;
        background-image: url('assets/img/tree-arrow.png'); height: 15px; width: 15px; top: 1.3em; left: 0em;
        cursor : zoom-in;
        margin-top: -1em;
        transition : all 0.3s;
    } 
    li:before:hover {   
        box-shadow: 1px 1px 1px 1px grey;
    }
    li.Expand:before {   
        // border-color: #008eef transparent ;   
        // border-width:   0.55em  0.35em 0 0.35em ;
        // top: 0.8em; 
        cursor : zoom-out;
   -webkit-transform: rotate(90deg);
  -moz-transform: rotate(90deg);
  -ms-transform: rotate(90deg);
  -o-transform: rotate(90deg);
  transform: rotate(90deg); 
    }
    li.noChild:before {   
        background-color: #998c68;
        border-radius: 20%;
        border-width: 1px;
        background-image: none;
        display: block;
        height: 8px;
        width: 8px;
        left: 0.1em;
        top: 1.3em;
        margin-bottom: 4px;
        cursor : zoom-out;
        box-shadow: 0px 0px 1px 2px #998c68;
    }
    li.noChild.file:before {   
        border-color: #008eef;
        border-radius : 50%;
        border-width:  0.2em ; 
        background-image: url('assets/img/pdf.png');
        display: block;  
        height: 15px;
        width: 13px;
        left: 0em;
        top: 1.2em; 
    }
    li.noChild:hover {
        //background-color: #F0E1A6;
    }
    .link:hover {
        text-decoration: none;
        cursor: alias!important;
        color : #262837;
    }
    tree-view.collapse.in:hover {
        cursor: zoom-out;
    }

}
  `]
})
export class TreeView {
    
  treeData = [];
  
  constructor(){
  }
  
  @Input() deep: number = 2;
  
  @Input() set data(data:any){
      this.treeData = data;
  };
  
  @Input() set json(data:string){
      this.treeData = this.createTree(data, 0);
  }

  private _labelName = "label";
  @Input() set labelName(name: string){ this._labelName = name ;};   // IMPORTANT: this has to be a string. then gice in component  [_labelName]=" 'myLabel' " with simple cotes
  @Input() getLabel: Function = (node)=>{ return node[this._labelName]; };  // this is to give a builder for the label
  @Input() childName: string = "children";   // IMPORTANT: this has to be a string. then gice in component  [childName]=" 'child' " with simple cotes
  
  @Output() onSelectNode:EventEmitter<any> = new EventEmitter<any>();

  previous_node;

  toggle(event, node){
      event.preventDefault();
      event.stopPropagation();
      if (!node.isExpand){
          if (this.previous_node && node!=this.previous_node)
            this.previous_node.isExpand = false;
          this.previous_node = node;
      }
      node.isExpand = !node.isExpand;
      return node;
  }

  selectNode(node){
      this.onSelectNode.emit(node);
   }

  createTree(parent, level) { 
      let result = [];    
      if (Array.isArray(parent)) {
          parent.forEach((obj, key) => { 
              result.push(this.createNode(key, obj, level));
          })
      } else {
          for (var key in parent) {
              result.push(this.createNode(key, parent[key], level));
          }
      }
      return result
  }

  createNode(key, obj, level) {
      let node =  { label: key+"", children: [] , isExpand : level>=this.deep};
      if (typeof obj === 'string') {
          node.children = [{ label: "'"+obj+"'" }];
      } else if (typeof obj === 'number'){
          node.children = [{ label: "'"+obj+"'" }];
          if (key.toLowerCase().includes("date")){ 
              node.children = [{ label: new Date(obj).toLocaleDateString() }];
          } else if (key.toLowerCase().includes("time")){ 
              node.children = [{ label: new Date(obj).toLocaleTimeString() }];
          }
      } else {
          node.children = this.createTree(obj, ++level);// drill down
      }
      return node
  }
  
}
