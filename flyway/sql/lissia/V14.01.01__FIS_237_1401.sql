ALTER TABLE AGENTS
ADD  MOBILE                             char(20)             NULL,   
     DOCUMENTATION_LANGUAGE             smallint             NULL,   
     DESIRED_PAYMENT_CCY                char(3)              NULL,   
     PAYMENT_COMMISSION                 smallint             NULL,   
     VAT                                smallint             NULL,   
     FIN_FEES_STATEMENT                 smallint             NULL
     
GO