-- Contextualisation Light : ne montrer que si Part of previously accepted premium = No
Update CHECK_STEP set VISIBLEIF = 'G|LIGHT==NO' where STEP_ID = 54 and check_id in (161,164,3,38,34,144,153,154,155);


Delete from CHECK_STEP where STEP_ID = 54 and CHECK_ID in (3,161) and LABEL_ID = 11;

Update CHECK_STEP set check_id = 161 where CHECK_ID = 164;

update CHECK_WORKFLOW set CHECK_DESC = 'Free' where CHECK_ID = 164;

-- Review documentation - 68 Ajout Comment
--select * from CHECK_WORKFLOW where CHECK_DESC = 'Comment' and not exists(select * from check_step where CHECK_ID = CHECK_WORKFLOW.CHECK_ID)
insert into CHECK_STEP values (135,68,1,0,20,0,13,NULL,NULL);