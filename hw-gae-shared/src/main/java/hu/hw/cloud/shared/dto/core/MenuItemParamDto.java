/**
 * 
 */
package hu.hw.cloud.shared.dto.core;

import hu.hw.cloud.shared.dto.Dto;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class MenuItemParamDto implements Dto {

	private String name;

	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
