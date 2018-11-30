import { Component, OnInit, Input, EventEmitter, Output, ViewChild, ElementRef, QueryList, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { StateMode, PaginationService, UserService } from "../../../utils/index";
import { UoptDetailService } from "../../../_services/uopt-detail.service";
import { Page, AssetManagerStrategy, AssetManagerStrategyStatus, Roles, CodeLabelType, CodeLabel, CodeLabelValue } from "../../../_models/index";
import { Subscription } from "rxjs/Subscription";
import { AgentService } from "../../agent.service";
import { defaultPageSize } from "../../../_models/constant";
import { WebiaService } from '../../../_services/index';
import { AmsStrategyService, RiskTypeDescription } from './am-strategies.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { AmsStrategyRuleService } from './am-strategy-rule.service';

@Component({
	selector: 'am-strategies',
	templateUrl: 'am-strategies.component.html',
	providers: [AmsStrategyService, AmsStrategyRuleService],
	styleUrls: ['./am-strategies.component.css'],
	encapsulation: ViewEncapsulation.None
})
export class AmStrategiesComponent implements OnInit, AfterViewInit {
	mode = StateMode.readonly;
	
	strategies: Array<AssetManagerStrategy>;
	riskTypes: Array<CodeLabel>;
	riskTypeDesc: Array<RiskTypeDescription>;
	riskClasses: Array<any>;

	status: string;
	strategyPage: Page;
	subs: Subscription[] = [];

	size: number = defaultPageSize / 2;
	newStrategy: AssetManagerStrategy;

	// Used to hide/show the new row during the search.
	// By default show on init
	searchText: string;

	///////////////// Input data ////////////////	
	@Input() agentId: string;

	@Input()
	set amStrategies(strategies: Array<AssetManagerStrategy>) {
		this.strategies = this.strategyService.createListIfNull(strategies);
		this.strategies = this.strategies.sort(this.strategyService.comparableByRiskType);
		this.amStrategiesChange.emit(this.strategies);
		let filteredItems = this.doSearch(this.searchText, this.strategies);
		this.strategyPage = this.strategyService.getFirstPage(this.size, filteredItems);		
	}
	get amStrategies() {
		return this.strategyService.createListIfNull(this.strategies);
	}

	@Input()
	set pageSize(size: number) {
		this.size = (size > 0) ? size : this.size;
	};

	@Output() amStrategiesChange: EventEmitter<Array<AssetManagerStrategy>> = new EventEmitter<Array<AssetManagerStrategy>>();

	@ViewChild('search') search: ElementRef;


	//////////////// Constructor ////////////////
	constructor(private uoptDetailService: UoptDetailService,
		private strategyService: AmsStrategyService,
		private strategyRuleService: AmsStrategyRuleService) {
		// Don't fetch data in a component constructor
		// Constructors should do no more than set the initial local variables to simple values.
		this.status = AssetManagerStrategyStatus.ENABLED;
		this.newStrategy = new AssetManagerStrategy();
	}

	/////////////// Angular methods //////////////////
	ngOnInit() {
		this.riskClasses = new Array<any>();
		this.riskTypes = new Array<CodeLabel>();

		this.subs.push(this.uoptDetailService.getRiskClasses().subscribe(riskClasses => this.riskClasses = riskClasses));
		this.subs.push(this.strategyService.getRiskTypeDescription().subscribe(riskTypeDesc => this.riskTypeDesc = riskTypeDesc));
		this.subs.push(this.strategyService.getRiskTypes().subscribe(riskTypes => this.riskTypes = riskTypes));

		this.mode = this.strategyService.getMode();
	}

	ngAfterViewInit(): void {
		Observable.fromEvent(this.search.nativeElement, 'keyup')
			.delay(250)
			.subscribe((inputEvent: KeyboardEvent) => this.searchItem((<HTMLInputElement>inputEvent.target).value));
	}

	/////////////// Our own methods //////////////////
	setStatusFilter(flag: boolean): void {
		this.status = this.strategyService.asStatus(flag);
		let strategies = this.doSearch(this.searchText, this.amStrategies);
		this.strategyPage = this.strategyService.getFirstPage(this.size, strategies);
	}

	pageChanged(pageNumber: number): void {
		let strategies = this.doSearch(this.searchText, this.amStrategies);
		this.strategyPage = this.strategyService.getPage(pageNumber, this.size, strategies);
	}

	/**
	 * Save the strategy
	 * @param newStrategy 
	 */
	save(newStrategy: AssetManagerStrategy): void {		
		if(newStrategy) {
			newStrategy.agentNo = this.agentId;
			this.strategyService.save(newStrategy).subscribe(strategy => {
				let foundItem = this.amStrategies.find((item: AssetManagerStrategy) => item.amsId === strategy.amsId);
				if(foundItem) {
					foundItem = strategy;
				} else {
					this.amStrategies = this.amStrategies.concat(strategy);
				}
				this.newStrategy = new AssetManagerStrategy();
				this.strategyService.messageService.addAlertSuccess("The asset manager strategy with risk type " + strategy.riskType + " and code " + strategy.riskProfile + " has been succesfully created.", true, 'agentCpt')
			}, error => {});
		}
	}

	clearNewEntry() {
		if(this.newStrategy){
			this.newStrategy.riskType = null;
			this.newStrategy.riskProfile = null;
			this.newStrategy.riskProfileDescription = null;
			this.newStrategy.classOfRisk = null;
		}
	}

	deactiveItem(strategy: AssetManagerStrategy): void {
		this.strategyService.deactiveItem(this.amStrategies.find(item => item.amsId === strategy.amsId));
		this.amStrategiesChange.emit(this.amStrategies);
		let strategies = this.doSearch(this.searchText, this.amStrategies);
		this.strategyPage = this.strategyService.getPage(this.strategyPage.number, this.size, strategies);
	}
		
	canDeactiveItem(strategy: AssetManagerStrategy): boolean {
		return this.strategyService.canDeactiveItem(strategy);
	}

	isValidItem(strategy: AssetManagerStrategy): boolean {
		return this.strategyService.isValidItem(strategy);
	}

	isActiveItem(strategy: AssetManagerStrategy): boolean {
		return this.strategyService.isActiveItem(strategy);
	}

	isReadOnlyMode(mode: string): boolean {
		return this.strategyService.isReadOnlyMode(mode);
	}

	hasProfilRiskType(strategy: AssetManagerStrategy): boolean {
		return this.strategyService.hasProfilRiskType(strategy);
	}

	searchItem(text: string): void {
		let strategies = this.doSearch(this.searchText, this.amStrategies);
		this.strategyPage = this.strategyService.getFirstPage(this.size, strategies);
	}

	toUpperCase(event: KeyboardEvent): void {
		(<HTMLInputElement>event.target).value = this.strategyRuleService.setRiskProfileToUpper((<HTMLInputElement>event.target).value);
	}

	clearSearch(): void {
		this.searchText = '';
		let strategies = this.strategyService.filterByStatus(this.status, this.amStrategies);
		this.strategyPage = this.strategyService.getFirstPage(this.size, strategies);
	}

	private doSearch(text: string, items: Array<AssetManagerStrategy>): Array<AssetManagerStrategy> {
		let filteredItems = this.strategyService.filterByStatus(this.status, items);
		return (text) ? this.strategyService.searchItem(this.status, text, filteredItems) : filteredItems;
	}

}