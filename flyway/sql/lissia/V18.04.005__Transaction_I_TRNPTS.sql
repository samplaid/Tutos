CREATE NONCLUSTERED INDEX [I_TRNPTS]
ON [dbo].[TRANSACTIONS] ([POLICY],[EVENT_TYPE],[STATUS])
GO