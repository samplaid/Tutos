// import { TestBed, async, ComponentFixture, inject, fakeAsync, tick } from "@angular/core/testing";
// import { RouterTestingModule } from '@angular/router/testing';
// import { FundModule, fundRoutes } from "./index";
// import { FundComponent } from "./fund.component";
// import { ActivatedRoute, Router, ActivatedRouteSnapshot } from "@angular/router";
// import { By } from "@angular/platform-browser";
// import { Observable } from "rxjs/Observable";
// import { Fund } from "../_models/fund";
// import { DateUtils } from "../utils/date-utils";
// import { subscribeMockServices, UTILS_MOCK_PROVIDERS } from "../utils/services/mock/utils.mock";

// describe('Fund component', () => {
//     beforeEach(async(()=>{
//         TestBed.configureTestingModule({
//             imports:[   FundModule,
//                         RouterTestingModule.withRoutes(fundRoutes)
//                     ],
//             declarations:[ ],
//             providers:  [ ...UTILS_MOCK_PROVIDERS ]
//         }).compileComponents();
//         subscribeMockServices();
//     }));
    
//     it('fields should be emptied except invest Cash Limit on FID creation mode', async(()=>{
//         let activatedRouted = TestBed.get(ActivatedRoute);
//         activatedRouted.params = Observable.of({id:''});
//         let snapshot: ActivatedRouteSnapshot = new ActivatedRouteSnapshot();
//         snapshot.data = {subType: 'FID'}
//         activatedRouted.snapshot = snapshot;

//         const fixture:ComponentFixture<FundComponent> = TestBed.createComponent(FundComponent);
//         fixture.detectChanges();              
//         fixture.whenStable().then(() => {
//             const component: FundComponent = fixture.componentInstance;
//             expect(!component._fundId).toBeTruthy();           
//             expect(component.fund.investCashLimit).toEqual(100);  
//             expect(!component.fund.fdsId).toBeTruthy(); 
//             expect(!component.fund.name).toBeTruthy(); 
//             expect(component.fund.status).toEqual(0); 
//             expect(!component.fund.pool).toBeTruthy(); 
//             expect(!component.fund.currency).toBeTruthy(); 
//             expect(!component.fund.depositBank).toBeTruthy(); 
//             expect(!component.fund.accountRoot).toBeTruthy(); 
//             expect(!component.fund.iban).toBeTruthy(); 
//             expect(!component.fund.depositAccount).toBeTruthy(); 
//             expect(!component.fund.bankDepositFee).toBeTruthy(); 
//             expect(!component.fund.depositBankFlatFee).toBeTruthy(); 
//             expect(!component.fund.bankDepFeeCcy).toBeTruthy(); 
//             expect(!component.fund.financialAdvisor).toBeTruthy(); 
//             expect(!component.fund.finAdvisorFee).toBeTruthy(); 
//             expect(!component.fund.groupingCode).toBeTruthy(); 
//             expect(!component.fund.fundClassification).toBeTruthy(); 
//             expect(!component.fund.hasTransaction).toBeTruthy(); 
//             expect(!component.fund.assetManagerFee).toBeTruthy(); 
//             expect(!component.fund.finFeesFlatAmount).toBeTruthy(); 
//             expect(!component.fund.assetManFeeCcy).toBeTruthy(); 
//             expect(!component.fund.assetManager).toBeTruthy(); 
//             expect(!component.fund.finFeesMinAmount).toBeTruthy(); 
//             expect(!component.fund.finFeesMaxAmount).toBeTruthy(); 
//             expect(!component.fund.assetManFeeCcy).toBeTruthy(); 
//             expect(!component.fund.performanceFee).toBeTruthy(); 
//             expect(!component.fund.privateEquity).toBeTruthy(); 
//             expect(!component.fund.privateEquityFee).toBeTruthy(); 
//             expect(!component.fund.riskProfileDate).toBeTruthy(); 
//             expect(!component.fund.assetManRiskProfile).toBeTruthy(); 
//             expect(!component.fund.riskProfile).toBeTruthy(); 
//             expect(!component.fund.classOfRisk).toBeTruthy(); 
//             expect(!component.fund.riskCurrency).toBeTruthy(); 
//             expect(!component.fund.investCat).toBeTruthy(); 
//             expect(!component.fund.alternativeFunds).toBeTruthy(); 
//             expect(!component.fund.poc).toBeTruthy(); 
//             expect(!component.fund.poa).toBeTruthy(); 
//             expect(!component.fund.poaType).toBeTruthy(); 
//             expect(!component.fund.mandateHolder).toBeTruthy(); 
//             expect(!component.fund.poaDate).toBeTruthy(); 
//             expect(!component.fund.notes).toBeTruthy(); 

