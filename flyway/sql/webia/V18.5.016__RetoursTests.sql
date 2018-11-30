-- Modification libellé 

update CHECK_WORKFLOW set CHECK_DESC = 'MOV-11 - LPS 6. The premium is paid in cash, by cheque or physical remittance of securities' where CHECK_ID = 166;

update CHECK_WORKFLOW set CHECK_DESC = 'MOV-11 - LPS 7. The premium is paid via the account of a third party' where CHECK_ID = 167;

update CHECK_WORKFLOW set CHECK_DESC = 'POSTPONE Step Acceptance after valuation' where CHECK_ID = 175;

update step set STEP_REJECTABLE = 0 where step_id = 52;