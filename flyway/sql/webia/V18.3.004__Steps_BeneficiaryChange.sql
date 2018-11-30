

INSERT INTO STEP VALUES ('Awaiting Activation',11,0,1);

update step set STEP_AUTOMATIC = 1 where step_id in (50,32);

update step set STEP_REJECTABLE = 1 where step_id in (31,34,36);