//             // prices date is initialized to the data of today.
//             let compiled = fixture.debugElement.query(By.css('fund-prices input[name="dp"]'));
//             let priceDate:HTMLInputElement = compiled.nativeElement;            
//             expect(priceDate.value).toBe(DateUtils.formatToddMMyyyy(new Date(),'/'));
            
//         });
//     }));


//     it('should show the class risk when the risk profile is selected', async(()=>{
//         let activatedRouted = TestBed.get(ActivatedRoute);
//         activatedRouted.params = Observable.of({id:''});
//         let snapshot: ActivatedRouteSnapshot = new ActivatedRouteSnapshot();
//         snapshot.data = {subType: 'FID'}
//         activatedRouted.snapshot = snapshot;

//         const fixture:ComponentFixture<FundComponent> = TestBed.createComponent(FundComponent);
//         fixture.detectChanges();               
//         fixture.whenStable().then(() => {
//         const component: FundComponent = fixture.componentInstance;
//             let compiled = fixture.debugElement.query(By.css('#riskProfile'));
//             let riskProfile:HTMLSelectElement = compiled.nativeElement;
            
//             expect(component.riskProfileList.length).toBeGreaterThanOrEqual(1);
//             riskProfile.selectedIndex = 0;
//             riskProfile.dispatchEvent(new Event('change'));
//             fixture.detectChanges();      
//             compiled = fixture.debugElement.query(By.css('#classOfRisk'));
//             expect(compiled).toBeDefined();
//         });
//     }));

//     it('should show the Private Equity fees when the Private Equity is selected and should not show the private equity in otherwise.', async(()=>{
//         let activatedRouted = TestBed.get(ActivatedRoute);
//         activatedRouted.params = Observable.of({id:''});
//         let snapshot: ActivatedRouteSnapshot = new ActivatedRouteSnapshot();
//         snapshot.data = {subType: 'FID'}
//         activatedRouted.snapshot = snapshot;

//         const fixture:ComponentFixture<FundComponent> = TestBed.createComponent(FundComponent);
//         fixture.detectChanges();               
//         fixture.whenStable().then(() => {
//             const component: FundComponent = fixture.componentInstance;
//             let compiled = fixture.debugElement.query(By.css('#privateEquity'));
//             let privateEquity:HTMLInputElement = compiled.nativeElement;
            
//             expect(component.riskProfileList.length).toBeGreaterThanOrEqual(1);
//             privateEquity.checked = true;
//             privateEquity.dispatchEvent(new Event('change'));
//             fixture.detectChanges();      
//             compiled = fixture.debugElement.query(By.css('.privateEquity-fees'));
//             expect(compiled).toBeDefined();

//             privateEquity.checked = false;
//             privateEquity.dispatchEvent(new Event('change'));
//             fixture.detectChanges();      
//             compiled = fixture.debugElement.query(By.css('.privateEquity-fees'));
//             expect(!compiled).toBeTruthy();
//         });
//     }));

//     it('should run the test', async(()=>{
//         let activatedRouted = TestBed.get(ActivatedRoute);
//         activatedRouted.params = Observable.of({id:''});
//         let snapshot: ActivatedRouteSnapshot = new ActivatedRouteSnapshot();
//         snapshot.data = {subType: 'FID'}
//         activatedRouted.snapshot = snapshot;

//         const fixture:ComponentFixture<FundComponent> = TestBed.createComponent(FundComponent);
//         fixture.detectChanges();               
//         fixture.whenStable().then(() => {
//             const component: FundComponent = fixture.componentInstance;
//             let compiled = fixture.debugElement.query(By.css('#privateEquity'));
//             let privateEquity:HTMLInputElement = compiled.nativeElement;
            
//             expect(component.riskProfileList.length).toBeGreaterThanOrEqual(1);
//             privateEquity.checked = true;
//             privateEquity.dispatchEvent(new Event('change'));
//             fixture.detectChanges();      
//             compiled = fixture.debugElement.query(By.css('.privateEquity-fees'));
//             expect(compiled).toBeDefined();

//             privateEquity.checked = false;
//             privateEquity.dispatchEvent(new Event('change'));
//             fixture.detectChanges();      
//             compiled = fixture.debugElement.query(By.css('.privateEquity-fees'));
//             expect(!compiled).toBeTruthy();
//         });
//     }));

// });