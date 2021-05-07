package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.Customer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Customer} entity.
 */
public class CustomerDTO implements Serializable {

	private Long id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String telephone;

	@NotNull
	private String gender;

	@NotNull
	private String address;

	@NotNull
	private String city;

	@NotNull
	private String state;

	@NotNull
	private String zipCode;

	private Long pin;

	private Long userID;

	private String userLogin;

	public Long getID() {
		return id;
	}

	public void setID(Long id) {
		this.id = id;
	}

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userId) {
		this.userID = userId;
	}

	public Long getPin() {
		return pin;
	}

	public void setPin(Long pin) {
		this.pin = pin;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CustomerDTO customerDTO = (CustomerDTO) o;
		if (customerDTO.getID() == null || getID() == null) {
			return false;
		}
		return Objects.equals(getID(), customerDTO.getID());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getID());
	}

	@Override
	public String toString() {
		return "CustomerDTO{" +
			"id=" + getID() +
			", firstName='" + getFirstName() + "'" +
			", lastName='" + getLastName() + "'" +
			", telephone='" + getTelephone() + "'" +
			", gender='" + getGender() + "'" +
			", address='" + getAddress() + "'" +
			", city='" + getCity() + "'" +
			", state='" + getState() + "'" +
			", zipCode='" + getZipCode() + "'" +
			", pin='" + getPin() + "'" +
			", userID=" + getUserID() + "'" +
			", userLogin='" + getUserLogin() +
			"}";
	}
}
