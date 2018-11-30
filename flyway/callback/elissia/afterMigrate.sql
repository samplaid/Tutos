update WORKFLOW_ITEM_TYPE set url = '${formURL}' where id = 7;
go
update WORKFLOW_ITEM_TYPE set url = '${beneficiaryChangeURL}' where id = 11;
go
update WORKFLOW_ITEM_TYPE set url = '${additionalPremiumURL}' where id = 12;
go
update WORKFLOW_ITEM_TYPE set url = '${brokerChangeURL}' where id = 13;
go
update ${schemaCommon}.[dbo].[USERS] set EMAIL_EXTN='${batchUser}@wealins.com', LOGIN_ID='${batchUser}' where NAME0='Technical batch user'
GO 
IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME like '%ADD_USER%')
    DROP PROCEDURE dbo.ADD_USER
GO
CREATE PROCEDURE dbo.ADD_USER @USR_ID nvarchar(20), @PASSWORD nvarchar(40), @EMAIL_EXTN varchar(40)
AS
BEGIN
   IF NOT EXISTS (SELECT * FROM ${schemaCommon}.[dbo].[USERS] where LOGIN_ID = @USR_ID)
	BEGIN
	INSERT ${schemaCommon}.[dbo].[USERS] ([USR_ID], [PASSWORD], [NAME0], [USER_GROUP], [STATUS], [EMAIL_EXTN], [LANGUAGE0], [CREATED_DATE], [CREATED_TIME], [LOGIN_ID], USER_TYPE, [SALT_VALUE], DISP_CATEGORY, [THEME]) 
	VALUES (
		@USR_ID, @PASSWORD, @USR_ID, 0, 1, @EMAIL_EXTN, 3, GETDATE(), GETDATE(),
		@USR_ID, 1, N'ZbbI6vllZEc=', 0, N'fisa');
	END
END

BEGIN
   IF NOT EXISTS (SELECT * FROM ${schemaCommon}.[dbo].USER_GROUP_RELATIONSHIPS where FK_USERSUSR_ID = 
					(SELECT USR_ID FROM ${schemaCommon}.[dbo].[USERS] where LOGIN_ID = @USR_ID))
	BEGIN
		INSERT INTO ${schemaCommon}.[dbo].USER_GROUP_RELATIONSHIPS VALUES ('1', @USR_ID, 1);
	END
END

BEGIN
	IF NOT EXISTS (SELECT * FROM WORKFLOW_QUEUE WHERE FK_USERSUSR_ID = 
					(SELECT USR_ID FROM ${schemaCommon}.[dbo].[USERS] where LOGIN_ID = @USR_ID))
	BEGIN
		INSERT INTO WORKFLOW_QUEUE (NAME0, TYPE0, FK_USERSUSR_ID, STATUS) VALUES (@USR_ID, 1, 
			(SELECT USR_ID FROM ${schemaCommon}.[dbo].[USERS] where LOGIN_ID = @USR_ID), 1);
	END
END
go
EXEC dbo.ADD_USER @USR_ID = N'AMC', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'amc@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'AZZ', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'azz@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'LUR', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'lur@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'API', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'api@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'FEB', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'feb@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'NGA', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'nga@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'RVN', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'rvn@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'CWA', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'cwa@wealins.com';
EXEC dbo.ADD_USER @USR_ID = N'DUO', @PASSWORD=N'fBiIWOEGNmWMT4BkrlmF3sv2VzM=', @EMAIL_EXTN=N'duo@wealins.com';

go