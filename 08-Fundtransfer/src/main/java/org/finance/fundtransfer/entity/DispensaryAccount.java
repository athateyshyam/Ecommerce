/**
 * 
 */
package org.finance.fundtransfer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Athatey Shyam
 *
 */
@Entity
@Table(name = "dispensary_tbl")
public class DispensaryAccount implements Serializable {

	private static final long serialVersionUID = -8872761455686379352L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Account no can never be blank")
	@Column(name = "dispensary_account_no",unique = true)
	private String dispensaryAccountNo;
	@NotBlank(message = "Account Name can never be blank")
	private String dispensaryAccountName;
	private Double dispensaryAccountBalance;

	public DispensaryAccount() {
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

	public Double getDispensaryAccountBalance() {
		return dispensaryAccountBalance;
	}

	public void setDispensaryAccountBalance(Double dispensaryAccountBalance) {
		this.dispensaryAccountBalance = dispensaryAccountBalance;
	}

}
