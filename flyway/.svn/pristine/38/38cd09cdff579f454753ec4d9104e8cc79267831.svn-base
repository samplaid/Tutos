-- Ajout LPS sur Account Opening
update label set label_order = 45 where LABEL_ID = 8;

INSERT INTO CHECK_STEP
select a.CHECK_ID, 13, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID;

Insert into CHECK_STEP values(112,13,0,0,400,0,8,null,null);

-- Awaiting Account Opening 14
INSERT INTO CHECK_STEP
select a.CHECK_ID, 14, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID;

Insert into CHECK_STEP values(112,14,0,0,400,0,8,null,null);

-- Premium Transfer Request

INSERT INTO CHECK_STEP
select a.CHECK_ID, 15, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID;

Insert into CHECK_STEP values(112,15,0,0,400,0,8,null,null);


-- Check documentation
INSERT INTO CHECK_STEP
select a.CHECK_ID, 24, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID;

Insert into CHECK_STEP values(112,24,0,0,400,0,8,null,null);


-- Sending 
delete from check_step where step_id = 27 and label_id = 13 and check_id not in (121,106);

INSERT INTO CHECK_STEP
select a.CHECK_ID, 27, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID;

Insert into CHECK_STEP values(112,27,0,0,400,0,8,null,null);



-- Follow up
INSERT INTO CHECK_STEP
select a.CHECK_ID, 28, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID;

Insert into CHECK_STEP values(112,28,0,0,400,0,8,null,null);

Insert into CHECK_STEP values(98,28,0,0,390,0,8,null,null);

UPDATE CHECK_STEP set IS_UPDATABLE = 1 WHERE STEP_ID = 28 and CHECK_ID = 98;

