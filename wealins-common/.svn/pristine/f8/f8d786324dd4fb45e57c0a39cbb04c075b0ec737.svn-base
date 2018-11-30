package lu.wealins.common.dto.liability.services.enums;

public enum GeneralNoteType
{
	NOTES("Notes                                                                                               "),
	NOTIFICATION("Notification                                                                                        "),
	DEATH_BENEFICIARY("Beneficiary (at Death)                                                                              "),
	LIFE_BENEFICIARY("Beneficiary (at Maturity)                                                                           "),
	UNDERWRITING("Underwriting                                                                                        "),
	PROCURATIONS("Procurations                                                                                        "),
	PLEDGE("Pledge                                                                                              "),
	ASSIGNMENT("Assignment                                                                                          "),
	BELGIAN_LEGAL_HEIRS("Belgian Legal Heirs                                                                                 "),
	PEP_DAP("PEP/DAP                                                                                             ");

  
  private String val;
  
  private GeneralNoteType(String val)
  {
    this.val = val;
  }
  
  public String val()
  {
    return this.val;
  }
  
  public static GeneralNoteType fromString(String val)
  {
    if (val != null) {
      for (GeneralNoteType b : values()) {
        if (val.equalsIgnoreCase(b.val)) {
          return b;
        }
      }
    }
    return null;
  }
}