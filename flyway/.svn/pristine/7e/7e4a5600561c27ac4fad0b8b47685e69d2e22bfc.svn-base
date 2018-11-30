
DECLARE @registrationStepId INT = 52;
DECLARE @analysisStepId INT = 54;
DECLARE @waitingDispatchStepId INT = 53;
DECLARE @validateAnalysisStepId INT = 55;
DECLARE @acceptanceStepId INT = 56;
DECLARE @newStepId INT = 51;

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Part of previously accepted premium', 'YesNo', 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @newStepId, 1, 1, 1, 0, 13);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Comment', 'Text', 'GRP1');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 1, 0, 1, 0, 13);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The request is duly completed and signed', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @registrationStepId, 1, 1, 1, 0, 13);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @waitingDispatchStepId, 0, 0, 1, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The KYC is duly completed and signed', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(3, @registrationStepId, 1, 1, 2, 0, 13);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(3, @waitingDispatchStepId, 0, 0, 2, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Portfolio statement (if asset transfer)', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(4, @registrationStepId, 1, 1, 1, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(4, @waitingDispatchStepId, 0, 0, 1, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('PRIIPS Receipt', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(139, @registrationStepId, 1, 1, 2, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(139, @waitingDispatchStepId, 0, 0, 2, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Transfer letter', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(5, @registrationStepId, 1, 1, 3, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(5, @waitingDispatchStepId, 0, 0, 3, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Pool document', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(6, @registrationStepId, 1, 1, 4, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(6, @waitingDispatchStepId, 0, 0, 4, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Certificate of regularisation', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(7, @registrationStepId, 1, 1, 5, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(7, @waitingDispatchStepId, 0, 0, 5, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Act of donation', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(8, @registrationStepId, 1, 1, 6, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(8, @waitingDispatchStepId, 0, 0, 6, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Medical questionnaire', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(14, @registrationStepId, 1, 1, 7, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(14, @waitingDispatchStepId, 0, 0, 7, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Side letter Private Equity', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(18, @registrationStepId, 1, 1, 8, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(18, @waitingDispatchStepId, 0, 0, 8, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Depositing of assets outside the EEA', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(19, @registrationStepId, 1, 1, 9, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(19, @waitingDispatchStepId, 0, 0, 9, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Key Investor document', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(22, @registrationStepId, 1, 1, 10, 0, 1);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(22, @waitingDispatchStepId, 0, 0, 10, 0, 4, 'NO');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Fees Split signed', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(111, @registrationStepId, 1, 1, 1, 0, 2);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Comment', 'Text', 'GRP1');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @registrationStepId, 1, 0, 2, 0, 2);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @waitingDispatchStepId, 0, 0, 11, 0, 4);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Comment', 'Text', 'GRP1');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(111, @waitingDispatchStepId, 0, 0, 1, 0, 2);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @waitingDispatchStepId, 1, 0, 2, 0, 2);

insert into LABEL(LABEL_DESC, LABEL_ORDER) values('Request form & ID', 10);
alter table CHECK_WORKFLOW alter column CHECKCODE varchar(20);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The request is duly completed and signed', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 1, 1, 1, 0, (SELECT IDENT_CURRENT('LABEL')));

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The KYC is duly completed and signed', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(3, @analysisStepId, 1, 1, 2, 0, 8);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The KYC elements and origin of funds information are completed with Internet searches', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(38, @analysisStepId, 1, 1, 3, 0, 8);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The Factiva/Safewatch checks are without any results', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(34, @analysisStepId, 1, 1, 4, 0, 8);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-11 The premium payment has one of the criteria : LPS-6, LPS-7, LPS-9', 'YesNo', 'MOV11');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 0, 1, 5, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 5, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 5, 0, 18, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-11 - LPS 6. The first premium is paid in cash, by cheque or physical remittance of securities', 'YesNo', 'MOV11-LPS6');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 1, 1, 6, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 6, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 6, 0, 18, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-11 - LPS 7. The first premium is paid via the account of a third party', 'YesNo', 'MOV11-LPS7');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 1, 1, 7, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 7, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 7, 0, 18, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-11 - LPS 9. The file presents another atypical element relating to the premium payment', 'YesNo', 'MOV11-LPS9');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 1, 1, 8, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 8, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 8, 0, 18, 'YES');

update CHECK_WORKFLOW set CHECK_DESC='MOV 15 - The terrorism / PEP check on the policyholder / insured person / beneficiary has indicated a positive alert' where CHECK_ID=152
--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV 15 - The terrorism / PEP check on the policyholder / insured person / beneficiary has indicated a positive alert', 'YesNo', 'MOV15');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(152, @analysisStepId, 0, 1, 9, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(152, @validateAnalysisStepId, 0, 0, 9, 0, 8, 'YES');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV 12 - The client practices a sensitive profession or belongs to a sensitive sector of activity', 'YesNo', 'MOV12');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(151, @analysisStepId, 0, 1, 10, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(151, @validateAnalysisStepId, 0, 0, 10, 0, 8, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-9 The annual premium or guarantie is doubled', 'YesNo', 'MOV9');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 0, 1, 11, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 11, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 11, 0, 18, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-10 The additional premium brings the contract value to 100,000.00 EUR or more', 'YesNo', 'MOV10');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 0, 1, 12, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 12, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 12, 0, 18, 'YES');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-13 The contract was subject to a STR', 'YesNo', 'MOV13');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(149, @analysisStepId, 0, 1, 13, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(149, @validateAnalysisStepId, 0, 0, 13, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(149, @acceptanceStepId, 0, 0, 13, 0, 18, 'YES');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-14 The requested transaction is atypical (cash payment, third party payment, etc.)', 'YesNo', 'MOV14');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(150, @analysisStepId, 1, 1, 14, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(150, @validateAnalysisStepId, 0, 0, 14, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values(150, @acceptanceStepId, 0, 0, 14, 0, 18, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('MOV-17 The contract shows frequent and unexplained movements of funds from different banking institutions or from different countries', 'YesNo', 'MOV17');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 1, 1, 15, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @validateAnalysisStepId, 0, 0, 15, 0, 8, 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @acceptanceStepId, 0, 0, 15, 0, 18, 'YES');

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('The funds originate from a country different than the client''s country of residence', 'YesNo', 'FUNDS_ORIGIN_CLIENT');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 0, 1, 16, 0, 8);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('The funds originate from a non-sensitive country', 'YesNo', 'COUNTRY_SENSITIVE');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 0, 1, 17, 0, 8);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Le dossier est en ordre fiscalement selon les indicateurs de non-conformité fiscale', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(39, @analysisStepId, 1, 1, 18, 0, 8);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Information to Compliance', 'YesNo', 'YES');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, METADATA) values(45, @analysisStepId, 1, 1, 19, 1, 8, 'STEP_ACCEPTANCE');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(45, @validateAnalysisStepId, 0, 0, 19, 0, 8);
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(45, @acceptanceStepId, 0, 0, 19, 0, 18);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Score', 'Number', 'SCORE');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(112, @validateAnalysisStepId, 0, 0, 1, 0, 24);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Score', 'Number', 'SCORE');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(112, @acceptanceStepId, 0, 0, 20, 0, 18);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Medical acceptance executed', 'YesNoNa', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(75, @analysisStepId, 1, 1, 20, 0, 8);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('Financial check done', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(68, @analysisStepId, 1, 1, 1, 0, 6);

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The additional premium does not result in a change in the fund classification', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values((SELECT IDENT_CURRENT('CHECK_WORKFLOW')), @analysisStepId, 1, 1, 2, 0, 6);

