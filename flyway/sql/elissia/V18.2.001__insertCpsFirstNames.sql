DECLARE @cps_firstname_id int; 
select @cps_firstname_id = (select mk.ID from ${schemaWorkflow}.dbo.WORKFLOW_METADATA_KEY mk where mk.DESCRIPTION='CPS 1')

DECLARE @cps_user_id int; 
select @cps_user_id = (select mk.ID from ${schemaWorkflow}.dbo.WORKFLOW_METADATA_KEY mk where mk.DESCRIPTION='subscription_first_cps_user')

delete from ${schemaWorkflow}.dbo.WORKFLOW_ITEM_METADATA where FK_WORKFLOW_METADATA_KEY = @cps_firstname_id

insert into ${schemaWorkflow}.dbo.WORKFLOW_ITEM_METADATA (VALUE0, FK_WORKFLOW_ITEM_ID, FK_WORKFLOW_METADATA_KEY, item_index, display_value)
select users.NAME0, im.FK_WORKFLOW_ITEM_ID, @cps_firstname_id, im.item_index, im.display_value 
from ${schemaWorkflow}.dbo.WORKFLOW_ITEM_METADATA im
inner join ${schemaWorkflow}.dbo.WORKFLOW_METADATA_KEY mk on mk.ID=im.FK_WORKFLOW_METADATA_KEY
inner join ${schemaCommon}.dbo.USERS users on users.USR_ID=im.VALUE0
where mk.id=@cps_user_id