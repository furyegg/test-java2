package enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by jason zhang on 3/18/2015.
 */
public enum FormInstanceType {

	NONE("NONE"), EXTERNAL_TOOLSET("EXTERNAL_TOOLSET"), INTERNAL_FIN("INTERNAL_FIN");

	String code;

	FormInstanceType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static FormInstanceType fromString(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (FormInstanceType type : FormInstanceType.values()) {
				if (type.code.equals(value)) {
					return type;
				}
			}
		}
		return null;
	}
}
