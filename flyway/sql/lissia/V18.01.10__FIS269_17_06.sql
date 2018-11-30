/* FIS-269 data correction for PNONNO */

Update product_values 
set value_from = 2, MODIFY_BY = '*SQL*', MODIFY_DATE = GETDATE(), MODIFY_PROCESS = 'SQLScript', MODIFY_TIME = GETDATE()
where control like 'PNONNO%' and value_from = 0

