
// import { TestBed, inject, fakeAsync, tick, async } from "@angular/core/testing";
// import { XHRBackend, HttpModule, Response, ResponseOptions, BaseRequestOptions, Http } from "@angular/http";
// import { MockBackend, MockConnection } from "@angular/http/testing";
// import { AgentService } from "./agent.service";
// import { MessageService, ApiService, ConfigService, UtilsModule, HttpService } from "../utils/index";
// import { KeycloakService } from "../_guards/keycloak.service";
// import { FullAgent } from "../_models/index";
// import { KeycloakHttp } from "../_guards/keycloak.http";
// import { Observable } from "rxjs/Observable";
// import { ErrorObservable } from "rxjs/observable/ErrorObservable";

// import { AgentModule } from "./index";
// import { UTILS_MOCK_PROVIDERS, subscribeMockServices } from "../utils/services/mock/utils.mock";

// class MockKeycloakHttp {}

// describe('Agent service Test', () => {  
//     let mockAgent = new FullAgent(); 
//     let agentService: AgentService;

//     beforeEach(async(() => {
// 		TestBed.configureTestingModule({
//             imports: [
//                 HttpModule,
//             ],
// 			providers: [ ...UTILS_MOCK_PROVIDERS, AgentService ],            
//         });
//         subscribeMockServices(); 
//         agentService = TestBed.get(AgentService);
//     }));
       
//     it('saveAgent method should update an agent', async(() => { 
//         let inAgent = new FullAgent();
//         inAgent.agtId = 'A03604'
//         inAgent.name = 'Test in';
//         mockAgent = inAgent;
//         mockAgent.name = 'A.I.B. (Associated Insurance Brokers) SA';       
//         agentService.saveAgent(inAgent).then((res: FullAgent) => {
//             expect(res).toBeDefined();
//             expect(res.name).toEqual(mockAgent.name);
//         });
//     }));

//     it('saveAgent method should return rejection promise during the update.', async(() => { 
//         let inAgent = new FullAgent();       
//         let spy =  spyOn(agentService, 'updateAgent');
//         spy.argsForCall = [inAgent];        
//         spy.and.returnValue(Observable.throw(new Error));
//         agentService.saveAgent(mockAgent).catch(err => {
//             expect(err).toBeDefined();
//         });
//     }));

//     it('saveAgent method should create a new agent', async(() => { 
//         let spy =  spyOn(agentService, 'createAgent');
//         let inAgent = new FullAgent();
//         inAgent.agtId = undefined;    
//         spy.argsForCall = [inAgent];    
//         mockAgent.agtId = 'A01235';
//         spy.and.returnValue(Observable.of(mockAgent));
//         agentService.saveAgent(inAgent).then((res: FullAgent) => {
//             expect(res).toBeDefined();
//             expect(res.agtId).toEqual('A01235');
//         });
//     }));

//     it('saveAgent method should return rejection promise during the creation.', async(() => { 
//         let inAgent = new FullAgent();       
//         let spy =  spyOn(agentService, 'createAgent');
//         spy.argsForCall = [inAgent];        
//         spy.and.returnValue(Observable.throw(new Error));
//         agentService.saveAgent(mockAgent).catch(err => {
//             expect(err).toBeDefined();
//         });
//     }));

//     it('saveAgent method should returns UnsavedObjectException over the update and creation', async(() => { 
//         agentService.saveAgent(undefined).catch(err => {
//             expect(err).toBe('UnsavedObjectException')
//         });
//         mockAgent.agtId = undefined;
//         agentService.saveAgent(mockAgent).catch(err => {
//             expect(err).toBe('UnsavedObjectException')
//         });
//     }));

//     it('getAgentFoyer method should fill the datastore and returns the agent foyer A01141', fakeAsync((() => { 
//         const mockAgentFoyer: FullAgent = new FullAgent();
//         mockAgentFoyer.agtId = 'A01141';        
//         agentService.dataStore.agentFoyer = undefined;    
//         // First call, the datastore is empty
//         agentService.getAgentFoyer().subscribe(agentFoyer => {            
//             expect(agentService.dataStore.agentFoyer.agtId).toEqual(agentFoyer.agtId);
//         });
//         // Now try to retrieve form the datastore
//         tick();
//         agentService.dataStore.agentFoyer = 'MockValue'
//         agentService.getAgentFoyer().subscribe(agentFoyer => {
//             expect(agentService.dataStore.agentFoyer).toBeDefined();
//             expect(agentService.dataStore.agentFoyer).toEqual('MockValue');
//         });
//     })));

//     it('getAllAgentCategories method should make a call to the server if the key allAgentCategories is not present in the datastore or is empty, fill the datastore and returns the list of agent category', 
//         fakeAsync(() => { 
//             agentService.dataStore.allAgentCategories = undefined;    
//             // case of allAgentCategories key is not present in cache. --> should be setted in cache after call on the server  
//             let spy = spyOn(agentService, 'GET').and.callFake(()=>{return Observable.of([{"acaId":"DC","name":"Distribution Company","status":2,"registrationType":1,"reportCategory":""}]);});;
//             agentService.getAllAgentCategories();
//             expect(agentService.GET).toHaveBeenCalled();
//             expect(agentService.dataStore.allAgentCategories).toBeDefined();
//             agentService.getAllAgentCategories();
//             expect(spy.calls.count()).toEqual(1);         
//             tick();
           
//     }));
        
//     it('getWealinsAssetManager method should make a call to the server if the key wealinsAssetManager is not present in the datastore , fill the datastore and returns the asset manager', 
//         fakeAsync((() => { 
//             agentService.dataStore.wealinsAssetManager = undefined;    
//             // First call, the datastore is empty               
//             agentService.getWealinsAssetManager().subscribe(wealinsAM => {  
//                 expect(agentService.dataStore.wealinsAssetManager).toBeDefined();
//                 expect(agentService.dataStore.wealinsAssetManager.agtId).toEqual(wealinsAM.agtId);
//             });                
//     })));
    
//     it('getWealinsAssetManager method should retrieve data from datastore if it is filled in.', 
//         async(() => {                
//             let wealinsAM = new FullAgent();
//             wealinsAM.agtId = 'A01141';               
//             agentService.dataStore.wealinsAssetManager = wealinsAM;    
//             // First call, the datastore is empty                
//             agentService.getWealinsAssetManager().subscribe(wealinsAM => {  
//                 expect(agentService.dataStore.wealinsAssetManager).toBeDefined();
//                 expect(agentService.dataStore.wealinsAssetManager.agtId).toEqual(wealinsAM.agtId);
//             });                
//     }));

//     it('saveAgentContact method should retrieve data from datastore if it is filled in.', 
//         async(() => {                
//             let wealinsAM = new FullAgent();
//             wealinsAM.agtId = 'A01141';               
//             agentService.dataStore.wealinsAssetManager = wealinsAM;    
//             // First call, the datastore is empty                
//             agentService.getWealinsAssetManager().subscribe(wealinsAM => {  
//                 expect(agentService.dataStore.wealinsAssetManager).toBeDefined();
//                 expect(agentService.dataStore.wealinsAssetManager.agtId).toEqual(wealinsAM.agtId);
//             });                
//     }));
// });
