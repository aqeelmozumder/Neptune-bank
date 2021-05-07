package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.Payee;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Payee} entity.
 */
public class PayeeDTO implements Serializable {

	private Long payeeID;

	@NotNull
	private String emailID;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String telephone;

	private Long customerID;

	public Long getPayeeID() {
		return payeeID;
	}

	public void setPayeeID(Long payeeID) {
		this.payeeID = payeeID;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
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

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PayeeDTO payeeDTO = (PayeeDTO) o;
		if (payeeDTO.getPayeeID() == null || getPayeeID() == null) {
			return false;
		}
		return Objects.equals(getPayeeID(), payeeDTO.getPayeeID());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getPayeeID());
	}

	@Override
	public String toString() {
		return "PayeeDTO{" +
			"payeeID=" + getPayeeID() +
			", emailID='" + getEmailID() + "'" +
			", firstName='" + getFirstName() + "'" +
			", lastName='" + getLastName() + "'" +
			", telephone='" + getTelephone() + "'" +
			", customerID=" + getCustomerID() + "'" +
			"}";
	}
}
