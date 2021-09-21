/**
 * 
 */
package org.finance.fundtransfer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Athatey Shyam
 *
 */
@Entity
@Table(name = "bank_details_tbl")
public class BankEntity implements Serializable {

	private static final long serialVersionUID = 8121414647261527820L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Bank name must required")
	private String bankName;
	@NotBlank(message = "Bank branch code must required")
	@Column(name = "branch_code",unique = true)
	private String branchCode;
	@NotBlank(message = "Bank IFSC must required")
	@Column(name = "ifsc",unique = true)
	private String ifsc;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_id")
	private List<Account> listOfAccounts;

	public BankEntity() {
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

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<Account> getListOfAccounts() {
		return listOfAccounts;
	}

	public void setListOfAccounts(List<Account> listOfAccounts) {
		this.listOfAccounts = listOfAccounts;
	}

}
