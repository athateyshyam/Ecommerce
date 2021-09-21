package org.ecommerce.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddressRequest {
	@NotBlank(message = "Area must be required")
	private String area;
	@NotBlank(message = "City must be required")
	private String city;
	@NotBlank(message = "State must be required")
	private String state;
	@NotBlank(message = "Country must be required")
	private String country;
	@NotBlank(message = "Pin must be required")
	@Size(min = 6,max = 6,message = "PIN must have 6 digits")
	private String pin;

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
