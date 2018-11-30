-- Rachat total

select * from step where WORKFLOW_ITEM_TYPE_ID = 15

-- Analysis -- 100
insert into CHECK_STEP values (142,100,1,1,10,0,28,NULL,NULL);
insert into CHECK_STEP values (143,100,1,1,20,0,28,NULL,NULL);
--insert into CHECK_STEP values (185,83,1,1,30,1,28,NULL,'ENOUGH_CASH');
insert into CHECK_STEP values (186,100,1,1,40,0,28,NULL,NULL);
insert into CHECK_STEP values (187,100,1,1,50,0,28,NULL,NULL);
insert into CHECK_STEP values (189,100,1,1,70,0,28,NULL,NULL);

insert into CHECK_STEP values (39,100,1,1,10,0,8,NULL,NULL);
insert into CHECK_STEP values (190,100,1,1,20,0,8,NULL,NULL);
insert into CHECK_STEP values (191,100,1,1,30,0,8,NULL,NULL);
insert into CHECK_STEP values (192,100,1,1,40,0,8,NULL,NULL);
insert into CHECK_STEP values (152,100,1,1,50,0,8,NULL,NULL);
insert into CHECK_STEP values (150,100,1,1,60,0,8,NULL,NULL);
insert into CHECK_STEP values (149,100,1,1,70,0,8,NULL,NULL);
insert into CHECK_STEP values (151,100,1,1,80,0,8,NULL,NULL);
insert into CHECK_STEP values (193,100,1,1,90,0,8,NULL,NULL);

-- Score
insert into CHECK_STEP values (112,100,1,1,1,0,24,NULL,NULL);

insert into CHECK_STEP values (153,100,1,1,10,0,27,NULL,NULL);


-- Déplacer GDPR consent 
UPDATE CHECK_STEP set label_id = 27 where CHECK_ID = 186 and STEP_ID = 100;

--select * from CHECK_STEP where step_id = 83 and CHECK_ID in (151,152)

UPDATE CHECK_STEP set IS_UPDATABLE = 0 where  step_id = 100 and CHECK_ID in (151,152);


-- Validate Analysis -- 101
insert into CHECK_STEP values (191,101,0,0,910,0,8,NULL,NULL);
insert into CHECK_STEP values (192,101,0,0,10,0,8,NULL,NULL);
insert into CHECK_STEP values (193,101,0,0,20,0,8,NULL,NULL);
insert into CHECK_STEP values (151,101,0,0,30,0,8,NULL,NULL);
insert into CHECK_STEP values (149,101,0,0,40,0,8,NULL,NULL);
insert into CHECK_STEP values (150,101,0,0,50,0,8,NULL,NULL);
insert into CHECK_STEP values (152,101,0,0,60,0,8,NULL,NULL);
insert into CHECK_STEP values (112,101,0,0,900,0,24,NULL,NULL);




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

-- MOV13 contextualiser, non modifiable
UPDATE CHECK_STEP set IS_UPDATABLE = 0 where CHECK_ID = 149 and IS_UPDATABLE = 1;


-- Question sur Securities transfer
SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (318,'Securities transfer before debit of financial fees',NULL,'YesNo',NULL,NULL,NULL);
 
  
SET IDENTITY_INSERT CHECK_WORKFLOW OFF;

-- Ajout à l'étape Analysis
insert into CHECK_STEP values (318,100,1,1,80,0,28,'G|SEC_TRANSFER==YES',NULL);

UPDATE CHECK_STEP set IS_UPDATABLE = 0 WHERE CHECK_ID = 112 and IS_UPDATABLE = 1;

/*
- Awaiting cash/transfer : 
	Si liquidation de titres : 
	- Did the Asset Manager sell the assets?
	- Did the Asset Manager take his fees?
	Si transfert de titres : 
	- Did the Deposit Bank transfer the assets?
	- Did the Deposit Bank take his fees?

*/
-- Question sur Securities transfer
SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (319,'Did the Asset Manager sell the assets?',NULL,'YesNo',NULL,NULL,NULL);
 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (320,'Did the Asset Manager take his fees?',NULL,'YesNo',NULL,NULL,NULL);
 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (321,'Did the Deposit Bank transfer the assets?',NULL,'YesNo',NULL,NULL,NULL);
 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (322,'Did the Deposit Bank take his fees?',NULL,'YesNo',NULL,NULL,NULL);
 
  
