// import { TestBed, inject, async, ComponentFixture, fakeAsync, tick } from '@angular/core/testing';
// import { AgentBasicFormComponent } from "./agent-basic-form.component";
// import { UTILS_MOCK_PROVIDERS, subscribeMockServices, busyConfigMock, setUpConnection } from "../../../utils/services/mock/utils.mock";
// import { AgentService } from "../../agent.service";
// import { AgentIdentityFormComponent } from "../identity-form/agent-identity-form.component";
// import { AgentAddressFormComponent } from "../address/agent-address-form.component";
// import { CommonModule } from "@angular/common";
// import { FormsModule } from "@angular/forms";
// import { HttpModule } from "@angular/http";
// import { AgentModule, agentRoutes } from "../../index";
// import { ActivatedRoute } from "@angular/router";
// import { RouterTestingModule } from "@angular/router/testing";
// import { Observable } from "rxjs/Observable";
// import { MockBackend } from "@angular/http/testing";
// import { ApiService } from "../../../utils/index";
// import { FullAgent } from "../../../_models/index";
// import { StateMode } from "../../../utils/mode";

// const modulesConfig = 	{
// 							imports: [  AgentModule,
// 										RouterTestingModule.withRoutes(agentRoutes)
// 									 ],							
// 							providers: [ ...UTILS_MOCK_PROVIDERS]
// 						}

// describe('Agent basic form component', () => {
// 	let sub;
// 	let route: ActivatedRoute;
// 	beforeEach(async(() => {
// 		TestBed.configureTestingModule(modulesConfig).compileComponents();
// 		sub = subscribeMockServices();
// 		route = TestBed.get(ActivatedRoute);
// 		route.params = Observable.of({ id: 'A04093' });
// 	}));
		
// 	it('should load agent with a giving id on a modal initialization', fakeAsync(() => {
// 		let fixture: ComponentFixture<AgentBasicFormComponent> = TestBed.createComponent(AgentBasicFormComponent);
// 		fixture.detectChanges();
// 		tick(28);
// 		let comp:AgentBasicFormComponent = fixture.componentInstance;
// 		comp.inModal = true; //  in a mmodal
// 		comp.agentId = 'A04093'; // fake id
// 		comp.ngOnInit();
// 		tick();
// 		expect(comp.agent).toBeDefined();		
// 	}));

// 	it('should be able to decode url when called via a link.', fakeAsync(() => {
// 		const idRef = 'A04/093';
// 		route.params = Observable.of({ id: 'A04%2F093' });
// 		let backend = TestBed.get(MockBackend);
// 		let apiService = TestBed.get(ApiService);
// 		sub.getAgent.unsubscribe();	
// 		let agentMock: FullAgent = require('../../../../assets/mock-data/mock-agent-bk');
// 		agentMock.agtId = idRef;
		
// 		setUpConnection(backend, { body: agentMock, url: apiService.getURL('getAgent',idRef), method: 'get', status: 200 });
		
// 		let fixture: ComponentFixture<AgentBasicFormComponent> = TestBed.createComponent(AgentBasicFormComponent);
// 		fixture.detectChanges();	
// 		tick(28);
// 		let comp:AgentBasicFormComponent = fixture.componentInstance;		
// 		expect(comp.agentId).toEqual(idRef); // should replaced by a '/'
// 		expect(comp.agent).toBeDefined();
// 	}));

// 	it('should return an agent when the agentId is provided and the agent exists.', fakeAsync(() => {
// 		let backend = TestBed.get(MockBackend);
// 		let apiService = TestBed.get(ApiService);				
// 		sub.getAgent.unsubscribe();
// 		let agentMock: FullAgent = require('../../../../assets/mock-data/mock-agent-bk');
// 		agentMock.agtId = 'X0001';
// 		setUpConnection(backend, { body:  agentMock, url: apiService.getURL('getAgent', 'X0001'), method: 'get', status: 200 });
		
// 		////////////// create components //////////////////
// 		let fixture: ComponentFixture<AgentBasicFormComponent> = TestBed.createComponent(AgentBasicFormComponent);
// 		fixture.detectChanges();	
// 		tick();		
// 		let comp:AgentBasicFormComponent = fixture.componentInstance;
// 		comp.agentId = 'X0001'; // fake id
// 		comp.loadOrInitializeAgent(comp.agentId);
// 		tick();
		
// 		/////////////// Expect result  //////////////////
// 		expect(comp.agent.agtId).toEqual('X0001');
// 	}));

// 	it('should return an agent when the agentId is provided and the agent exists.' +
// 	   'Other than the readonly mode and if it is initialized, title should start with Up-date word ', fakeAsync(() => {
// 		let backend = TestBed.get(MockBackend);
// 		let apiService = TestBed.get(ApiService);				
// 		sub.getAgent.unsubscribe();
// 		let agentMock: FullAgent = require('../../../../assets/mock-data/mock-agent-bk');
// 		agentMock.agtId = 'X0001';
// 		setUpConnection(backend, { body:  agentMock, url: apiService.getURL('getAgent', 'X0001'), method: 'get', status: 200 });
		
// 		////////////// create components //////////////////
// 		let fixture: ComponentFixture<AgentBasicFormComponent> = TestBed.createComponent(AgentBasicFormComponent);
// 		fixture.detectChanges();	
// 		tick(28);		
// 		let comp:AgentBasicFormComponent = fixture.componentInstance;
// 		comp.agentId = 'X0001'; // fake id
// 		comp.title='X-SIR';
// 		comp.mode = StateMode.edit;
// 		comp.loadOrInitializeAgent(comp.agentId);
// 		tick();
		
// 		/////////////// Expect result  //////////////////
// 		expect(comp.agent.agtId).toEqual('X0001');
// 		expect(comp.title).toContain('Update');
// 	}));

// 	it('should return a new agent when the agentId is provided but the agent does not exist.', fakeAsync(() => {
// 		let backend = TestBed.get(MockBackend);
// 		let apiService = TestBed.get(ApiService);
// 		let fixture: ComponentFixture<AgentBasicFormComponent> = TestBed.createComponent(AgentBasicFormComponent);
// 		fixture.detectChanges();	
// 		tick(28);
// 		let comp:AgentBasicFormComponent = fixture.componentInstance;		
// 		comp.agentId = 'A04093'; // fake id
// 		sub.getAgent.unsubscribe();
// 		setUpConnection(backend, { body: undefined, url: apiService.getURL('getAgent', 'A04093'), method: 'get', status: 200 });
// 		comp.loadOrInitializeAgent(comp.agentId);
// 		tick();
// 		expect(comp.agent.agtId).toEqual(undefined);
// 		expect(comp.agent.status).toEqual(undefined);
// 	}));

// });
