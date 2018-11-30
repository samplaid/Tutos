package lu.wealins.common.dto.liability.services.enums;

public enum ClauseCode
{
	// standard
	_A1("Décès Option A – Héritiers du souscripteur n°1"),
	_A1I("Décès Option A – Héritiers du souscripteur n°1 – Désignation de bénéficiaire(s) Irrévocable(s)"),
	_A1R("Décès Option A – Héritiers du souscripteur n°1 – Désignation de bénéficiaire(s) Révocable(s)"),
	_A2("Décès Option A – Héritiers du souscripteur n°2"),
	_A2I("Décès Option A – Héritiers du souscripteur n°2 – Désignation de bénéficiaire(s) Irrévocables(s)"),
	_A2R("Décès Option A – Héritiers du souscripteur n°2 – Désignation de bénéficiaire(s) Révocables(s)"),
	_A3("Décès Option A – Héritiers des 2 souscripteurs"),
	_A3I("Décès Option A – Héritiers des 2 souscripteurs – Désignation de bénéficiaire(s) Irrévocables(s)"),
	_A3R("Décès Option A – Héritiers des 2 souscripteurs – Désignation de bénéficiaire(s) Révocables(s)"),
	_B1("Décès Option B – Héritiers du souscripteur n°1"),
	_B1I("Décès Option B – Héritiers du souscripteur n°1 – Désignation de bénéficiaire(s) Irrévocables(s)"),
	_B1R("Décès Option B – Héritiers du souscripteur n°1 – Désignation de bénéficiaire(s) Révocables(s)"),
	_B2("Décès Option B – Héritiers du souscripteur n°2"),
	_B2I("Décès Option B – Héritiers du souscripteur n°2 – Désignation de bénéficiaire(s) Irrévocables(s)"),
	_B2R("Décès Option B – Héritiers du souscripteur n°2 – Désignation de bénéficiaire(s) Révocables(s)"),
	_B3("Décès Option B – Héritiers des 2 souscripteurs"),
	_B3I("Décès Option B – Héritiers des 2 souscripteurs – Désignation de bénéficiaire(s) Irrévocables(s)"),
	_B3R("Décès Option B – Héritiers des 2 souscripteurs – Désignation de bénéficiaire(s) Révocables(s)"),
	_1DA1("Démembrement option 1 : Clause bénéficiaire en PLEINE PROPRIETE. Décès Option A – Héritiers du souscripteur usufruitier n°1"),
	_1DA2("Démembrement option 1 : Clause bénéficiaire en PLEINE PROPRIETE. Décès Option A – Héritiers du souscripteur usufruitier n°2"),
	_1DA3("Démembrement option 1 : Clause bénéficiaire en PLEINE PROPRIETE. Décès Option A – Héritiers des 2 souscripteurs usufruitiers"),
	_1DB1("Démembrement option 1 : Clause bénéficiaire en PLEINE PROPRIETE. Décès Option B – Héritiers du souscripteur usufruitier n°1"),
	_1DB2("Démembrement option 1 : Clause bénéficiaire en PLEINE PROPRIETE. Décès Option B – Héritiers du souscripteur usufruitier n°2"),
	_1DB3("Démembrement option 1 : Clause bénéficiaire en PLEINE PROPRIETE. Décès Option B – Héritiers des 2 souscripteurs usufruitiers"),
	_2D("Démembrement option 2 : Clause bénéficiaire DEMEMBREE"),
	
	// not standard
	_NO("Clause bénéficiaire déposée chez un notaire ou clause non standard"),
	_DE("Démembrement – Clause non standard");
	
  private String val;
  
  private ClauseCode(String val)
  {
    this.val = val;
  }
  
  public String val()
  {
    return this.val;
  }
  
  public static ClauseCode fromString(String val)
  {
    if (val != null) {
      for (ClauseCode b : values()) {
        if (val.equalsIgnoreCase(b.val)) {
          return b;
        }
      }
    }
    return null;
  }
}