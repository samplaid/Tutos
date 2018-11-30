
INSERT INTO APPLI_PARAM VALUES ('EXEMPT_PRICING_FREQUENCY_FE','Pricing Frequency à exclure pour FE','4,6');

ALTER TABLE APPLI_PARAM ALTER COLUMN VALUE nvarchar(1000);

INSERT INTO APPLI_PARAM VALUES ('RULES_2_POLICYHOLDERS_NORD','Texte clause libre pour Finlande,Suède et Norvège','Policyholder no. 1 and Policyholder no. 2
Should any of the first ranking beneficiaries die before the occurrence of the insured event the deceased beneficiary’s share shall be transferred to the surviving first ranking beneficiary.
In case all the first ranking beneficiaries die before the payment of the death or maturity benefit, the applicable benefit shall be distributed to the “Second Ranking Beneficiary”.');