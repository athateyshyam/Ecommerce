/**
 * 
 */
package org.finance.fundtransfer.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {
	private static final long serialVersionUID = -2489932120047529554L;
	private String accNo;
	private String accType;
	private Double balance;

	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
