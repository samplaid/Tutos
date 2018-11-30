
drop synonym IF EXISTS WORKFLOW_USERS;
Go
drop synonym IF EXISTS WORKFLOW_USER_GROUPS;
Go
drop synonym IF EXISTS WORKFLOW_USER_GROUP_RELATIONSHIPS;
Go
drop synonym IF EXISTS WORKFLOW_QUEUE;
Go
drop synonym IF EXISTS WORKFLOW_ITEM;
Go
drop synonym IF EXISTS WORKFLOW_ITEM_TYPE;
Go
drop synonym IF EXISTS WORKFLOW_ACTION;
Go
drop synonym IF EXISTS WORKFLOW_ITEM_METADATA;
Go
drop synonym IF EXISTS WORKFLOW_METADATA_KEY;
Go
create synonym WORKFLOW_USERS for ${schemaCommon}.DBO.USERS
Go
create synonym WORKFLOW_USER_GROUPS for ${schemaCommon}.DBO.USER_GROUPS
Go
create synonym WORKFLOW_USER_GROUP_RELATIONSHIPS for ${schemaCommon}.DBO.USER_GROUP_RELATIONSHIPS
Go
create synonym WORKFLOW_QUEUE for ${schemaWorkflow}.DBO.WORKFLOW_QUEUE
Go
create synonym WORKFLOW_ITEM for ${schemaWorkflow}.DBO.WORKFLOW_ITEM
Go
create synonym WORKFLOW_ITEM_TYPE for ${schemaWorkflow}.DBO.WORKFLOW_ITEM_TYPE
Go
create synonym WORKFLOW_ACTION for ${schemaWorkflow}.DBO.WORKFLOW_ACTION
Go
create synonym WORKFLOW_ITEM_METADATA for ${schemaWorkflow}.DBO.WORKFLOW_ITEM_METADATA
Go
create synonym WORKFLOW_METADATA_KEY for ${schemaWorkflow}.DBO.WORKFLOW_METADATA_KEY
Go
--
--
-- Add super users
--
update [dbo].[APPLI_PARAM] set value = ${loginByPassStepAccess} where code = 'LOGINS_BY_PASS_STEP_ACCESS'
Go
