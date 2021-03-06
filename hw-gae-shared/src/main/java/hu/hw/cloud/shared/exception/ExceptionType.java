/**
 * 
 */
package hu.hw.cloud.shared.exception;

import java.io.Serializable;

/**
 * @author CR
 *
 */
public enum ExceptionType implements Serializable {

	// LOGIN
	LOGIN_INSUFFICIENT_AUTHENTICATION,

	LOGIN_USERNAME_NOT_FOUND,
	
	LOGIN_BAD_CREDENTIALS,

	LOGIN_DISABLED_USER,

	LOGIN_UNKNOWN_PROBLEM,

	//
	MISMATCHED_ACCOUNT, 
	
	MISSING_VALUE, 
	
	FAILD_ENTITIY_VALIDATION, 
	
	UNIQUE_INDEX_CONFLICT, 
	
	UNKNOWN;
}
