update client set BASE_URL = REPLACE(BASE_URL, 'sv-lvp18', '${keycloakSrv}') where base_url like '%sv-lvp18%';
update COMPONENT_CONFIG set VALUE = REPLACE(VALUE, 'GF_RHSSO_PRD', '${rhssoGroup}') where VALUE like '%GF_RHSSO_PRD%';
update REDIRECT_URIS set value = REPLACE(VALUE, 'sv-lvp18', '${keycloakSrv}') where VALUE like '%sv-lvp18%';
update REDIRECT_URIS set value = REPLACE(VALUE, 'sv-lvp74', '${appsSrv}') where VALUE like '%sv-lvp74%';
update REDIRECT_URIS set value = REPLACE(VALUE, 'sv-lvp92', '${esbSrv}') where VALUE like '%sv-lvp92%';