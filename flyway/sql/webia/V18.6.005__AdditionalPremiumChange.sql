-- Ajout étape follow-up Versement supplémentaire 
insert into step values('Follow-up',12,1,0,0,0);

-- 97  -- Questions Compliance en visu et score
insert into CHECK_STEP VALUES (169,97,0,0,10,0,18,NULL,NULL); -- MOV9
insert into CHECK_STEP VALUES (170,97,0,0,20,0,18,NULL,NULL); --MOV10
insert into CHECK_STEP VALUES (165,97,0,0,30,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (166,97,0,0,40,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (167,97,0,0,50,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (168,97,0,0,60,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (151,97,0,0,70,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (149,97,0,0,80,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (150,97,0,0,90,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (152,97,0,0,100,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (171,97,0,0,110,0,18,NULL,NULL);
insert into CHECK_STEP VALUES (112,97,0,0,900,0,18,NULL,NULL);

-- Question PRIIPS -- 182
SET IDENTITY_INSERT CHECK_WORKFLOW ON;

insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (182,'PRIIPS Receipt',NULL,'YesNoNa',NULL,NULL,'NO');

 insert into CHECK_WORKFLOW (CHECK_ID, CHECK_DESC, CHECK_EXPLAIN, CHECK_TYPE,DEFAULTVALUE,CHECKCODE,COMMENTIF)
 values (183,'Fees Split signed',NULL,'YesNoNa',NULL,NULL,'NO');

 
 SET IDENTITY_INSERT CHECK_WORKFLOW OFF;

 -- 182 remplace 139 
 Update CHECK_STEP set CHECK_ID = 182 where CHECK_ID = 139 and step_id in (select distinct step_id  from step where WORKFLOW_ITEM_type_id = 12);
 -- 183 remplace 111
 Update CHECK_STEP set CHECK_ID = 183 where CHECK_ID = 111 and step_id in (select distinct step_id  from step where WORKFLOW_ITEM_type_id = 12);

 -- Ajout commentaire en Review documentation 135 visible en check documentation 69
 insert into CHECK_STEP VALUES (135,69,0,0,10,0,13,'ISNOTNULL',NULL);

