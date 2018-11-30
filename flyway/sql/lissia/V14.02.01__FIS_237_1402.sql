
  CREATE TABLE  AGENT_BANK_ACCOUNTS  
    (AGB_ID                             int                  NOT NULL,   
     AGENT                              char(8)              NOT NULL,   
     ACCOUNT_NAME                       char(40)             NULL,   
     IBAN                               char(35)             NULL,   
     BIC                                char(35)             NULL,   
     BANK_NAME                          char(35)             NULL,   
     BANK_ADDRESS1                      char(40)             NULL,   
     BANK_ADDRESS2                      char(40)             NULL,   
     BANK_ADDRESS3                      char(40)             NULL,   
     COUNTRY                            char(3)              NULL,   
     COMMISSION_TYPE                    char(12)             NULL,   
     COMM_PAYMENT_TYPE                  char(12)             NULL,   
     COMM_PAYMENT_CURRENCY              char(12)             NULL,   
     STATUS                             smallint             NOT NULL,   
     NOTES                              char(250)            NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_TIME                       datetime             NULL,   
     CREATED_DATE                       datetime             NULL,   
     CREATED_PROCESS                    char(15)             NULL,   
     MODIFIED_BY                        char(5)              NULL,   
     MODIFIED_TIME                      datetime             NULL,   
     MODIFIED_DATE                      datetime             NULL,   
     MODIFIED_PROCESS                   char(15)             NULL,   
     FK_AGENTSAGT_ID                    char(8)              NULL,
    CONSTRAINT agb_id
    PRIMARY KEY NONCLUSTERED 
      (AGB_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000588 
    ON  AGENT_BANK_ACCOUNTS  
    (FK_AGENTSAGT_ID                      ) 

GO