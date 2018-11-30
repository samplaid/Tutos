
Print 'Update Clients DAP'
Exec sp_rename 'Clients.DAP' ,  'DAP1', 'COLUMN';
  GO

ALTER TABLE Clients ADD DAP NCHAR(1) NULL;
  go

Update CLIENTS
  SET DAP = CAST(DAP1 AS NCHAR(1))
  GO

Alter Table Clients Drop Column DAP1;
go

Print 'Client_Endorsements DAP'
Exec sp_rename 'Client_Endorsements.DAP' ,  'DAP1', 'COLUMN';
  GO

ALTER TABLE Client_Endorsements ADD DAP NCHAR(1) NULL;
  go

Update CLIENT_ENDORSEMENTS
  SET DAP = CAST(DAP1 AS NCHAR(1))
  GO

Alter Table Client_endorsements Drop Column DAP1;
go

