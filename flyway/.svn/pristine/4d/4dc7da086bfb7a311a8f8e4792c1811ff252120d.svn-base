
ALTER TABLE AGENT_HISTORY_DETAILS
ADD
     NEXT_CATEGORY                      char(10)             NULL,   
     NEXT_CATEGORY_DATE                 datetime             NULL,   
     LAST_CATEGORY_CHANGE               datetime             NULL,   
     MOBILE                             char(20)             NULL,   
     DOCUMENTATION_LANGUAGE             smallint             NULL,   
     DESIRED_PAYMENT_CCY                char(3)              NULL,   
     PAYMENT_COMMISSION                 smallint             NULL,   
     VAT                                smallint             NULL,   
     FIN_FEES_STATEMENT                 smallint             NULL
     
 GO
 
 
   CREATE TABLE  EXCHANGE_RATES_HISTORY_DETAILS  
     (XRH_ID                             DECIMAL(15)          NOT NULL,   
      FROM_CURRENCY                      char(4)              NOT NULL,   
      TO_CURRENCY                        char(4)              NOT NULL,   
      DATE                               datetime             NOT NULL,   
      MID_RATE                           DECIMAL(15, 7)       NULL,   
      BUY_RATE                           DECIMAL(15, 7)       NULL,   
      SELL_RATE                          DECIMAL(15, 7)       NULL,   
      COMPANY_RATE                       DECIMAL(15, 7)       NULL,   
      RECIPROCAL                         smallint             NULL,   
      STATUS                             smallint             NOT NULL,   
      CREATED_BY                         char(5)              NULL,   
      CREATED_DATE                       datetime             NOT NULL,   
      CREATED_TIME                       datetime             NOT NULL,   
      CREATED_PROCESS                    char(12)             NULL,   
      FK_EXCHANGE_RATXRS_ID              char(16)             NULL,
     CONSTRAINT I0010588
     PRIMARY KEY NONCLUSTERED 
       (XRH_ID                             )) 
 
 GO
 
   CREATE NONCLUSTERED INDEX  I0000590 
     ON  EXCHANGE_RATES_HISTORY_DETAILS  
     (FK_EXCHANGE_RATXRS_ID                ) 
 
