ALTER TABLE STEP ADD STEP_ABORTABLE bit;
GO
ALTER TABLE STEP ADD STEP_REVERSABLE bit;
GO




insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('New', 12, 0, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Registration', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Waiting Dispatch', 12, 1, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Analysis', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Validate Analysis', 12, 1, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Acceptance', 12, 1, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Request to Client/Partner', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Acceptance Bis', 12, 1, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Account Opening Request', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Awaiting Account Opening', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Premium Transfer Request', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Generate Management Mandate', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Awaiting Premium', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Premium Input and NAV', 12, 1, 'false', 'true', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Validate Additional Premium', 12, 1, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Awaiting Valuation', 12, 0, 'true', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Generate Documentation', 12, 0, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Review Documentation', 12, 0, 'false', 'true', 'true');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Check Documentation', 12, 0, 'false', 'true', 'true');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Validate Documentation', 12, 1, 'false', 'false', 'false');
insert into [STEP]([STEP_WORKFLOW], [WORKFLOW_ITEM_TYPE_ID], [STEP_REJECTABLE], [STEP_AUTOMATIC], [STEP_ABORTABLE],[STEP_REVERSABLE]) values('Sending', 12, 1, 'false', 'false', 'false');

update step set STEP_AUTOMATIC = 'true' where STEP_WORKFLOW = 'Awaiting Valuation' and WORKFLOW_ITEM_TYPE_ID = 12;



