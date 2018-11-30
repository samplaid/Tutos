insert into KEYCLOAK_ROLE (ID, CLIENT_REALM_CONSTRAINT, CLIENT_ROLE, DESCRIPTION, NAME, REALM_ID, CLIENT, REALM, SCOPE_PARAM_REQUIRED) 
	SELECT '2df6869e-5cbd-401e-9272-5a412d5175eb',ID ,'TRUE','Authorize commission page access in Webia application', 'WEBIA_APP_COMMISSION_MANAGEMENT', REALM_ID, ID ,null,'FALSE' from CLIENT where CLIENT_ID='wealins-front-applications' and REALM_ID='Wealins';


insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '2df6869e-5cbd-401e-9272-5a412d5175eb', ID  from KEYCLOAK_GROUP where name ='GG_BTD_Support' and REALM_ID='Wealins';
insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '2df6869e-5cbd-401e-9272-5a412d5175eb', ID  from KEYCLOAK_GROUP where name ='GG_BTD_TechnicalDevelopment' and REALM_ID='Wealins';
insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '2df6869e-5cbd-401e-9272-5a412d5175eb', ID  from KEYCLOAK_GROUP where name ='GG_BTD_BusinessAnalysis' and REALM_ID='Wealins';

insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '2df6869e-5cbd-401e-9272-5a412d5175eb', ID  from KEYCLOAK_GROUP where name ='GG_FIN_AccountingReporting' and REALM_ID='Wealins';


