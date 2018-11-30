update CHECK_WORKFLOW set check_desc = 'Decision' where checkcode in ('C_AC2','C_ACP')
go
update CODE_LABEL set label = 'Accepted' where TYPE_CD in ('C_AC2','C_ACP') and code in ('ACCEPT') 
go