

  CREATE TABLE  POLICY_PREM_HISTORY_DETAILS  
    (PPH_ID                             DECIMAL(15)          NOT NULL,   
     LAPSE_WARNING_1                    int                  NULL,   
     LAPSE_WARNING_2                    int                  NULL,   
     LAPSE_WARNING_3                    int                  NULL,   
     POLICY                             char(14)             NOT NULL,   
     COVERAGE                           smallint             NULL,   
     MODAL_PREMIUM                      DECIMAL(15, 2)       NULL,   
     CURRENCY                           varchar(5)           NULL,   
     FREQUENCY                          smallint             NULL,   
     DATE_NEXT_BILLED_PREMIUM           datetime             NULL,   
     DATE_NEXT_PREMIUM_DUE              datetime             NULL,   
     COLLECTION_METHOD_PREMIUM_1        smallint             NULL,   
     COLLECTION_METHOD                  smallint             NULL,   
     COLLECTION_SUB_METHOD              smallint             NULL,   
     BANK_ID                            DECIMAL(12)          NULL,   
     NEXT_ESC_NUMBER                    smallint             NULL,   
     CONTRIBUTION_PERCENTAGE            DECIMAL(10, 6)       NULL,   
     PREVIOUS_DATE_PAID_TO              datetime             NULL,   
     STATUS                             smallint             NOT NULL,   
     TOTAL_PREMIUM                      DECIMAL(15, 2)       NULL,   
     CREATED_BY                         varchar(5)           NOT NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     CREATED_PROCESS                    varchar(15)          NOT NULL,   
     ESC_POC_ADJUSTMENT                 DECIMAL(15, 2)       NULL,   
     DISABLE_ESCALATION                 smallint             NULL,   
     NET_PREMIUM                        DECIMAL(15, 2)       NULL,   
     MODIFIED_PREMIUM                   DECIMAL(15, 2)       NULL,   
     FK_POLICY_PREMIPCP_ID              char(18)             NULL,
    CONSTRAINT I0000617
    PRIMARY KEY NONCLUSTERED 
      (PPH_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0010616 
    ON  POLICY_PREM_HISTORY_DETAILS  
    (FK_POLICY_PREMIPCP_ID                ) 

GO

