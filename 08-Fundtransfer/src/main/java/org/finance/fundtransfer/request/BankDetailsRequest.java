/**
 * 
 */
package org.finance.fundtransfer.request;

import java.io.Serializable;
import java.util.List;

import org.finance.fundtransfer.entity.Account;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Athatey Shyam
 *
 */
public class BankDetailsRequest{

	private String bankName;
	private String branchCode;
	private String ifsc;
	

	public BankDetailsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

}
