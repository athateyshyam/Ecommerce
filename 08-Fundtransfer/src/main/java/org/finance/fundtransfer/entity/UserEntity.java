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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Athatey Shyam
 *
 */
@Entity
@Table(name = "user_tbl")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1122432443107423932L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Name is mandatory")
	private String firstName;
	private String middleName;
	@NotBlank(message = "Last Name is mandatory")
	private String lastName;
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	@Column(name = "email",unique = true)
	private String email;
	@NotBlank(message = "Mobile Number is mandatory")
	@Size(min = 10, max = 10, message = "Mobile number must contains 10 digits only")
	private String mobile;
	@NotBlank(message = "Gender is mandatory")
	private String gender;
	@NotBlank(message = "Age is mandatory")
	@Min(value = 18, message = "Age should not be less than 18")
	@Max(value = 150, message = "Age should not be greater than 150")
	private String age;
	@NotBlank(message = "PAN Number is mandatory")
	@Column(name = "pan_no",unique = true)
	private String panNo;
	@NotBlank(message = "Aadhar Number is mandatory")
	@Size(min = 12, max = 12, message = "Aadhar number must contains 12 digits only")
	@Column(name = "aadhar_no",unique = true)
	private String aadharNo;
	private String username;
	@JsonIgnore
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Address> listOfAddress;

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Address> getListOfAddress() {
		return listOfAddress;
	}

	public void setListOfAddress(List<Address> listOfAddress) {
		this.listOfAddress = listOfAddress;
	}
}
