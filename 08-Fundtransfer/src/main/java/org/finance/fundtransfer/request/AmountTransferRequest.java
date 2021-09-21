/**
 * 
 */
package org.finance.fundtransfer.request;

/**
 * @author Athatey Shyam
 *
 */
public class AmountTransferRequest {
	private String fromAccount;
	private String toAccount;
	private double balance;

	public AmountTransferRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
