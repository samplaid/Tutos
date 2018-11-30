-- Définit l'étape Generate Documentation comme automatique
update STEP set STEP_AUTOMATIC = 1 where workflow_item_type_id = 7 and step_workflow = 'Generate Documentation';
