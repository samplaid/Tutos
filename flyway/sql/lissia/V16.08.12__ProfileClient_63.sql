INSERT INTO UOPT_DEFINITIONS (UDF_ID, NAME, STATUS, USEAGE, CREATED_BY, CREATED_DATE, CREATED_TIME,CREATED_PROCESS)
VALUES (63,'Profile Client', 1, 1, 'rvn', GETDATE(), GETDATE(),'WEBIA');

 INSERT INTO [UOPT_DETAILS] (UDD_ID, KEY_VALUE, DESCRIPTION, STATUS, DEFINITION, TYPE, CREATED_BY, CREATED_DATE, CREATED_TIME, CREATED_PROCESS, FK_UOPT_DEFINITUDF_ID, GROUP_ID, DEFAULT0)
  VALUES ('CONSERV','CONSERV','Conservative',1,63,0,'rvn',GETDATE(), GETDATE(), 'WEBIA', 63,'',0);
 
   INSERT INTO [UOPT_DETAILS] (UDD_ID, KEY_VALUE, DESCRIPTION, STATUS, DEFINITION, TYPE, CREATED_BY, CREATED_DATE, CREATED_TIME, CREATED_PROCESS, FK_UOPT_DEFINITUDF_ID, GROUP_ID, DEFAULT0)
  VALUES ('BALANCED','BALANCED','Balanced',1,63,0,'rvn',GETDATE(), GETDATE(), 'WEBIA', 63,'',0);
 
 INSERT INTO [UOPT_DETAILS] (UDD_ID, KEY_VALUE, DESCRIPTION, STATUS, DEFINITION, TYPE, CREATED_BY, CREATED_DATE, CREATED_TIME, CREATED_PROCESS, FK_UOPT_DEFINITUDF_ID, GROUP_ID, DEFAULT0)
  VALUES ('AGRESSIV','AGRESSIV','Agressive',1,63,0,'rvn',GETDATE(), GETDATE(), 'WEBIA', 63,'',0);