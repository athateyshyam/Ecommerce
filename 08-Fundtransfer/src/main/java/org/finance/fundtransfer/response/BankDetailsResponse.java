/**
 * 
 */
package org.finance.fundtransfer.response;

/**
 * @author Athatey Shyam
 *
 */
public class BankDetailsResponse {
	private String bankName;
	private String branchCode;
	private String ifsc;

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
