-- MOV1 non updatable : 192

Update CHECK_STEP set IS_UPDATABLE = 0 WHERE CHECK_ID = 192 and IS_UPDATABLE = 1;

-- Suppression Comment Validate Analysis (84)
delete from CHECK_STEP where STEP_ID = 84 and CHECK_ID = 160;

-- Acceptance Bis(87)
Insert into CHECK_STEP
select check_id, 87, IS_UPDATABLE, IS_MANDATORY,CHECK_ORDER,IS_METADATA,LABEL_ID,VISIBLEIF,METADATA
from CHECK_STEP
where step_id=76;

Insert into CHECK_STEP
select check_id, 104, IS_UPDATABLE, IS_MANDATORY,CHECK_ORDER,IS_METADATA,LABEL_ID,VISIBLEIF,METADATA
from CHECK_STEP
where step_id=76;

-- Complete Analysis 88 - 106
Insert into CHECK_STEP values (125,88,0,0,10,0,25,'ISNOTNULL',NULL);
Insert into CHECK_STEP values (123,88,0,0,20,0,25,'ISNOTNULL',NULL);

-- Suppression Comment Review documentation - 92
delete from CHECK_STEP where STEP_ID = 92 and CHECK_ID = 135;

-- Check documentation - 94
Insert into CHECK_STEP values (119,94,1,1,50,0,13,NULL,NULL);

Insert into CHECK_STEP
select check_id, 94, IS_UPDATABLE, IS_MANDATORY,CHECK_ORDER,IS_METADATA,LABEL_ID,VISIBLEIF,METADATA
from CHECK_STEP
where STEP_ID in (88) and LABEL_ID in (8,24) and CHECK_ID not in (191);



--





select * from CHECK_STEP where STEP_ID in (88) and LABEL_ID = 8;

select * from step where STEP_WORKFLOW like 'Compl%'



select * from check_step where CHECK_ID in (69)

select * from check_workflow where CHECK_DESC like '%cash%'
select * from label

Select * from CHECK_STEP where STEP_ID in (88)

Select * from step where STEP_WORKFLOW like 'Comple%'
