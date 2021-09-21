/**
 * 
 */
package org.finance.fundtransfer.dto;

import java.io.Serializable;

/**
 * @author Athatey Shyam
 *
 */
public class DispensaryAccountDTO implements Serializable {

	private static final long serialVersionUID = -8545248839942290218L;
	private Long id;
	private String dispensaryAccountNo;
	private String dispensaryAccountName;
	private Double dispensaryAccountBalance;
	private String savingAccNo;
	
	public DispensaryAccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDispensaryAccountNo() {
		return dispensaryAccountNo;
	}

	public void setDispensaryAccountNo(String dispensaryAccountNo) {
		this.dispensaryAccountNo = dispensaryAccountNo;
	}

	public String getDispensaryAccountName() {
		return dispensaryAccountName;
	}

	public void setDispensaryAccountName(String dispensaryAccountName) {
		this.dispensaryAccountName = dispensaryAccountName;
	}

	public String getSavingAccNo() {
		return savingAccNo;
	}

	public void setSavingAccNo(String savingAccNo) {
		this.savingAccNo = savingAccNo;
	}

	public Double getDispensaryAccountBalance() {
		return dispensaryAccountBalance;
	}

	public void setDispensaryAccountBalance(Double dispensaryAccountBalance) {
		this.dispensaryAccountBalance = dispensaryAccountBalance;
	}
	
}
