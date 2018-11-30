insert into step values ('Update Input',7,0);

-- 29

insert into CHECK_WORKFLOW values ('The data of the documentation are correct',null,'YesNo',NULL,NULL,NULL);
-- 140

insert into CHECK_STEP values (140,24,1,1,20,1,13,NULL,'CHECK_DATA');


-- LPS au step Update Input
INSERT INTO CHECK_STEP
select CHECK_ID, 29, IS_UPDATABLE, IS_MANDATORY, CHECK_ORDER, IS_METADATA, LABEL_ID, VISIBLEIF, METADATA
from CHECK_STEP where STEP_ID = 27 and CHECK_ORDER > 50;



