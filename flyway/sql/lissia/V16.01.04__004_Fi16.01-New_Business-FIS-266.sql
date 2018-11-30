

ALTER TABLE POLICIES
ADD
     SCUDO                              smallint             NULL,   
     DATE_LAST_SCUDO                    datetime             NULL,   
     MANDATO_ALL_INCASSO                smallint             NULL,   
     BROKER_REF_CONTRACT                char(30)             NULL,   
     NO_COOL_OFF                        smallint             NULL
     
GO


  ALTER TABLE  POLICY_AGENT_SHARES 
  ADD
  	PARTNER_AUTHORIZED                 smallint             NULL
  	
GO
  
  CREATE TABLE  POLICY_BENEFICIARY_CLAUSES  
    (PBC_ID                             DECIMAL(15)          NOT NULL,   
     POLICY                             char(14)             NULL,   
     RANK                               smallint             NULL,   
     TYPE                               char(10)             NULL,   
     TYPE_OF_CLAUSE                     char(1)              NULL,   
     STATUS                             smallint             NULL,   
     TEXT_OF_CLAUSE                     char(255)            NULL,   
     CODE                               char(10)             NULL,   
     CREATED_PROCESS                    char(15)             NOT NULL,   
     MODIFY_PROCESS                     char(15)             NULL,   
     CREATED_BY                         char(5)              NOT NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     MODIFY_BY                          char(5)              NULL,   
     MODIFY_DATE                        datetime             NULL,   
     MODIFY_TIME                        datetime             NULL,   
     FK_POLICIESPOL_ID                  char(14)             NULL,
    CONSTRAINT I0000631
    PRIMARY KEY NONCLUSTERED 
      (PBC_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000633 
    ON  POLICY_BENEFICIARY_CLAUSES  
    (FK_POLICIESPOL_ID                    ) 

GO