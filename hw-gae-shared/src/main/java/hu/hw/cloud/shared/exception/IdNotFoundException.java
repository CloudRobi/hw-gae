/**
 * 
 */
package hu.hw.cloud.shared.exception;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class IdNotFoundException extends Exception {

	private String entiy;

	private Object value;

	public IdNotFoundException(String entiy, Object value) {
		super();
		this.entiy = entiy;
		this.value = value;
	}

	public String getEntiy() {
		return entiy;
	}

	public Object getValue() {
		return value;
	}
}
