package lu.wealins.common.dto.liability.services;

public class PolicyNoteDTO{
	
	private GeneralNoteDTO note;
	private OptionDetailDTO type;
	
	public GeneralNoteDTO getNote() {
		return note;
	}
	public void setNote(GeneralNoteDTO note) {
		this.note = note;
	}
	public OptionDetailDTO getType() {
		return type;
	}
	public void setType(OptionDetailDTO type) {
		this.type = type;
	}

	
	
}
