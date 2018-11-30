
-- add new LoginID column
Alter TABLE  USERS  
    add
     LOGIN_ID                           varchar(40)          NULL
GO

-- LoginID will be unique, so allow the USR_ID to populate column as a temp measure
Update Users
	set login_id = usr_id
go

-- create the new unique index for LoginID
  CREATE UNIQUE NONCLUSTERED INDEX  LISS_USR_R1 
    ON  USERS  
    (LOGIN_ID                             ) 

GO