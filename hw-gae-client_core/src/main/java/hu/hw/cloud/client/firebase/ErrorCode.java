/**
 * 
 */
package hu.hw.cloud.client.firebase;

/**
 * @author robi
 *
 */
public enum ErrorCode {
	PERMISSION_DENIED;

	public boolean is(String errorCode) {
		return name().equals(errorCode);
	}
}