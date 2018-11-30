
ALTER TABLE CLIENTS
ADD  COUNTRY_OF_BIRTH                   char(30)             NULL,   
     PROVINCE_OF_BIRTH                  char(40)             NULL,   
     PROFESSION                         char(40)             NULL,   
     ACTIVITY_RISK_CAT                  smallint             NULL,   
     DATE_OF_REVISION                   datetime             NULL,   
     NAT_ID_COUNTRY                     char(30)             NULL,   
     NATIONAL_ID_2                      char(20)             NULL,   
     NAT_ID2_COUNTRY                    char(30)             NULL,
     FIDUCIARY				smallint	     NULL
GO

ALTER TABLE CLIENT_ENDORSEMENTS
ADD  COUNTRY_OF_BIRTH                   char(30)             NULL,   
     PROVINCE_OF_BIRTH                  char(40)             NULL,   
     PROFESSION                         char(40)             NULL,   
     ACTIVITY_RISK_CAT                  smallint             NULL,   
     DATE_OF_REVISION                   datetime             NULL,   
     NAT_ID_COUNTRY                     char(30)             NULL,   
     NATIONAL_ID_2                      char(20)             NULL,   
     NAT_ID2_COUNTRY                    char(30)             NULL,
     FIDUCIARY				smallint	     NULL
GO