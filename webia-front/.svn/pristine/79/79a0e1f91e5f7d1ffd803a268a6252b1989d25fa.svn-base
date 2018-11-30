import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'filterTree'})
export class FilterTreePipe implements PipeTransform {
    transform(nodes, filter) {
        if (!filter || filter=="") return nodes;
        return this.filterNodeArray(nodes, filter).filter((o) => o!=null);
    }

    filterNodeArray(nodes, filter) {
        if (!nodes) return null;
        return nodes.map((node) => this.filterNodeRecursively(node, filter));
    }

    filterNodeRecursively(node, filter) {
        Object.assign(node, {
            children: this.filterNodeArray(node.children||[], filter)
        });
        if (!this.matches(node, filter)) return null;
    }

    matches(node, filter) {
        if (!node) return false;
        if (!node.children || node.children.length) return true;
        return node.value.toLowerCase().includes(filter.toLowerCase());
    }

}
