WITH commission_status AS (
SELECT	distinct policy_id
		, CONCAT (YEAR(com_date),MONTH(com_date)) AS com_period
		, com_currency
		, com_amount
		, origin
		, AGENT_ID
		, CASE WHEN com_type = 'ENTRY' THEN 'ENTRY' WHEN com_type IN ('ADM','SWITCH','SURR','OPCVM','PRADM') THEN 'PORTFOLIO' ELSE 'UNKNOWN' END AS comm_type
		, status
  FROM	commission_to_pay
 WHERE	transfer_id IS NOT NULL
   AND	statement_id IS NOT NULL
)
, sap_status AS (
SELECT	sap.sap_openbal_id, sap.AMOUNT_CCY, com_period, com_amount, com.status,COM_CURRENCY,com.AGENT_ID
  FROM	sap_open_balance sap
		INNER JOIN commission_status com ON (
			sap.policy_id = com.policy_id AND
			CONCAT (YEAR(accounting_date), MONTH(accounting_date)) = com.com_period AND
			sap.currency = com.com_currency AND
			sap.amount_ccy = com.com_amount AND
			sap.origin = com.origin AND
			sap.commission_type = com.comm_type AND 
			sap.AGENT = com.AGENT_ID
		)
)
UPDATE	sap_open_balance
   SET	sap_open_balance.status = sap_status.status
  FROM	sap_open_balance
		INNER JOIN sap_status ON (sap_open_balance.sap_openbal_id = sap_status.sap_openbal_id)
 WHERE	sap_open_balance.status IS NULL