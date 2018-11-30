
-- 142,143 (label 13)

--select max(check_id) from CHECK_WORKFLOW; -- 176

SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (177,'The broker change involves a change of fees','if fees increase, the policyholder agrees with','YesNo',NULL,NULL,NULL);

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
values (178,'The new broker is in order for Wealins',NULL,'YesNo',NULL,NULL,NULL);

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
values (179,'If the broker is based in france : does the agreement include a "droit de suite"?',NULL,'YesNoNa',NULL,NULL,NULL);

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
values (180,'If the old broker had an e-banking acces : is his access stopped?',Null,'YesNoNa',NULL,NULL,NULL);

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
values (181,'If sending rules are mail to agent, do we have a request signed by the policyholder for changing the correspondance adress?', 'is correspondance adress updated?','YesNoNa',NULL,NULL,NULL);

SET IDENTITY_INSERT CHECK_WORKFLOW ON;

-- modif libellé "is his acces stopped" en "has his acces been removed"
update CHECK_WORKFLOW set CHECK_DESC = 'If the old broker had an e-banking acces : has his acces been removed?' where CHECK_ID = 180;


insert into CHECK_STEP Values (142,72,1,1,10,0,13,NULL,NULL);
insert into CHECK_STEP Values (143,72,1,1,20,0,13,NULL,NULL);
insert into CHECK_STEP Values (177,72,1,1,30,0,13,NULL,NULL);
insert into CHECK_STEP Values (178,72,1,1,40,0,13,NULL,NULL);
insert into CHECK_STEP Values (179,72,1,1,50,0,13,NULL,NULL);
insert into CHECK_STEP Values (180,72,1,1,60,0,13,NULL,NULL);
insert into CHECK_STEP Values (181,72,1,1,70,0,13,NULL,NULL);

-- 157 (label 8)

insert into CHECK_STEP Values (157,72,1,1,10,0,8,NULL,'STEP_ACCEPTANCE');

--153, 154, 155 (label 27)

insert into CHECK_STEP Values (153,72,1,1,10,0,27,NULL,NULL);
insert into CHECK_STEP Values (154,72,1,1,20,0,27,NULL,NULL);
insert into CHECK_STEP Values (155,72,1,1,30,0,27,NULL,NULL);

-- Validate 
insert into CHECK_STEP Values (25,73,1,0,10,0,13,NULL,NULL);
-- select * from check_step where step_id = 31

-- Check documentation 79
insert into CHECK_STEP values (119,79,1,1,50,0,13,NULL,NULL);
insert into CHECK_STEP values (137,79,1,0,100,0,13,NULL,NULL);

-- Visualisation de la décision (label 22)
insert into CHECK_STEP values (79,79,0,0,10,0,22,'ISNOTNULL',NULL);
insert into CHECK_STEP values (108,79,0,0,20,0,22,'ISNOTNULL',NULL);
insert into CHECK_STEP values (123,79,0,0,20,0,25,'ISNOTNULL',NULL);
insert into CHECK_STEP values (125,79,0,0,10,0,25,'ISNOTNULL',NULL);


--select * from step where STEP_WORKFLOW = 'Premium Transfer Request' --79 --69 -- 33 --24

--select * from CHECK_STEP a, CHECK_WORKFLOW b where a.CHECK_ID = b.CHECK_ID and a.STEP_ID in (15)

-- Sending 80
insert into CHECK_STEP values (121,80,1,1,50,0,13,NULL,NULL);

-- Acceptance 74
Insert into CHECK_STEP values (79,74,1,1,10,0,22,NULL,'COMPLIANCE_ACCEPTED');
Insert into CHECK_STEP values (108,74,1,1,50,0,22,NULL,NULL);
Insert into CHECK_STEP values (157,74,0,0,10,0,13,NULL,NULL);

--select * from step where WORKFLOW_ITEM_TYPE_ID in (11,13)
--select * from CHECK_STEP, CHECK_WORKFLOW where step_id = 58 and CHECK_STEP.CHECK_ID = CHECK_WORKFLOW.CHECK_ID

-- Suppression question KYC (155) sur VSUP et CAP (conservé sur CBN)
Delete from CHECK_STEP where CHECK_ID = 155 and step_id in (54,72);

-- Suppression question FATCA sur CAP
Delete from CHECK_STEP where CHECK_ID = 154 and step_id in (72);


-- Request to Client/Partner 75
Insert into CHECK_STEP values (79,75,0,0,5,0,22,NULL,NULL);
Insert into CHECK_STEP values (108,75,0,0,10,0,22,NULL,NULL);
Insert into CHECK_STEP values (122,75,1,1,10,0,24,NULL,NULL);

-- Acceptance BIS  76
insert into CHECK_STEP values (108,76,0,0,10,0,22,NULL,NULL);
Insert into CHECK_STEP values (122,76,0,0,20,0,22,NULL,NULL);
Insert into CHECK_STEP values (123,76,1,1,20,0,25,NULL,NULL);
Insert into CHECK_STEP values (125,76,1,1,10,0,25,NULL,'COMPLIANCE_ACCEPTED');

/*
select * from STEP where STEP_ID in (30,54,72)

select * from CHECK_STEP where step_id in (69,79)
select * from CHECK_WORKFLOW where CHECK_DESC like '%PRIIP%'

select * from step 
*/