GO


  CREATE TABLE  FUNDS_HISTORY_DETAILS  
    (FDH_ID                             DECIMAL(15)          NOT NULL,   
     CURRENCY                           char(5)              NOT NULL,   
     FUND_TYPE                          smallint             NOT NULL,   
     NAME                               char(35)             NOT NULL,   
     PRICING_FREQUENCY                  smallint             NOT NULL,   
     PRICING_DAY                        smallint             NULL,   
     PRICING_DAY_OF_MONTH               smallint             NULL,   
     PRICING_DELAY                      smallint             NULL,   
     PRICE_BASIS                        smallint             NOT NULL,   
     BID_OFFER_SPREAD                   DECIMAL(8, 4)        NULL,   
     STATUS                             smallint             NOT NULL,   
     UNIT_TYPES                         smallint             NOT NULL,   
     FWD_PRICE_REPORT_DAYS              smallint             NULL,   
     GROUPING_CODE                      char(10)             NULL,   
     CREATED_BY                         varchar(5)           NOT NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     CREATED_PROCESS                    char(12)             NULL,   
     ACCOUNTING_STREAM                  smallint             NULL,   
     DOCUMENTATION_NAME                 char(100)            NULL,   
     ISIN_CODE                          char(12)             NULL,   
     NL_PRODUCT                         char(3)              NULL,   
     CUT_OFF_TIME                       datetime             NULL,   
     MAX_ALLOCATION_PERCENT             DECIMAL(8, 4)        NULL,   
     UL_FUND_TYPE                       char(8)              NULL,   
     NAV_ENTRY_FEE                      DECIMAL(15, 6)       NULL,   
     NAV_EXIT_FEE                       DECIMAL(15, 6)       NULL,   
     ASSET_MANAGER                      char(8)              NULL,   
     DEPOSIT_BANK                       char(8)              NULL,   
     COUNTRY                            char(27)             NULL,   
     NOTES                              char(100)            NULL,   
     RISK_PROFILE                       char(8)              NULL,   
     RISK_CURRENCY                      char(8)              NULL,   
     INVEST_CAT                         char(8)              NULL,   
     INVEST_OBJECTIVE                   char(8)              NULL,   
     ALTERNATIVE_FUNDS                  char(8)              NULL,   
     RISK_PROFILE_DATE                  datetime             NULL,   
     INVEST_CASH_LIMIT                  DECIMAL(15, 2)       NULL,   
     POA                                smallint             NULL,   
     POA_TYPE                           char(12)             NULL,   
     MANDATE_HOLDER                     char(8)              NULL,   
     POA_DATE                           datetime             NULL,   
     IBAN                               char(35)             NULL,   
     SALES_REP                          char(8)              NULL,   
     PRIVATE_EQUITY                     int                  NULL,   
     PERFORMANCE_FEE                    char(100)            NULL,   
     DEPOSIT_ACCOUNT                    char(20)             NULL,   
     FUND_CLASSIFICATION                char(1)              NULL,   
     PRIVATE_EQUITY_FEE                 DECIMAL(15, 2)       NULL,   
     ACCOUNT_ROOT                       char(20)             NULL,   
     FUND_SUB_TYPE                      char(3)              NULL,   
     FINANCIAL_ADVISOR                  char(8)              NULL,   
     ASSET_MANAGER_FEE                  DECIMAL(15, 8)       NULL,   
     FIN_FEES_FLAT_AMOUNT               DECIMAL(15, 2)       NULL,   
     FIN_FEES_MIN_AMOUNT                DECIMAL(15, 2)       NULL,   
     FIN_FEES_MAX_AMOUNT                DECIMAL(15, 2)       NULL,   
     BANK_DEPOSIT_FEE                   DECIMAL(15, 8)       NULL,   
     FIN_ADVISOR_FEE                    DECIMAL(15, 8)       NULL,   
     CLASS_OF_RISK                      char(1)              NULL,   
     ASSET_MAN_RISK_PROFILE             smallint             NULL,   
     POC                                smallint             NULL,   
     POC_TYPE                           char(12)             NULL,   
     CONSULTANT                         char(8)              NULL,   
     POC_DATE                           datetime             NULL,   
     FK_FUNDSFDS_ID                     char(8)              NULL,
    CONSTRAINT I0000592
    PRIMARY KEY NONCLUSTERED 
      (FDH_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000594 
    ON  FUNDS_HISTORY_DETAILS  
    (FK_FUNDSFDS_ID                       ) 

GO


  CREATE TABLE  FUND_PRICES_HISTORY_DETAILS  
    (FPH_ID                             DECIMAL(15)          NOT NULL,   
     FUND                               char(8)              NOT NULL,   
     STATUS                             smallint             NOT NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_DATE                       datetime             NOT NULL,   
     CREATED_TIME                       datetime             NOT NULL,   
     CREATED_PROCESS                    char(12)             NULL,   
     DATE                               datetime             NOT NULL,   
     UNIT_TYPE                          smallint             NOT NULL,   
     PRICE_TYPE                         smallint             NOT NULL,   
     CURRENCY                           char(4)              NULL,   
     PRICE                              DECIMAL(15, 6)       NOT NULL,   
     RECORD_TYPE                        smallint             NULL,   
     FK_FUND_PRICESFPR_ID               char(20)             NULL,
    CONSTRAINT I0000596
    PRIMARY KEY NONCLUSTERED 
      (FPH_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000598 
    ON  FUND_PRICES_HISTORY_DETAILS  
    (FK_FUND_PRICESFPR_ID                 ) 

GO




  CREATE TABLE  FUND_TRANS_HISTORY_DETAILS  
    (FTH_ID                             DECIMAL(15)          NOT NULL,   
     PRIORITY                           smallint             NULL,   
     PRODUCT_LINE                       char(8)              NULL,   
     HOLDING                            smallint             NULL,   
     POLICY                             char(14)             NULL,   
     COVERAGE                           smallint             NULL,   
     PRICE                              DECIMAL(15, 6)       NULL,   
     UNITS                              DECIMAL(15, 6)       NULL,   
     ACTION0                            smallint             NULL,   
     UNIT_TYPE                          smallint             NULL,   
     DATE                               datetime             NOT NULL,   
     ACTIVITY_DATE                      datetime             NULL,   
     PRICING_DATE                       datetime             NULL,   
     EXCHANGE_RATE                      DECIMAL(15, 7)       NULL,   
     FUND_CURRENCY                      char(5)              NULL,   
     POLICY_CURRENCY                    char(5)              NULL,   
     VALUE_POL_CCY                      DECIMAL(15, 2)       NULL,   
     VALUE_FUND_CCY                     DECIMAL(15, 2)       NULL,   
     FUND                               varchar(8)           NOT NULL,   
     EVENT_TYPE                         smallint             NOT NULL,   
     DATE_FWD_PRICE_REP                 datetime             NULL,   
     TRANSACTION0                       DECIMAL(14)          NOT NULL,   
     LINE_NO                            smallint             NOT NULL,   
     HOLDING_VALUATION                  DECIMAL(15, 2)       NULL,   
     STATUS                             smallint             NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_DATE                       datetime             NULL,   
     CREATED_TIME                       datetime             NULL,   
     CREATED_PROCESS                    char(12)             NULL,   
     CREATED_SYSTEM_DATE                datetime             NULL,   
     RECIPROCAL                         smallint             NULL,   
     BASIS                              smallint             NULL,   
     ACT_FUND_UNITS                     DECIMAL(15, 6)       NULL,   
     ACT_FUND_VALUE_FUND_CCY            DECIMAL(15, 2)       NULL,   
     ACT_FUND_VALUE_POLICY_CCY          DECIMAL(15, 2)       NULL,   
     ACT_FUND_FACTOR                    DECIMAL(12, 8)       NULL,   
     MARGIN_VALUE                       DECIMAL(15, 2)       NULL,   
     MARGIN_VALUE_PREM                  DECIMAL(15, 2)       NULL,   
     FK_FUND_TRANSACFTR_ID              DECIMAL(15)          NULL,
    CONSTRAINT I0000603
    PRIMARY KEY NONCLUSTERED 
      (FTH_ID                             )) 

GO

  CREATE TABLE  PRODUCT_VALUES_HISTORY_DETAILS  
    (PRH_ID                             DECIMAL(15)          NOT NULL,   
     TABLE_NUMBER                       DECIMAL(15)          NULL,   
     VALUE_FROM                         smallint             NULL,   
     CURRENCY                           char(4)              NOT NULL,   
     CONTROL                            char(6)              NOT NULL,   
     NUMERIC_VALUE                      DECIMAL(15, 8)       NULL,   
     PRODUCT_LINE                       char(20)             NULL,   
     ALPHA_VALUE                        char(100)            NULL,   
     DEC_PLACES                         smallint             NULL,   
     CODE_VALUE                         char(20)             NOT NULL,   
     DATA_TYPE                          int                  NOT NULL,   
     SUB_DATA_TYPE                      int                  NULL,   
     CREATED_PROCESS                    char(15)             NULL,   
     STATUS                             smallint             NOT NULL,   
     CREATED_BY                         char(5)              NULL,   
     CREATED_DATE                       datetime             NULL,   
     CREATED_TIME                       datetime             NULL,   
     MAXIMUM_VALUE                      DECIMAL(15, 8)       NULL,   
     MAXIMUM_TABLE_NUMBER               DECIMAL(15)          NULL,   
     MAXIMUM_VALUE_FROM                 smallint             NULL,   
     USEAGE                             smallint             NULL,   
     FK_PRODUCT_VALUPRC_ID              char(30)             NULL,
    CONSTRAINT I0000602
    PRIMARY KEY NONCLUSTERED 
      (PRH_ID                             )) 

GO

  CREATE NONCLUSTERED INDEX  I0000604 
    ON  FUND_TRANS_HISTORY_DETAILS  
    (FK_FUND_TRANSACFTR_ID                ) 

GO

  CREATE NONCLUSTERED INDEX  I0000606 
    ON  PRODUCT_VALUES_HISTORY_DETAILS  
    (FK_PRODUCT_VALUPRC_ID                ) 

GO

