

-- Awaiting valuation automatic pour souscription
update step set STEP_AUTOMATIC = 1 where STEP_ID = 22;

-- Libellés 
update CHECK_WORKFLOW set CHECK_EXPLAIN = 'has correspondance address been updated?' where CHECK_ID = 181;

update CHECK_WORKFLOW set CHECK_EXPLAIN = 'if fees increase: we have a signed instruction from the policyholder' where CHECK_ID = 177;

update CHECK_WORKFLOW set CHECK_DESC = 'The new broker has signed an agrement with Wealins' where CHECK_ID = 178;

-- 1ère décision compliance à l'étape accpetance bis
Insert into CHECK_STEP  values (79, 76, 0,0,5,0,22,NULL,NULL);

