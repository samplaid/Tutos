package lu.wealins.common.dto.liability.services.enums;

public enum ClientType {
	PHYSICAL((short) 1), MORAL((short) 3);

	private final short type;

	private ClientType(short type) {
		this.type = type;
	}

	public short getType() {
		return type;
	}

	public ClientType toEnum(Short type) {
		ClientType enumResult = null;

		if (type != null) {
			for (ClientType eachType : values()) {
				if (eachType.getType() == type.shortValue()) {
					enumResult = eachType;
					break;
				}
			}
		}

		return enumResult;
	}
}
