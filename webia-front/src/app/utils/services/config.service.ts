import {Injectable} from "@angular/core";

@Injectable()
export class ConfigService {

    private static cache = {};

    constructor(private data: any) { }

    public static loadInstance(jsonFile: string) {
        return new Promise((resolve, reject) => {
            var xobj = new XMLHttpRequest();
            xobj.overrideMimeType("application/json");
            xobj.open('GET', jsonFile, true); 
            xobj.onreadystatechange = () => {
                if (xobj.readyState == 4) {
                    if (xobj.status == 200) {
                        this.cache[jsonFile] = new ConfigService(JSON.parse(xobj.responseText));
                        resolve();
                    }
                    else {
                        reject(`Could not load file '${jsonFile}': ${xobj.status}`);
                    }
                }
            }
            xobj.send(null);
        });
    }

    public static getInstance(jsonFile: string) {
        if (jsonFile in this.cache) {
            return this.cache[jsonFile];
        } 
        throw `Could not find config '${jsonFile}', did you load it?`;
    }

    public getProps(key: string) {
        if (this.data == null) {
            return null;
        }
        if (key in this.data) {
            return this.data[key];
        }
        return null;
    }
}