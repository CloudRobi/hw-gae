/**
 * 
 */
package hu.hw.cloud.shared.dto.common;

import hu.hw.cloud.shared.cnst.ServiceType;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class ServiceDto extends AccountChildDto {

	/**
	 * Egyedi azonosító
	 */
	private String code;

	/**
	 * Megnevezés
	 */
	private String description;

	private ServiceType type;

	/**
	 * ÁFA kód
	 */
	private String vatCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
}
