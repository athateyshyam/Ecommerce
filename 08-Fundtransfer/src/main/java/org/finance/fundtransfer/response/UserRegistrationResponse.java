/**
 * 
 */
package org.finance.fundtransfer.response;

/**
 * @author Athatey Shyam
 *
 */
public class UserRegistrationResponse {
	private String username;
	private String password;
	private String accountNo;

	public UserRegistrationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
}
