

  CREATE TABLE  NLI_MAPPINGS_DETAILS_HISTORY  
    (NLH_ID                             DECIMAL(15)          NOT NULL,   
     TYPE                               smallint             NULL,   
     ACCOUNT                            char(12)             NOT NULL,   
     CENTRE                             char(14)             NULL,   
     CURRENCY                           char(4)              NULL,   
     COMPANY                            smallint             NULL,   
     BRANCH                             char(6)              NULL,   
     NL_ACCOUNT                         char(20)             NOT NULL,   
     STATUS                             smallint             NOT NULL,   
     CREATED_BY                         varchar(5)           NOT NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     CREATED_PROCESS                    varchar(15)          NOT NULL,   
     DISABLE_EXPORT                     smallint             NULL,   
     FK_NLI_MAPPINGSNLM_ID              char(45)             NULL,
    CONSTRAINT I0000616
    PRIMARY KEY NONCLUSTERED 
      (NLH_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000618 
    ON  NLI_MAPPINGS_DETAILS_HISTORY  
    (FK_NLI_MAPPINGSNLM_ID                ) 

GO

