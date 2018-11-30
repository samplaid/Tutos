package lu.wealins.common.dto.liability.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillDTO {
  protected Long bilNo;
  
  public Long getBilNo()
  {
    return this.bilNo;
  }
  
  public void setBilNo(Long value)
  {
    this.bilNo = value;
  }
}