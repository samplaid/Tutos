

  CREATE TABLE  AGENT_HIER_HISTORY_DETAILS  
    (AHD_ID                             DECIMAL(15)          NOT NULL,   
     RATE_ORIGIN                        smallint             NULL,   
     RATE_APPLICATION                   smallint             NULL,   
     WRITING_AGENT                      char(8)              NULL,   
     TYPE                               smallint             NULL,   
     AGENT_ID                           char(8)              NULL,   
     RATE                               DECIMAL(12, 7)       NULL,   
     START_DATE                         datetime             NULL,   
     END_DATE                           datetime             NULL,   
     STATUS                             smallint             NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_TIME                       datetime             NULL,   
     CREATED_DATE                       datetime             NULL,   
     CREATED_PROCESS                    char(15)             NULL,   
     FK_AGENT_HIERARAGH_ID              DECIMAL(15)          NULL,
    CONSTRAINT I0000608
    PRIMARY KEY NONCLUSTERED 
      (AHD_ID                             )) 

GO

  CREATE TABLE  GENERAL_NOTES_HISTORY_DETAILS  
    (PNH_ID                             DECIMAL(15)          NOT NULL,   
     POLICY                             char(14)             NOT NULL,   
     TYPE                               smallint             NULL,   
     COVERAGE                           smallint             NULL,   
     CREATED_PROCESS                    varchar(15)          NOT NULL,   
     STATUS                             smallint             NOT NULL,   
     CREATED_BY                         varchar(5)           NOT NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     DETAILS                            varchar(1000)        NULL,   
     LINK_TYPE                          smallint             NULL,   
     FK_GENERAL_NOTEPON_ID              DECIMAL(10)          NULL,
    CONSTRAINT I0000610
    PRIMARY KEY NONCLUSTERED 
      (PNH_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000612 
    ON  AGENT_HIER_HISTORY_DETAILS  
    (FK_AGENT_HIERARAGH_ID                ) 

GO

  CREATE NONCLUSTERED INDEX  I0000614 
    ON  GENERAL_NOTES_HISTORY_DETAILS  
    (FK_GENERAL_NOTEPON_ID                ) 

GO

