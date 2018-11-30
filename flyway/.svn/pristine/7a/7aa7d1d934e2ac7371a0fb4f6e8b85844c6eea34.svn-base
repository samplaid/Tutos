update [POLICIES] set [MAIL_TO_AGENT] ='' where POL_ID in ( SELECT POL_ID
  FROM POLICIES 
  where [MAIL_TO_AGENT]  = '#NA'     
 )