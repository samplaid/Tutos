import { NgModule } from '@angular/core';
import { PolicyPartnersComponent } from './policy-partners/policy-partners.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AgentModule } from '../../agent';
import { UtilsModule } from '../../utils';
import { PolicyCommunicationComponent } from './policy-communication/policy-communication.component';
import { PolicyClientsComponent } from './policy-clients/policy-clients.component';
import { PopoverModule } from 'ngx-bootstrap';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        UtilsModule,
        AgentModule,
        PopoverModule.forRoot()],
    exports: [
        PolicyPartnersComponent,
        PolicyCommunicationComponent,
        PolicyClientsComponent,
    ],
        // TODO : The other policy components (policy-clauses, policy-fees, etc... should be moved here as well)
    declarations: [
        PolicyPartnersComponent,
        PolicyCommunicationComponent,
        PolicyClientsComponent,
    ],
    providers: [
    ],
})
export class PolicyComponentModule {}
