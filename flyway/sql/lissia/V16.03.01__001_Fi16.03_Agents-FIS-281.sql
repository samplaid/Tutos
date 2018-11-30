
  ALTER TABLE  AGENTS 
  ADD
       CRM_REFERERENCE                    char(50)             NULL,   
       CRM_STATUS                         char(20)             NULL,   
       PAYMENT_SUPERIOR_AGENT             smallint             NULL,   
       TITLE                              char(8)              NULL,   
       FIRSTNAME                          char(30)             NULL
       
  GO
  
  ALTER TABLE  AGENTS 
  ALTER COLUMN       ADDRESS_LINE_1                     char(50)             NULL  
  ALTER TABLE  AGENTS 
  ALTER COLUMN       ADDRESS_LINE_2                     char(50)             NULL  
  ALTER TABLE  AGENTS 
  ALTER COLUMN       ADDRESS_LINE_3                     char(50)             NULL 
  ALTER TABLE  AGENTS 
  ALTER COLUMN       ADDRESS_LINE_4                     char(50)             NULL  
  ALTER TABLE  AGENTS 
  ALTER COLUMN       TOWN                               char(50)             NULL
  ALTER TABLE  AGENTS 
  ALTER COLUMN       FAX                                char(25)             NULL   
  ALTER TABLE  AGENTS 
  ALTER COLUMN       TELEPHONE                          char(25)             NULL
  ALTER TABLE  AGENTS 
  ALTER COLUMN       MOBILE                             char(25)             NULL
       
 GO
 
   ALTER TABLE  AGENT_HISTORY_DETAILS
   ADD
        CRM_REFERERENCE                    char(50)             NULL,   
        CRM_STATUS                         char(20)             NULL,   
        PAYMENT_SUPERIOR_AGENT             smallint             NULL,   
        TITLE                              char(8)              NULL,   
        FIRSTNAME                          char(30)             NULL
        
  GO
  
  ALTER TABLE AGENT_BANK_ACCOUNTS
  ADD
       ACCOUNT_CURRENCY                   char(3)              NULL
       
  GO
  
    CREATE TABLE  AGENT_CONTACTS  
      (AGC_ID                             DECIMAL(15)          NOT NULL,   
       AGENT_NO                           char(8)              NOT NULL,   
       CONTACT_ID                         char(8)              NOT NULL,   
       STATUS                             char(1)              NOT NULL,   
       CONTACT_FUNCTION                   char(15)             NULL,   
       CREATED_BY                         char(5)              NULL,   
       CREATED_TIME                       datetime             NULL,   
       CREATED_DATE                       datetime             NULL,   
       CREATED_PROCESS                    char(15)             NULL,   
       MODIFIED_BY                        char(5)              NULL,   
       MODIFIED_TIME                      datetime             NULL,   
       MODIFIED_DATE                      datetime             NULL,   
       MODIFIED_PROCESS                   char(15)             NULL,   
       FK_AGENTSAGT_ID                    char(8)              NULL,
      CONSTRAINT I0000639
      PRIMARY KEY NONCLUSTERED 
        (AGC_ID                             )) 
  
GO

  CREATE TABLE  ASSET_MANAGER_STRATEGIES  
    (AMS_ID                             DECIMAL(15)          NOT NULL,   
     AGENT_NO                           char(8)              NOT NULL,   
     RISK_PROFILE                       char(8)              NOT NULL,   
     RISK_PROFILE_DESCRIPTION           char(80)             NOT NULL,   
     CLASS_OF_RISK                      char(1)              NULL,   
     STATUS                             char(1)              NOT NULL,   
     CREATED_PROCESS                    char(12)             NULL,   
     MODIFY_PROCESS                     char(15)             NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_DATE                       datetime             NULL,   
     CREATED_TIME                       datetime             NULL,   
     MODIFY_BY                          char(5)              NULL,   
     MODIFY_DATE                        datetime             NULL,   
     MODIFY_TIME                        datetime             NULL,   
     FK_AGENTSAGT_ID                    char(8)              NULL,
    CONSTRAINT I0000637
    PRIMARY KEY NONCLUSTERED 
      (AMS_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000641 
    ON  AGENT_CONTACTS  
    (FK_AGENTSAGT_ID                      ) 

GO

  CREATE NONCLUSTERED INDEX  I0000643 
    ON  ASSET_MANAGER_STRATEGIES  
    (FK_AGENTSAGT_ID                      ) 

GO