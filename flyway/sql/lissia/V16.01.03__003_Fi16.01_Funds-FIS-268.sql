
ALTER TABLE  FUNDS
ADD
     DEPOSIT_BANK_FLAT_FEE              DECIMAL(15, 2)       NULL,   
     ASSET_MAN_FEE_CCY                  char(3)              NULL,   
     BANK_DEP_FEE_CCY                   char(3)              NULL,   
     FIN_ADVISOR_FEE_CCY                char(3)              NULL,   
     BROKER                             char(8)              NULL,   
     UCITS                              smallint             NULL,   
     MAX_ALLOCATION_A                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_B                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_C                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_D                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_N                   DECIMAL(8, 4)        NULL
     
GO

ALTER TABLE  FUNDS_HISTORY_DETAILS
ADD
     DEPOSIT_BANK_FLAT_FEE              DECIMAL(15, 2)       NULL,   
     ASSET_MAN_FEE_CCY                  char(3)              NULL,   
     BANK_DEP_FEE_CCY                   char(3)              NULL,   
     FIN_ADVISOR_FEE_CCY                char(3)              NULL,   
     BROKER                             char(8)              NULL,   
     UCITS                              smallint             NULL,   
     MAX_ALLOCATION_A                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_B                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_C                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_D                   DECIMAL(8, 4)        NULL,   
     MAX_ALLOCATION_N                   DECIMAL(8, 4)        NULL
     
GO