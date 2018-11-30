﻿--
-- Script was generated by Devart dbForge Studio for SQL Server, Version 5.4.270.0
-- Product Home Page: http://devart.com/dbforge/sql/studio
-- Script date 23/04/2018 17:16:03
-- Source server version: 12.00.2269
-- Source connection string: Data Source=elissia-sql,1433;Integrated Security=False;User ID=elissia-flywaydb
-- Target server version: 12.00.4213
-- Target connection string: Data Source=elissia-sql,1435;Integrated Security=False;User ID=elissia-flywaydb
-- Run this script against $[CLIENT_ENV]_COMMON to synchronize it with $[CLIENT_ENV]_COMMON
-- Please backup your target database before running this script
--



SET LANGUAGE 'British English'
SET DATEFORMAT ymd
SET ARITHABORT, ANSI_PADDING, ANSI_WARNINGS, CONCAT_NULL_YIELDS_NULL, QUOTED_IDENTIFIER, ANSI_NULLS, NOCOUNT, XACT_ABORT ON
SET NUMERIC_ROUNDABORT, IMPLICIT_TRANSACTIONS OFF
GO

USE $[CLIENT_ENV]_COMMON
GO
IF DB_NAME() <> N'$[CLIENT_ENV]_COMMON' SET NOEXEC ON
GO


--
-- Set transaction isolation level
--
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
GO

--
-- Start Transaction
--
BEGIN TRANSACTION
SET IDENTITY_INSERT dbo.SYSTEM_PROPERTIES OFF
GO

INSERT dbo.SYSTEM_PROPERTIES(CATEGORY, PROPERTY_NAME, PROPERTY_VALUE, DESCRIPTION, STATUS, CREATED_BY, CREATED_DATE) VALUES (N'workflow', N'landing.page.run.personalqueue.search', N'true', N'If current user has personal queue, if set to TRUE, this will be the default queue selected in the workflow home page queue search filter.', 1, N'AAAAA', '2018-04-23 00:00:00.0000000')
GO

IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END

--
-- Commit Transaction
--
IF @@TRANCOUNT>0 COMMIT TRANSACTION
SET NOEXEC OFF
GO
