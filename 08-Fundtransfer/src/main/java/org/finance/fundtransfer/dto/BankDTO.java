/**
 * 
 */
package org.finance.fundtransfer.dto;

import java.io.Serializable;
import java.util.List;

import org.finance.fundtransfer.entity.Account;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Athatey Shyam
 *
 */
public class BankDTO implements Serializable {

	private static final long serialVersionUID = -3969882341309106117L;
	private Long id;
	private String bankName;
	private String branchCode;
	private String ifsc;
	

	public BankDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
