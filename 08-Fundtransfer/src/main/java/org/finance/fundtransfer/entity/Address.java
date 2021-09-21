package org.finance.fundtransfer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_address_tbl")
public class Address implements Serializable {

	private static final long serialVersionUID = 4649379110426847345L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Area is mandatory")
	private String area;
	@NotBlank(message = "City is mandatory")
	private String city;
	@NotBlank(message = "State is mandatory")
	private String state;
	@NotBlank(message = "PinCode is mandatory")
	@Size(min = 6, max = 6, message = "Pin Code must contains 6 digits only")
	private String pin;
	@NotBlank(message = "Country is mandatory")
	private String country;

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