update CHECK_WORKFLOW set CHECKCODE='CTX7' where CHECK_ID=30
update CHECK_WORKFLOW set CHECKCODE='CTX8' where CHECK_ID=31
update CHECK_WORKFLOW set CHECKCODE='CTX9' where CHECK_ID=32
--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The Investment strategy is consistent with the policyholder's risk profile', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(30, @analysisStepId, 1, 1, 3, 0, 6);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The Note concerning investments in specific assets with particular risks is duly signed', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(31, @analysisStepId, 1, 1, 4, 0, 6);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The Appendix allowing a different CAA fund category is duly signed', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(32, @analysisStepId, 1, 1, 5, 0, 6);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The address on the request matches with the adress in Webia', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(153, @analysisStepId, 1, 1, 1, 0, 27);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('The contract / operation does not show any elements related to the USA (FATCA)', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(154, @analysisStepId, 1, 1, 2, 0, 27);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('We have a KYC dated after 2006.', 'YesNo', 'NO');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(155, @analysisStepId, 1, 1, 3, 0, 27);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Score', 'Number', 'SCORE');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(112, @analysisStepId, 0, 0, 1, 0, 24);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Comment', 'Text', 'GRP1');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(24, @analysisStepId, 1, 0, 2, 0, 24);

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('Decision', 'List', 'C_ACP');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, METADATA) values(79, @acceptanceStepId, 1, 1, 1, 1, 22, 'COMPLIANCE_ACCEPTED');

--insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, CHECKCODE) values('First decisionnal comments', 'Text', 'GRP1');
insert into CHECK_STEP(CHECK_ID, STEP_ID, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID) values(108, @acceptanceStepId, 1, 1, 2, 0, 22);