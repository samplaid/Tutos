package lu.wealins.common.dto.liability.services.enums;

public enum ContactType
{
	CORRESP("CORRESP   "),   //Home address
	CORRESP1("CORRESP1  ");  // Corresponding address
	
  private String val;
  
  private ContactType(String val)
  {
    this.val = val;
  }
  
  public String val()
  {
    return this.val;
  }
  
  public static ContactType fromString(String val)
  {
    if (val != null) {
      for (ContactType b : values()) {
        if (val.equalsIgnoreCase(b.val)) {
          return b;
        }
      }
    }
    return null;
  }
}