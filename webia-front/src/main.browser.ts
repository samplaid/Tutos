import './polyfills.browser';
import './rxjs.imports';

import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { bootloader } from '@angularclass/hmr';
import { AppModule } from './app/app.module';
import { BrowserAppModule } from './app/browser.app.module';
import { decorateModuleRef } from './environment';

if ('production' === ENV) {
  enableProdMode();
}

import { ConfigService } from './app/utils/services/config.service';

/*
 *  KeyCloak security
*/
import { KeycloakService } from './app/_guards/keycloak.service';


export function main(): Promise<any> {
  if (module.hot) {
    module.hot.accept();
  }
  return (
      KeycloakService.init().then( () => {   //
            ConfigService.loadInstance('./config.json').then(() => {
                        platformBrowserDynamic().bootstrapModule(BrowserAppModule)
                        .then(decorateModuleRef)
                        .catch((err) => console.error("platformBrowserDynamic error :",err))
                  }).catch(e => console.error("ConfigService error :",e))
      }).catch(e => console.error("KeycloakService error :",e) )   //
      )
}

// needed for hmr
// in prod this is replace for document ready
bootloader(main);
