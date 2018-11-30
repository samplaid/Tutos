/****** Object:  StoredProcedure [dbo].[pr_pst_set]    Script Date: 01/09/2017 15:06:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--
-- PR_PST_SET
--

-- fermeture du posting set actuellement ouvert
--

-- version 1.0	ROT 21.01.2008
--		mise en prod
--
-- version 1.1	ROT 05.08.2008
--		> created_system_date à aujourd'hui
--		> created_by à 'AUTO'
--

--create procedure pr_pst_set
ALTER procedure [dbo].[pr_pst_set]

as

set nocount on

declare	@pstset		float,
	@dateval		datetime--,
--  v1.1		@sysdate		datetime


select @dateval = getdate()
--  v1.1	select @sysdate = date_value from controls where ctl_id = 'SYSTEM_DATE'
select @pstset = pst_id  from posting_sets where status = 1


-- fermeture du posting-set ouvert

update posting_sets
  set 	status = 2,
	closed_by = right(system_user, 3),
	closed_date = @dateval,
	closed_time = @dateval,
	closed_process = 'RP000',
--	closed_system_date = @sysdate		-- v1.1
	closed_system_date = @dateval
from posting_sets
where pst_id = @pstset


-- ouverture d'un nouveau posting-set


INSERT INTO [POSTING_SETS] ([PST_ID] ,[STATUS]  ,[NLI_QTANO]
           ,[CREATED_DATE] ,[CREATED_TIME],[CREATED_PROCESS],[CREATED_BY],[CREATED_SYSTEM_DATE]
           ,[CLOSED_BY] ,[CLOSED_DATE],[CLOSED_TIME],[CLOSED_PROCESS],[CLOSED_SYSTEM_DATE]
           ,[SAP_STATUS] ,[SAP_EXPORT_DATE])
     values (@pstset + 1, 1, 0
		, @dateval, @dateval, 'RP000', 'AUTO', @dateval
		, NULL, NULL, NULL, NULL, NULL
		, NULL,NULL);

print 'Le posting-set ' + cast(@pstset as varchar) + ' a été fermé. Nouveau posting-set: ' + cast(@pstset + 1 as varchar)