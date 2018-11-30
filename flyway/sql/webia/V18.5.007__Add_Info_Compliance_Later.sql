-- Ajout choix compliance 

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, DEFAULTVALUE) values('Step Acceptance after valuation',  'YesNo', 'NO' );

INSERT INTO CHECK_STEP VALUES (175,54,1,1,20,1,8,NULL,'LATER_ACCEPTANCE');

Update CHECK_STEP set CHECK_ORDER = 21 where CHECK_ID = 75 and STEP_ID = 54;


-- Ajout Check NAV sur Reviw Documentation

insert into CHECK_WORKFLOW(CHECK_DESC, CHECK_TYPE, COMMENTIF) values('NAV is checked', 'YesNo', NULL);

INSERT INTO CHECK_STEP VALUES (176,68,1,1,10,0,13,NULL,NULL);

