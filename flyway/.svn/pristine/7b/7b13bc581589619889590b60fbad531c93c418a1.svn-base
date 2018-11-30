alter table [dbo].[STEP]  ADD WORKFLOW_ITEM_TYPE_ID INT, STEP_REJECTABLE smallint NULL ; 
go
UPDATE [dbo].[STEP] set WORKFLOW_ITEM_TYPE_ID = 7;
go
UPDATE [dbo].[STEP] set STEP_REJECTABLE  = 0 where step_id in (1,22,23,24); 
go
UPDATE [dbo].[STEP] set STEP_REJECTABLE= 1 where step_id not in (1,22,23,24); 