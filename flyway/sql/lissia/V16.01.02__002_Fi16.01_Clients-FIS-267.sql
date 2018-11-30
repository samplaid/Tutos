

  ALTER TABLE  CLIENTS  
  ADD
     VAT_NUMBER                         char(20)             NULL,   
     CIRCULAR_LETTER                    char(10)             NULL,   
     CLASS_OF_RISK                      char(1)              NULL,   
     STATEMENT_BY_EMAIL                 char(1)              NULL,   
     MEDIA_EXPOSED_PERSON               char(1)              NULL,   
     MEP_DETAIL                         char(50)             NULL,   
     RELATIVE_CLOSE_ASSOC               char(1)              NULL,   
     RCA_DETAIL                         char(50)             NULL,   
     CRS_STATUS                         char(10)             NULL,   
     CRS_EXACT_STATUS                   char(10)             NULL,   
     COMMERCIAL_ENTITY                  char(1)              NULL
     
  ALTER TABLE  CLIENT_ENDORSEMENTS
  ADD
     NATIONAL_ID_NO 			char(20)             NULL,   
     VAT_NUMBER                         char(20)             NULL,   
     CIRCULAR_LETTER                    char(10)             NULL,   
     CLASS_OF_RISK                      char(1)              NULL,   
     STATEMENT_BY_EMAIL                 char(1)              NULL,   
     MEDIA_EXPOSED_PERSON               char(1)              NULL,   
     MEP_DETAIL                         char(50)             NULL,   
     RELATIVE_CLOSE_ASSOC               char(1)              NULL,   
     RCA_DETAIL                         char(50)             NULL,   
     CRS_STATUS                         char(10)             NULL,   
     CRS_EXACT_STATUS                   char(10)             NULL,   
     COMMERCIAL_ENTITY                  char(1)              NULL


GO

  CREATE TABLE  CLIENT_LINKED_PERSONS  
    (CLP_ID                             DECIMAL(15)          NOT NULL,   
     ENTITY                             int                  NULL,   
     CONTROLLING_PERSON                 int                  NULL,   
     STATUS                             smallint             NULL,   
     TYPE                               char(10)             NULL,   
     SUB_TYPE                           char(10)             NULL,   
     START_DATE                         datetime             NULL,   
     END_DATE                           datetime             NULL,   
     CREATED_PROCESS                    char(12)             NULL,   
     MODIFY_PROCESS                     char(12)             NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     MODIFY_BY                          char(5)              NULL,   
     MODIFY_DATE                        datetime             NULL,   
     MODIFY_TIME                        datetime             NULL,   
     FK_CLIENTSCLI_ID                   int                  NULL,
    CONSTRAINT I0000629
    PRIMARY KEY NONCLUSTERED 
      (CLP_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000635 
    ON  CLIENT_LINKED_PERSONS  
    (FK_CLIENTSCLI_ID                     ) 

GO