-- Contextualisation Light : ne montrer que si Part of previously accepted premium = No
-- Analysis : Documents 
insert into CHECK_STEP 
select check_id, 54, 1, 1, check_order, 0, 1, 'ISNULL',null
from CHECK_STEP
where STEP_ID = 54 and LABEL_ID = 11;

Update CHECK_STEP set VISIBLEIF = 'ISNULL&&G|LIGHT==NO' where STEP_ID = 54 and LABEL_ID = 1;

Update CHECK_STEP set VISIBLEIF = 'NO&&G|LIGHT==NO' where STEP_ID = 54 and LABEL_ID = 11;

delete from CHECK_STEP where STEP_ID = 54 and CHECK_ID = 162;

-- Par défaut No
Update CHECK_WORKFLOW set DEFAULTVALUE = 'NO' where CHECK_ID = 159;

-- Visu Part of previously accepted premium à Analysis si Yes
insert into CHECK_STEP values (159,54,0,0,10,0,13,'YES',NULL);

-- Missing documents updatable
Update CHECK_STEP set IS_UPDATABLE = 1, IS_MANDATORY = 1 where STEP_ID = 54 and LABEL_ID = 11;

-- Label Request form 
Update label set LABEL_ORDER = 8 where LABEL_ID = 28;


-- Info to compliance 
UPDATE CHECK_STEP set VISIBLEIF = 'G|LIGHT==NO' where CHECK_ID = 45 and STEP_ID = 54;

Delete from CHECK_STEP where CHECK_ID = 157 and STEP_ID = 54;

Insert into CHECK_STEP values (157, 54, 1,1,19,1,8,'G|LIGHT==YES','STEP_ACCEPTANCE');

UPDATE CHECK_STEP set VISIBLEIF = 'G|LIGHT==NO' where CHECK_ID = 45 and STEP_ID = 55;

Delete from CHECK_STEP where CHECK_ID = 157 and STEP_ID = 55;

Insert into CHECK_STEP values (157, 55, 0,0,19,0,8,'G|LIGHT==YES',NULL);


UPDATE CHECK_STEP set VISIBLEIF = 'G|LIGHT==NO' where CHECK_ID = 45 and STEP_ID = 56;

Delete from CHECK_STEP where CHECK_ID = 157 and STEP_ID = 56;

Insert into CHECK_STEP values (157, 56, 0,0,19,0,13,'G|LIGHT==YES',Null);