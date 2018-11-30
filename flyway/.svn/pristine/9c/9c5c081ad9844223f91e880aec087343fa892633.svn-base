-- Rachat partiel
-- Ajout step technique 
INSERT INTO step values ('Awaiting Put in Force',14,0,1,0,0);


-- Security Transfer
update CHECK_WORKFLOW set CHECK_TYPE = 'YesNo' where CHECK_ID = 184;

SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (185,'There is enough cash on the account to pay the surrender','In case of asset transfer: there is enough cash on the account to pay the outstanding fees / taxes','YesNo',NULL,NULL,NULL);

--insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
-- values (186,'GDPR consent is signed by the insured person(s)',NULL,'YesNo',NULL,NULL,NULL);

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (187,'We received the original policy schedule or a declaration of loss',NULL,'YesNo',NULL,NULL,NULL); 

--188 free
 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (189,'The country of payment is not a sensitive country',NULL,'YesNo',NULL,NULL,NULL); 

SET IDENTITY_INSERT CHECK_WORKFLOW OFF;


/*
The request is duly completed and signed by the policyholders, insured persons, cession holders and/or acceptant beneficiaries --142
The signature(s) match with the ID card -- 143
There is enough cash on the account to pay the surrender --185
GDPR consent is signed by the insured person(s) -- 186
We received the original policy schedule or a declaration of loss -- 187
In case of asset transfer: there is enough cash on the account to pay the outstanding fees / taxes -- 188
The country of payment is not a sensitive country -- 189
*/

--delete from CHECK_STEP where STEP_ID = 83;


-- Analysis -- 83
insert into CHECK_STEP values (142,83,1,1,10,0,28,NULL,NULL);
insert into CHECK_STEP values (143,83,1,1,20,0,28,NULL,NULL);
insert into CHECK_STEP values (185,83,1,1,30,1,28,NULL,'ENOUGH_CASH');
insert into CHECK_STEP values (186,83,1,1,40,0,28,NULL,NULL);
insert into CHECK_STEP values (187,83,1,1,50,0,28,NULL,NULL);
insert into CHECK_STEP values (189,83,1,1,70,0,28,NULL,NULL);



/*
The contract is tax compliant following the tax compliance indicators	 -- 39
Is "fiscal info" completed?	--190
"Passage Compliance: -- 191
"	"File to compliance, if one of following criteria are true:  
- The surrender amount is higher than 250K
- The surrender amount is higher than 50% of the contract value 
- The surrender occurs within 6 months of the subscription
- Frequent surrenders are done on the contract, without knowing the reason
- Belgian residents: the surrender is done by asset transfer"
MOV 1: The surrender occurs within two years of the subscription -- 192
MOV 15 - The terrorism / PEP check on the policyholder / insured person / beneficiary has indicated a positive alert -- 152	 
MOV 14 - The requested operation is atypical (cash payment via third party account etc.)	(mais pas sûre si on veut garder toutes ces questions) --150
MOV 13 - The contract was declared to the public prosecutor	--149
MOV 12- The client practices a sensitive profession/activity -- 151	
MOV 2 The surrender leads to eeconomically disproportionate penalties	-- 193
*/


SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (190,'Is "fiscal info" completed?',NULL,'YesNo',NULL,NULL,NULL);

 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (191,'Information to Compliance','File to compliance, if one of following criteria are true:  
- The surrender amount is higher than 250K
- The surrender amount is higher than 50% of the contract value 
- The surrender occurs within 6 months of the subscription
- Frequent surrenders are done on the contract, without knowing the reason
- Belgian residents: the surrender is done by asset transfer','YesNo',NULL,NULL,'YES');

 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (192,'MOV 1 - The surrender occurs within two years of the subscription',NULL,'YesNo',NULL,'MOV1',NULL);

  insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (193,'MOV 2 -The surrender leads to economically disproportionate penalties',NULL,'YesNo',NULL,'MOV2',NULL);

SET IDENTITY_INSERT CHECK_WORKFLOW OFF;

UPDATE CHECK_WORKFLOW set CHECKCODE = 'STEP_ACCEPTANCE' where  CHECK_ID = 191;


insert into CHECK_STEP values (39,83,1,1,10,0,8,NULL,NULL);
insert into CHECK_STEP values (190,83,1,1,20,0,8,NULL,NULL);
insert into CHECK_STEP values (191,83,1,1,30,0,8,NULL,NULL);
insert into CHECK_STEP values (192,83,1,1,40,0,8,NULL,NULL);
insert into CHECK_STEP values (152,83,1,1,50,0,8,NULL,NULL);
insert into CHECK_STEP values (150,83,1,1,60,0,8,NULL,NULL);
insert into CHECK_STEP values (149,83,1,1,70,0,8,NULL,NULL);
insert into CHECK_STEP values (151,83,1,1,80,0,8,NULL,NULL);
insert into CHECK_STEP values (193,83,1,1,90,0,8,NULL,NULL);

-- Score
insert into CHECK_STEP values (112,83,1,1,1,0,24,NULL,NULL);


select * from step where STEP_WORKFLOW = 'Analysis'

select * from CHECK_STEP, CHECK_WORKFLOW where step_id = 54 and CHECK_STEP.CHECK_ID = CHECK_WORKFLOW.CHECK_ID and CHECK_DESC like 'The add%'

SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (188,'The surrender does not result in a change in the fund classification',NULL,'YesNo',NULL,NULL,NULL);
  
SET IDENTITY_INSERT CHECK_WORKFLOW OFF;


insert into CHECK_STEP values (188,83,1,1,10,0,6,NULL,NULL);

/*
policy check	
	The address on the request matches with the adress in Webia
	--The contract / operation does not show any elements related to the USA (FATCA)  - A garder ???
fund	
	The surrender does not result in a change in the fund classification

	*/


insert into CHECK_STEP values (153,83,1,1,10,0,27,NULL,NULL);

/*
The beneficiary of the payment is the policyholder, cession holder and/or acceptant beneficiary MOV 3
MOV 16 - Request for payment in cash or by cheque
MOV-17 The contract shows frequent and unexplained movements of funds from different banking institutions or from different countries --177
The Factiva/Safewatch checks are without any results
"In case of pledge, we received a release of pledge for the surrender --> MOV-7 The contract was pledged after subscription
+ Infobulle: release of pledge to be requested ?? "

*/

select * from CHECK_WORKFLOW where check_desc like '%MOV%'


SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (194,'MOV 3 - The beneficiary of the payment is the policyholder, cession holder and/or acceptant beneficiary',NULL,'YesNo','Yes','MOV3',NULL);

 
insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (194,'MOV 16 -Request for payment in cash or by cheque',NULL,'YesNo','No','MOV16',NULL);

  
SET IDENTITY_INSERT CHECK_WORKFLOW OFF;


-- Déplacer GDPR consent 
UPDATE CHECK_STEP set label_id = 27 where CHECK_ID = 186 and STEP_ID = 83;

--select * from CHECK_STEP where step_id = 83 and CHECK_ID in (151,152)

UPDATE CHECK_STEP set IS_UPDATABLE = 0 where  step_id = 83 and CHECK_ID in (151,152);

-- MOV2 à NO par défaut
UPDATE CHECK_WORKFLOW set DEFAULTVALUE = 'NO' where CHECK_ID = 193;

select * from CHECK_WORKFLOW where CHECK_DESC like 'MOV%'


-- Acceptance  step : 85
insert into CHECK_STEP values (191,85,0,0,910,0,13,NULL,NULL);
insert into CHECK_STEP values (192,85,0,0,10,0,18,NULL,NULL);
insert into CHECK_STEP values (193,85,0,0,20,0,18,NULL,NULL);
insert into CHECK_STEP values (151,85,0,0,30,0,18,NULL,NULL);
insert into CHECK_STEP values (149,85,0,0,40,0,18,NULL,NULL);
insert into CHECK_STEP values (150,85,0,0,50,0,18,NULL,NULL);
insert into CHECK_STEP values (152,85,0,0,60,0,18,NULL,NULL);
insert into CHECK_STEP values (112,85,0,0,900,0,18,NULL,NULL);

insert into CHECK_STEP values (108,85,1,1,20,0,22,NULL,NULL);
insert into CHECK_STEP values (79,85,1,1,10,1,22,NULL,'COMPLIANCE_ACCEPTED');

-- Request to Client/Partner 86
insert into CHECK_STEP values (79,86,0,0,5,0,22,NULL,NULL);
insert into CHECK_STEP values (108,86,0,0,10,0,22,NULL,NULL);
insert into CHECK_STEP values (122,86,1,1,10,0,24,NULL,NULL);

-- Validate Analysis -- 84
insert into CHECK_STEP values (191,84,0,0,910,0,8,NULL,NULL);
insert into CHECK_STEP values (192,84,0,0,10,0,8,NULL,NULL);
insert into CHECK_STEP values (193,84,0,0,20,0,8,NULL,NULL);
insert into CHECK_STEP values (151,84,0,0,30,0,8,NULL,NULL);
insert into CHECK_STEP values (149,84,0,0,40,0,8,NULL,NULL);
insert into CHECK_STEP values (150,84,0,0,50,0,8,NULL,NULL);
insert into CHECK_STEP values (152,84,0,0,60,0,8,NULL,NULL);
insert into CHECK_STEP values (112,84,0,0,900,0,24,NULL,NULL);

-- Complete Analysis -- 88
insert into CHECK_STEP values (191,88,0,0,910,0,8,NULL,NULL);
insert into CHECK_STEP values (192,88,0,0,10,0,8,NULL,NULL);
insert into CHECK_STEP values (193,88,0,0,20,0,8,NULL,NULL);
insert into CHECK_STEP values (151,88,0,0,30,0,8,NULL,NULL);
insert into CHECK_STEP values (149,88,0,0,40,0,8,NULL,NULL);
insert into CHECK_STEP values (150,88,0,0,50,0,8,NULL,NULL);
insert into CHECK_STEP values (152,88,0,0,60,0,8,NULL,NULL);
insert into CHECK_STEP values (112,88,0,0,900,0,24,NULL,NULL);

insert into CHECK_STEP values (79,88,0,0,10,0,22,'ISNOTNULL',NULL);
insert into CHECK_STEP values (108,88,0,0,20,0,22,'ISNOTNULL',NULL);

--insert into CHECK_STEP values (79,88,0,0,10,0,25,'ISNOTNULL',NULL);
--insert into CHECK_STEP values (108,88,0,0,20,0,25,'ISNOTNULL',NULL);

-- Step Review Documentation
insert into CHECK_STEP values (135,92,1,0,20,0,13,NULL,NULL);
insert into CHECK_STEP values (176,92,1,1,10,0,13,NULL,NULL);

-- Step Awaiting Cash/Transfer 90
-- MOV
insert into CHECK_STEP values (151,90,0,0,30,0,8,NULL,NULL);
insert into CHECK_STEP values (149,90,0,0,40,0,8,NULL,NULL);
insert into CHECK_STEP values (150,90,0,0,50,0,8,NULL,NULL);
insert into CHECK_STEP values (152,90,0,0,60,0,8,NULL,NULL);
insert into CHECK_STEP values (112,90,0,0,900,0,24,NULL,NULL);
insert into CHECK_STEP values (192,90,0,0,10,0,8,NULL,NULL);
insert into CHECK_STEP values (193,90,0,0,20,0,8,NULL,NULL);


SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (196,'There is enough cash on the account to pay the surrender',NULL,'YesNo',NULL,NULL,NULL);

 
insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (197,'Is the assets tranfer done?',NULL,'YesNo',NULL,NULL,NULL);

  
SET IDENTITY_INSERT CHECK_WORKFLOW OFF;

insert into CHECK_STEP values (196,90,1,1,10,0,13,'G|SEC_TRANSFER==NO',NULL);
insert into CHECK_STEP values (197,90,1,1,12,0,13,'G|SEC_TRANSFER==YES',NULL);


-- Step Validate Input -- 89
-- MOV
insert into CHECK_STEP values (151,89,0,0,30,0,8,NULL,NULL);
insert into CHECK_STEP values (149,89,0,0,40,0,8,NULL,NULL);
insert into CHECK_STEP values (150,89,0,0,50,0,8,NULL,NULL);
insert into CHECK_STEP values (152,89,0,0,60,0,8,NULL,NULL);
insert into CHECK_STEP values (112,89,0,0,900,0,24,NULL,NULL);
insert into CHECK_STEP values (192,89,0,0,10,0,8,NULL,NULL);
insert into CHECK_STEP values (193,89,0,0,20,0,8,NULL,NULL);


/*

select * from CHECK_WORKFLOW where check_desc like '%MOV%'



select * from label



insert into CHECK_STEP values (160,84,1,0,1,0,13,NULL,NULL);

delete from CHECK_STEP where STEP_ID = 84



select * from step where STEP_WORKFLOW like 'Valid%'

-- 

select a.*, b.CHECK_DESC from CHECK_STEP a, CHECK_WORKFLOW b where --STEP_ID in (88) and 
a.CHECK_ID = b.CHECK_ID and VISIBLEIF is not null
order by label_id

*/










