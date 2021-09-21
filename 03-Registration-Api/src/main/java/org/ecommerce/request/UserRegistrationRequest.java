package org.ecommerce.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationRequest {
	@NotBlank(message = "First name must be required")
	private String firstName;
	@NotBlank(message = "Last name must be required")
	private String lastName;
	@NotBlank(message = "Email must be required")
	@Email(message = "Enter valid email address")
	private String email;
	@NotBlank(message = "Mobile number must be required")
	@Size(min = 10,max = 10,message = "Mobile number must have 10 digits")
	private String mobile;
	@NotBlank(message = "Gender must be required")
	private String gender;
	@NotNull(message = "Age must be required")
	@Min(value = 18, message = "Age should not be less than 18")
	@Max(value = 150, message = "Age should not be greater than 150")
	private int age;
	private AddressRequest address;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public AddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddressRequest address) {
		this.address = address;
	}

}
