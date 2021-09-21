/**
 * 
 */
package org.finance.fundtransfer.request;

public class AmountRequest {
	private String accNo;
	private Double balance;

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

}
