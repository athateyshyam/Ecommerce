/**
 * 
 */
package org.finance.fundtransfer.dto;

import java.io.Serializable;
import java.util.List;

import org.finance.fundtransfer.entity.Account;
import org.finance.fundtransfer.entity.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Athatey Shyam
 *
 */
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 3716594963339501120L;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String mobile;
	private String gender;
	private String age;
	private String panNo;
	private String aadharNo;
	private String username;
	@JsonIgnore
	private String password;
	private Account account;
	private BankDTO bankDto;
	private List<Address> listOfAddress;

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Address> getListOfAddress() {
		return listOfAddress;
	}

	public void setListOfAddress(List<Address> listOfAddress) {
		this.listOfAddress = listOfAddress;
	}

	public BankDTO getBankDto() {
		return bankDto;
	}

	public void setBankDto(BankDTO bankDto) {
		this.bankDto = bankDto;
	}

}