SET IDENTITY_INSERT CHECK_WORKFLOW OFF;

--105 Awaiting Cash/Transfer/Fees

-- Ajout à l'étape Awaiting Cash/Transfer/Fees
insert into CHECK_STEP values (319,105,1,1,10,0,8,NULL,NULL);
insert into CHECK_STEP values (320,105,1,1,20,0,8,NULL,NULL);

insert into CHECK_STEP values (321,105,1,1,30,0,8,'G|SEC_TRANSFER==YES',NULL);
insert into CHECK_STEP values (322,105,1,1,40,0,8,'G|SEC_TRANSFER==YES',NULL);


-- Check documentation 
Insert into CHECK_STEP values (119,112,1,1,50,0,13,NULL,NULL);

-- KYC & Compliance
Insert into CHECK_STEP values (149,112,0,0,40,0,8,NULL,NULL);
Insert into CHECK_STEP values (150,112,0,0,50,0,8,NULL,NULL);
Insert into CHECK_STEP values (151,112,0,0,30,0,8,NULL,NULL);
Insert into CHECK_STEP values (152,112,0,0,60,0,8,NULL,NULL);
Insert into CHECK_STEP values (192,112,0,0,10,0,8,NULL,NULL);
Insert into CHECK_STEP values (193,112,0,0,20,0,8,NULL,NULL);
Insert into CHECK_STEP values (112,112,0,0,900,0,8,NULL,NULL);

-- Validate documentation
INSERT INTO CHECK_STEP values (120,113,1,1,50,0,13,NULL,NULL);

-- Sending - 114
INSERT INTO CHECK_STEP values (121,114,1,1,50,0,13,NULL,NULL);

-- KYC & Compliance
Insert into CHECK_STEP values (149,114,0,0,40,0,8,NULL,NULL);
Insert into CHECK_STEP values (150,114,0,0,50,0,8,NULL,NULL);
Insert into CHECK_STEP values (151,114,0,0,30,0,8,NULL,NULL);
Insert into CHECK_STEP values (152,114,0,0,60,0,8,NULL,NULL);
Insert into CHECK_STEP values (192,114,0,0,10,0,8,NULL,NULL);
Insert into CHECK_STEP values (193,114,0,0,20,0,8,NULL,NULL);
Insert into CHECK_STEP values (112,114,0,0,900,0,8,NULL,NULL);


-- Question sur Payment
SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
  values (323,'Payment should be updated',NULL,'YesNo',NULL,NULL,NULL);


insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (324,'Financial fees directly debited by AM (without invoice)',NULL,'YesNo',NULL,NULL,NULL);
  
   
SET IDENTITY_INSERT CHECK_WORKFLOW OFF;

Insert into CHECK_STEP values (323,114,1,1,100,0,13,NULL,'PAYMENT_UPDATED');

Update CHECK_WORKFLOW set CHECK_DESC = 'Payment shouldn’t be updated' where CHECK_ID = 323;

-- Etape Analysis 
insert into CHECK_STEP values (324,100,1,1,100,0,28,NULL,NULL);

/*


Select *  from check_step where step_id in (114);


-- 

select * from step where step_workflow  like 'Send%'

select * from CHECK_STEP where step_id in (101)
*/


/*



select * from CHECK_WORKFLOW where check_desc like '%Payment%'



select * from label



insert into CHECK_STEP values (160,84,1,0,1,0,13,NULL,NULL);

delete from CHECK_STEP where STEP_ID = 84



select * from step where STEP_WORKFLOW like '%Validate doc%'

-- 




*/

--INSERT INTO CHECK_STEP (120,113,1,1,50,0,13,NULL,NULL);








