--select od.*,o.* from OPTION_DETAILS od inner join OPTIONS o on o.OPT_ID=od.FK_OPTIONSOPT_ID where o.OPT_ID='AGSPME' 

INSERT INTO OPTION_DETAILS 
Select 3,'Fax','AGSPME','rvn',getdate(),getdate(),null,null,null,max(odt_id)+1
from OPTION_DETAILS;

INSERT INTO OPTION_DETAILS 
Select 4,'Sepa','AGSPME','rvn',getdate(),getdate(),null,null,null,max(odt_id)+1
from OPTION_DETAILS;

INSERT INTO OPTION_DETAILS 
Select 5,'Swift','AGSPME','rvn',getdate(),getdate(),null,null,null,max(odt_id)+1
from OPTION_DETAILS;

INSERT INTO OPTION_DETAILS 
Select 6,'Swift+Fax','AGSPME','rvn',getdate(),getdate(),null,null,null,max(odt_id)+1
from OPTION_DETAILS;