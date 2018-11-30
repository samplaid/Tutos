package lu.wealins.common.dto.liability.services.enums;

public enum FundStrategyType
{

	FID("Dedicated internal fund"),
	FIC("Collectives internal fond"),
	FE("External fund"),
	UNKNOWN("Unknown");
	
  private String val;
  
  private FundStrategyType(String val)
  {
    this.val = val;
  }
  
  public String val()
  {
    return this.val;
  }
  
  public static FundStrategyType fromString(String val)
  {
    if (val != null) {
      for (FundStrategyType b : values()) {
        if (val.equalsIgnoreCase(b.val)) {
          return b;
        }
      }
    }
    return null;
  }
}