/**
 * 
 */
package org.finance.fundtransfer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Athatey Shyam
 *
 */
@Entity
@Table(name = "user_account_tbl")
public class Account implements Serializable {

	private static final long serialVersionUID = -6517034757250187828L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	private String accNo;
	private String accType;
	private Double balance;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private List<AccountTransaction> accountTransction;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private List<DispensaryAccount> listOfDispensary;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<AccountTransaction> getAccountTransction() {
		return accountTransction;
	}

	public void setAccountTransction(List<AccountTransaction> accountTransction) {
		this.accountTransction = accountTransction;
	}

	public List<DispensaryAccount> getListOfDispensary() {
		return listOfDispensary;
	}

	public void setListOfDispensary(List<DispensaryAccount> listOfDispensary) {
		this.listOfDispensary = listOfDispensary;
	}

	
}
