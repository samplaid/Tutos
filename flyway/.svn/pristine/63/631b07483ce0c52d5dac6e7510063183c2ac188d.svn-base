-- Mise à jour des personnes de contact des fonds 

update agents set category = 'PR'
where exists(select * from funds where agt_id = SALES_REP)
and category = 'AM';


update funds set sales_rep = '' where FINANCIAL_ADVISOR = SALES_REP and sales_rep <> '';
