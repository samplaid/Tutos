package lu.wealins.common.dto.liability.services.enums;

public enum LangueCode
{

	ENG(1),  //English                                                                                             
	UD(2),  //User.Def                                                                                            
	FRA(3),  //French                                                                                              
	POL(4),  //Polish                                                                                              
	CES(5),  //Czech                                                                                               
	TUR(6),  //Turkish                                                                                             
	NOR(7),  //Norwegian                                                                                           
	ITA(8),  //Italian                                                                                             
	DEU(9),  //German                                                                                              
	SWE(10),  //Swedish                                                                                             
	NED(11),  //Dutch - flemish                                                                                             
	SPA(12),  //Spanish                                                                                             
	POR(13),  //Portuguese                                                                                          
	DAN(14);  //Danish  
	
  private Integer val;
  
  private LangueCode(Integer val)
  {
    this.val = val;
  }
  
  public Integer val()
  {
    return this.val;
  }
  
  public static LangueCode fromValue(Integer val)
  {
    if (val != null) {
      for (LangueCode b : values()) {
        if (val.equals(b.val)) {
          return b;
        }
      }
    }
    return null;
  }
}