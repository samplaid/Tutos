ALTER TABLE dbo.[CLIENTS] ALTER COLUMN NAME  
            nchar(40) COLLATE SQL_Latin1_General_CP1_CI_AI;  
GO

ALTER TABLE dbo.[CLIENTS] ALTER COLUMN FIRST_NAME  
            nchar(40) COLLATE SQL_Latin1_General_CP1_CI_AI;  
GO

ALTER TABLE dbo.[CLIENTS] ALTER COLUMN MAIDEN_NAME  
            nchar(40) COLLATE SQL_Latin1_General_CP1_CI_AI;  
GO

ALTER TABLE dbo.[AGENTS] ALTER COLUMN NAME  
            nchar(150) COLLATE SQL_Latin1_General_CP1_CI_AI;  
GO