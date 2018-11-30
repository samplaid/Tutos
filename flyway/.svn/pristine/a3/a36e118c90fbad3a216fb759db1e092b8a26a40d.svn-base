-- Reprise de données
-- Type de fonds
  
  UPDATE FUNDS
  SET [FUND_SUB_TYPE] = 'FID'
  WHERE FUND_TYPE = 3 and 
  FDS_ID NOT LIKE 'F%';
  
  UPDATE FUNDS
  SET [FUND_SUB_TYPE] = 'FAS'
  WHERE FUND_TYPE = 3 and 
  FDS_ID LIKE 'F%';
  
   UPDATE FUNDS
  SET [FUND_SUB_TYPE] = 'FE'
  WHERE FUND_TYPE = 1;
  
  UPDATE FUNDS
  SET [FUND_SUB_TYPE] = 'FIC'
  WHERE FUND_TYPE = 1  and 
  NOTES Like '%FIC%';
  
   -- Classification et LC

   UPDATE FUNDS Set FUND_CLASSIFICATION = 'A' WHERE GROUPING_CODE LIKE '%DA%';

   UPDATE FUNDS Set FUND_CLASSIFICATION = 'B' WHERE GROUPING_CODE LIKE '%DB%';

   UPDATE FUNDS Set FUND_CLASSIFICATION = 'C' WHERE GROUPING_CODE LIKE '%DC%';

   UPDATE FUNDS Set FUND_CLASSIFICATION = 'D' WHERE GROUPING_CODE LIKE '%DD%';

   UPDATE FUNDS Set FUND_CLASSIFICATION = 'N'  WHERE GROUPING_CODE LIKE '%N%';

   UPDATE FUNDS Set FUND_CLASSIFICATION = 'U'  WHERE GROUPING_CODE LIKE '%DU%';


   UPDATE FUNDS Set GROUPING_CODE = 'C01/8'  WHERE GROUPING_CODE LIKE 'C01/8%';

   UPDATE FUNDS Set GROUPING_CODE = 'C04/8'  WHERE GROUPING_CODE LIKE 'C04/8%';

   UPDATE FUNDS Set GROUPING_CODE = 'C05/5'  WHERE GROUPING_CODE LIKE 'C05/5%';

   UPDATE FUNDS Set GROUPING_CODE = 'C06/8'  WHERE GROUPING_CODE LIKE 'C06/8%';

   UPDATE FUNDS Set GROUPING_CODE = 'C08/1'  WHERE GROUPING_CODE LIKE 'C08/1%';

   UPDATE FUNDS Set GROUPING_CODE = 'C15/3'  WHERE GROUPING_CODE LIKE 'C15/3%';
 
   UPDATE FUNDS Set GROUPING_CODE = 'C95/3'  WHERE GROUPING_CODE LIKE 'C95/3%';


   
   -- Frais de gestion Asset Manager
   update FUNDS set ASSET_MANAGER_FEE = fa.NUMERIC_VALUE
   from PRODUCT_VALUES fa
   where FDS_ID = FK_FUNDSFDS_ID
   and fa.CONTROL = 'FCORAT';

   -- Frais de gestion Banque dépositaire
   update FUNDS set BANK_DEPOSIT_FEE = ft.NUMERIC_VALUE - fa.NUMERIC_VALUE
   from PRODUCT_VALUES fa, PRODUCT_VALUES ft
   where FDS_ID = fa.FK_FUNDSFDS_ID
   and FDS_ID = ft.FK_FUNDSFDS_ID
   and fa.CONTROL = 'FCORAT'
   and ft.control = 'FFFRAT';

   -- Tous les FID/FAS sont en calcul trimestriel, fin du trimestre  : pas de résultat
   select * from PRODUCT_VALUES, FUNDS where control in ('FAAINT','FAAOCC')
   and NUMERIC_VALUE in (8,5) and FK_FUNDSFDS_ID is not null
   and funds.fds_id = FK_FUNDSFDS_ID
   and FUNDS.STATUS = 1
   AND fund_type = 3;

   -- Mise à jour Racine
   Update Funds set ACCOUNT_ROOT = rtrim(substring(FDS_ID,2,7))
   from funds
   where FUND_SUB_TYPE in ('FAS','FID')
   and ACCOUNT_ROOT is null
   and funds.status = 1;

   -- Mise à jour du Broker sur le FAS
   update funds
   set broker = SALES_REP, SALES_REP = null
   where exists(select * from agents where agt_id = sales_rep and category = 'BK')
   and fund_sub_type = 'FAS';

   -- Mise à jour du Financial Advisor
   UPDATE  funds 
   set FINANCIAL_ADVISOR = a.AGT_ID
   from POLICY_AGENT_SHARES p, AGENTS a, POLICY_FUND_HOLDINGS ph
   where p.AGENT = a.AGT_ID
   and a.CATEGORY = 'FA'
   and ph.FK_POLICIESPOL_ID = p.FK_POLICIESPOL_ID
   and funds.FDS_ID = ph.FUND
   and funds.FUND_SUB_TYPE = 'FAS';

   UPDATE POLICY_AGENT_SHARES 
   set POLICY_AGENT_SHARES.status = 2
   FROM AGENTS a, POLICY_FUND_HOLDINGS ph, funds
   where POLICY_AGENT_SHARES.AGENT = a.AGT_ID
   and a.CATEGORY = 'FA'
   and ph.FK_POLICIESPOL_ID = POLICY_AGENT_SHARES.FK_POLICIESPOL_ID
   and funds.FDS_ID = ph.FUND
   and funds.FUND_SUB_TYPE = 'FAS';


   -- Mise à jour du Financial Advisor sur le FAS
   update funds
   set FINANCIAL_ADVISOR = SALES_REP, SALES_REP = null
   where exists(select * from agents where agt_id = sales_rep and category = 'FA')
   and fund_sub_type = 'FAS'
   and FINANCIAL_ADVISOR is null;

   


