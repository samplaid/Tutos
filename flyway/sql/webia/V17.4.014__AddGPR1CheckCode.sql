update CHECK_WORKFLOW set checkcode=null where checkCode='GRP1' and check_type='YesNo';

update CHECK_WORKFLOW set checkcode='GRP1' where check_desc like '%omment%';