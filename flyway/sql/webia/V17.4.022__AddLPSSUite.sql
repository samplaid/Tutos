-- Ajout LPS sur Account Opening
update label set label_order = 45 where LABEL_ID = 8;

INSERT INTO CHECK_STEP
select a.CHECK_ID, 13, a.IS_UPDATABLE, a.IS_MANDATORY, replace(Checkcode,'LPS','')*10, a.IS_METADATA, 8, a.VISIBLEIF, a.METADATA
from check_step a, CHECK_WORKFLOW b where CHECKCODE like 'LPS%' and STEP_ID = 4 and a.check_id = b.CHECK_ID
and not exists(select * from CHECK_STEP b where b.CHECK_ID = a.CHECK_ID and STEP_ID = 13);

