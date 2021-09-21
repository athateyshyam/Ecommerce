/**
 * 
 */
package org.finance.fundtransfer.exceptions;

/**
 * @author Athatey Shyam
 *
 */
public class AccountServiceException extends RuntimeException {

	private static final long serialVersionUID = 3691466754124200805L;

	public AccountServiceException(String message) {
		super(message);
	}
}
