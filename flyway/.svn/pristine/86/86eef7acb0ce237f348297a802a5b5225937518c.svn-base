-- create WEBIA_APP_COMPTA role
insert into KEYCLOAK_ROLE (ID, CLIENT_REALM_CONSTRAINT, CLIENT_ROLE, DESCRIPTION, NAME, REALM_ID, CLIENT, REALM, SCOPE_PARAM_REQUIRED) 
	SELECT '61bd4a1c-9198-44a3-9c86-fa05ccb5e879',ID ,'TRUE','Authorize Access to compta page in Webia application', 'WEBIA_APP_COMPTA', REALM_ID, ID ,null,'FALSE' from CLIENT where CLIENT_ID='wealins-front-applications' and REALM_ID='Wealins';

-- add WEBIA_APP_COMPTA role to IT_DEV and COMPTA group
insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '61bd4a1c-9198-44a3-9c86-fa05ccb5e879', ID  from KEYCLOAK_GROUP where name ='GG_BTD_Support' and REALM_ID='Wealins';
insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '61bd4a1c-9198-44a3-9c86-fa05ccb5e879', ID  from KEYCLOAK_GROUP where name ='GG_BTD_TechnicalDevelopment' and REALM_ID='Wealins';
insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '61bd4a1c-9198-44a3-9c86-fa05ccb5e879', ID  from KEYCLOAK_GROUP where name ='GG_BTD_BusinessAnalysis' and REALM_ID='Wealins';

insert into GROUP_ROLE_MAPPING  (ROLE_ID, GROUP_ID) select '61bd4a1c-9198-44a3-9c86-fa05ccb5e879', ID  from KEYCLOAK_GROUP where name ='GG_FIN_AccountingReporting' and REALM_ID='Wealins';