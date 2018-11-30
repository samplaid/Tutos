INSERT INTO [dbo].[OPTION_DETAILS]
           ([NUMBER]
           ,[DESCRIPTION]
           ,[FK_OPTIONSOPT_ID]
           ,[CREATED_BY]
           ,[CREATED_DATE]
           ,[CREATED_TIME]
           ,[MODIFY_BY]
           ,[MODIFY_DATE]
           ,[MODIFY_TIME]
           ,[ODT_ID])
     select
           34,'Succession/legalheirs (death)','CPR_TYPE','rvn',GETDATE(), GETDATE(), 'rvn', GETDATE(), GETDATE(), max(odt_id)+1 
	 from option_details;

 INSERT INTO [dbo].[OPTION_DETAILS]
           ([NUMBER]
           ,[DESCRIPTION]
           ,[FK_OPTIONSOPT_ID]
           ,[CREATED_BY]
           ,[CREATED_DATE]
           ,[CREATED_TIME]
           ,[MODIFY_BY]
           ,[MODIFY_DATE]
           ,[MODIFY_TIME]
           ,[ODT_ID])
 select
           35,'Succession/legalheirs (life)','CPR_TYPE','rvn',GETDATE(), GETDATE(), 'rvn', GETDATE(), GETDATE(), max(odt_id)+1 
	 from option_details;