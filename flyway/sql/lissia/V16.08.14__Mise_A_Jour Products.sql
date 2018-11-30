
  Update PRODUCTS set status = 2
  where prd_id not like 'W%' and status <> 2;