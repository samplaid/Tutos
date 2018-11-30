
/****** Object:  Trigger [TRANSACTIONS_compliance_alert]    Script Date: 28/08/2017 16:56:17 ******/
DROP TRIGGER [dbo].[TRANSACTIONS_compliance_alert]
GO

/****** Object:  Trigger [dbo].[TRANSACTIONS_compliance_alert]    Script Date: 28/08/2017 16:56:17 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[TRANSACTIONS_compliance_alert] ON [dbo].[TRANSACTIONS]
WITH EXEC AS CALLER
AFTER INSERT NOT FOR REPLICATION
AS
begin
	declare	@buffer			char(250),
		@itrn			decimal(14, 0),
		@ievent_type		smallint,
		@ipolicy			varchar(16),
		@icreated_by		char(5),
		@pep_police		smallint,
		@dap_police		smallint,
		@private_equity_police	smallint,
		@insider_trader		char(15),
		@fatca			char(8),
		@addowner			smallint,
		@notes_police		varchar(1000),
		@to			char(80);

	-- for testing purposes
	--select @itrn = 46601001, @ipolicy = 'OW031393', @ievent_type = 2, @icreated_by = 'rot'


	-- no recursion call is allowed
	if trigger_nestlevel() > 1 begin RETURN end;

	-- set nocount on added to prevent extra result sets from
	-- interfering with select statements.
	set nocount on;


	-- inserted informations
	select	@itrn = TRN_ID, 
		@ievent_type = EVENT_TYPE, 
		@ipolicy = POLICY, 
		@icreated_by = CREATED_BY 
	from inserted;


	-- insert of a major transaction?
	if @ievent_type in (4, 6, 15, 17, 19, 21)
	  begin

		-- Recherche des données à surveiller sur la police

--v1.1		select @pep_police = PEP from policies where pol_id = @ipolicy;
		select	@pep_police = isnull(PEP, 0), 
			@dap_police = isnull(DAP, 0), 
			@private_equity_police = isnull(PRIVATE_EQUITY, 0) 
		from policies 
		where pol_id = @ipolicy;


		-- Recherche des données à surveiller sur le client	--v1.1

		select	@insider_trader = isnull(INSIDER_TRADING, ''),
			@fatca = isnull(CLASSIFICATION, '')
		from	CLI_POL_RELATIONSHIPS cpr
			left join CLIENTS c
			  on c.CLI_ID = cpr.FK_CLIENTSCLI_ID --and c.STATUS = 1
		where	cpr.FK_POLICIESPOL_ID = @ipolicy


		-- Recherche si relation Additional Owner	--v1.1

		select	@addowner = TYPE
		from	CLI_POL_RELATIONSHIPS cpr
		where	cpr.FK_POLICIESPOL_ID = @ipolicy
			and TYPE = 3


		-- Recherche des données à surveiller pour le compliance

		select @notes_police = details 
		from GENERAL_NOTES 
		where FK_POLICIESPOL_ID = @ipolicy and TYPE = 2;


		-- Tests et envoi des emails le cas échéant

		-- Police PEP as compliance?
--v1.1		if @notes_police like '%compliance%' or @pep_police = 1
		if @notes_police like '%compliance%'
		  begin
			select @buffer = 'Dear ' + rtrim(@icreated_by) + char(13) + char(10) + char(13) + char(10) 
				+ 'The transaction ' + cast(@itrn as char(7)) + ' you created on the policy ' + rtrim(@ipolicy) 
				+ ', marked as "Compliance", needs to be presented to the Comité d''Acceptation for approval.' + char(13) + char(10) + char(13) + char(10) 
				+ 'Please take the appropriate actions.';

			select @to = @icreated_by +'@fisa.lu;fac@fisa.lu;vlb@fisa.lu;dei@fisa.lu;wet@fisa.lu';

			EXEC msdb.dbo.sp_send_dbmail
				@profile_name = 'SQL Server Agent',
				@recipients = @to,
				--@copy_recipients = 'rot@fisa.lu',
				@body = @buffer,
				@subject = 'Approval from the Comité d''Acceptation is needed for a transaction ';
		  end;


		-- Police PEP ou DAP ou ...?		--v1.1
		if @pep_police = 1 or @dap_police = 1 or @private_equity_police = 1
		   or @insider_trader = 'IT YES' or @fatca = 'FATCA'
		  begin
			select @buffer = 'Dear ' + rtrim(@icreated_by) + char(13) + char(10) + char(13) + char(10) 
				+ 'The policy ' + rtrim(@ipolicy) + ' related to the transaction ' 
				+ cast(@itrn as char(7)) + ' you created needs attention.' + char(13) + char(10) + char(13) + char(10) 

				+ (select case when @pep_police = 1 or @dap_police = 1 or @private_equity_police = 1 then 'This policy is marked as ' else '' end)
				+ (select case @pep_police when 1 then 'PEP ' else '' end)
				+ (select case @dap_police when 1 then 'DAP ' else '' end)
				+ (select case @private_equity_police when 1 then 'PRIVATE EQUITY' else '' end) + char(13) + char(10) 

				+ (select case when @insider_trader = 'IT YES' or @fatca = 'FATCA' then 'At least one relationship is ' else '' end)
				+ (select case @insider_trader when 'IT YES' then 'INSIDER-TRADING ' else '' end)
				+ (select case @fatca when 'FATCA' then 'FATCA' else '' end) + char(13) + char(10) + char(13) + char(10)
				+ 'Please be cautious';

			select @to = @icreated_by +'@fisa.lu;fac@fisa.lu;vlb@fisa.lu;dei@fisa.lu;wet@fisa.lu';

			EXEC msdb.dbo.sp_send_dbmail
				@profile_name = 'SQL Server Agent',
				@recipients = @to,
				--@copy_recipients = 'rot@fisa.lu',
				@body = @buffer,
				@subject = 'Caution with the policy of a transaction ';
		  end;


		-- Additional owner?	--v1.1
		if @addowner = 3
		  begin
			select @buffer = 'Dear ' + rtrim(@icreated_by) + char(13) + char(10) + char(13) + char(10) 
				+ 'The transaction ' + cast(@itrn as char(7)) + ' you created on the policy ' + rtrim(@ipolicy) 
				+ ' has an additional owner defined.' + char(13) + char(10) + char(13) + char(10) 
				+ 'Please take care as some documents may be wrong.';

			select @to = @icreated_by +'@fisa.lu;fac@fisa.lu;vlb@fisa.lu;dei@fisa.lu;wet@fisa.lu';

			EXEC msdb.dbo.sp_send_dbmail
				@profile_name = 'SQL Server Agent',
				@recipients = @to,
				--@copy_recipients = 'rot@fisa.lu',
				@body = @buffer,
				@subject = 'Caution with the documents for the additional owner';
		  end;

	  end;

end;
GO

ALTER TABLE [dbo].[TRANSACTIONS] ENABLE TRIGGER [TRANSACTIONS_compliance_alert]
GO


