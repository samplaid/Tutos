delete FUNCTIONALITY_PROFIL;
delete FUNCTIONALITY;

delete PROFIL where NAME = 'Client e-wealins';
delete PROFIL where NAME = 'Courtage-2_commission_policy';


-- create functionality
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('impersonation', 'Able to impersonate');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('policy', 'Access to policy menu');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('documents', 'Access to document menu');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('tools', 'Access to tools menu');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('searchPolicies', 'Able to search policies');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('dashboard', 'Access to dashboard');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('policy_ext', 'Access to policy menu');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('documents_ext', 'Access to document menu');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('tools_ext', 'Access to tools menu');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('searchPolicies_ext', 'Able to search policies');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('dashboard_ext', 'Access to dashboard');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('policyNoMandated', 'Access to policy no mandated');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('unloadFax', 'Access to policy unload fax');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('unloadFax_ext', 'Access to policy unload fax');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('searchPoliciesInitialContract', 'Able to search policies by Partner reference');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('searchPoliciesInitialContract_ext', 'Able to search policies by Partner reference');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('communication', 'View communication');
insert into FUNCTIONALITY (NAME, DESCRIPTION) values ('commissions_ext', 'View commission from extern');



-- impersonation
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'impersonation'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'impersonation'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'impersonation'), (select id from PROFIL where NAME = 'Gestion'));

-- policy
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policy'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policy'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policy'), (select id from PROFIL where NAME = 'Gestion'));

-- documents
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents'), (select id from PROFIL where NAME = 'Gestion'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents'), (select id from PROFIL where NAME = 'Sales'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents'), (select id from PROFIL where NAME = 'Wealins'));

-- tools
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools'), (select id from PROFIL where NAME = 'Gestion'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools'), (select id from PROFIL where NAME = 'Sales'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools'), (select id from PROFIL where NAME = 'Wealins'));

--
-- searchPolicies
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPolicies'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPolicies'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPolicies'), (select id from PROFIL where NAME = 'Gestion'));

-- dashboard
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'dashboard'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'dashboard'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'dashboard'), (select id from PROFIL where NAME = 'Gestion'));

-- policy_ext
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policy_ext'), (select id from PROFIL where NAME = 'Courtage'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policy_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policy_ext'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));

-- documents_ext
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents_ext'), (select id from PROFIL where NAME = 'Sales'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents_ext'), (select id from PROFIL where NAME = 'Courtage'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents_ext'), (select id from PROFIL where NAME = 'Courtage_commission'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'documents_ext'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));

-- tools_ext
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools_ext'), (select id from PROFIL where NAME = 'Sales'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'tools_ext'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));

-- searchPolicies_ext
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPolicies_ext'), (select id from PROFIL where NAME = 'Courtage'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPolicies_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPolicies_ext'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));

-- dashboard_ext
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'dashboard_ext'), (select id from PROFIL where NAME = 'Courtage'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'dashboard_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'dashboard_ext'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));

-- policyNoMandated
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policyNoMandated'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policyNoMandated'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'policyNoMandated'), (select id from PROFIL where NAME = 'Gestion'));

-- unloadFax
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'unloadFax'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'unloadFax'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'unloadFax'), (select id from PROFIL where NAME = 'Gestion'));

-- unloadFax_ext
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'unloadFax_ext'), (select id from PROFIL where NAME = 'Courtage'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'unloadFax_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));

-- searchPoliciesInitialContract
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPoliciesInitialContract'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPoliciesInitialContract'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPoliciesInitialContract'), (select id from PROFIL where NAME = 'Gestion'));

-- searchPoliciesInitialContract
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'searchPoliciesInitialContract_ext'), (select id from PROFIL where NAME = 'Courtage-2_policy'));

-- communication
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Super admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Admin'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Gestion'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Sales'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Wealins'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Courtage'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Courtage-2_policy'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Courtage_commission'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'communication'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));

--
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'commissions_ext'), (select id from PROFIL where NAME = 'Courtage_commission'));
insert into FUNCTIONALITY_PROFIL (ID_FUNCTIONALITY, ID_PROFIL) 
	values ((select id from FUNCTIONALITY where NAME = 'commissions_ext'), (select id from PROFIL where NAME = 'Courtage_commission_policy'));