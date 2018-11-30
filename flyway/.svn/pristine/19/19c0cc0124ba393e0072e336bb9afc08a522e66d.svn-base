
select distinct pa.FK_POLICIESPOL_ID from POLICY_AGENT_SHARES pa
where pa.type = 5
and pa.AGENT = 'A01141'
and pa.status = 1
and exists(select * from POLICY_AGENT_SHARES p, agents a 
where p.FK_POLICIESPOL_ID = pa.FK_POLICIESPOL_ID and
p.type = 5
and a.AGT_ID = p.AGENT
and a.CATEGORY = 'BK'
and p.status = 1
and a.AGT_ID not in ('A01141'))
;

UPDATE POLICY_AGENT_SHARES 
set STATUS = 2
where POLICY_AGENT_SHARES.type = 5
and POLICY_AGENT_SHARES.AGENT = 'A01141'
and POLICY_AGENT_SHARES.status = 1
and exists(select * from POLICY_AGENT_SHARES p, agents a 
where p.FK_POLICIESPOL_ID = POLICY_AGENT_SHARES.FK_POLICIESPOL_ID and
p.type = 5
and a.AGT_ID = p.AGENT
and a.CATEGORY = 'BK'
and p.status = 1
and a.AGT_ID not in ('A01141'));

