

  CREATE TABLE  POLICY_FND_HLDGS_HIST_DETAILS  
    (PHH_ID                             DECIMAL(15)          NOT NULL,   
     FUND                               char(8)              NOT NULL,   
     CREATED_BY                         char(10)             NULL,   
     CREATED_DATE                       datetime             NULL,   
     CREATED_TIME                       datetime             NULL,   
     CREATED_PROCESS                    char(15)             NULL,   
     STATUS                             smallint             NOT NULL,   
     UNITS                              DECIMAL(15, 6)       NULL,   
     HOLDING_NO                         smallint             NOT NULL,   
     FUND_TYPE                          smallint             NULL,   
     HOLDING_TYPE                       smallint             NULL,   
     EFFECTIVE_DATE                     datetime             NULL,   
     FK_POLICY_FUND_PFH_ID              DECIMAL(15)          NULL,
    CONSTRAINT I0000620
    PRIMARY KEY NONCLUSTERED 
      (PHH_ID                             )) 

GO


  CREATE NONCLUSTERED INDEX  I0000622 
    ON  POLICY_FND_HLDGS_HIST_DETAILS  
    (FK_POLICY_FUND_PFH_ID                ) 

GO



