/**
 * 
 */
package org.finance.fundtransfer.dto;

import java.io.Serializable;

/**
 * @author Intel
 *
 */
public class AddressDTO implements Serializable {

	private static final long serialVersionUID = -245418924527114996L;

	private String area;
	private String city;
	private String state;
	private String pin;
	private String country;

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
