package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.Branch;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Branch} entity.
 */
public class BranchDTO implements Serializable {

	private Long branchID;

	@NotNull
	private String address;

	private String city;

	private String state;

	@NotNull
	private String pinCode;

	public Long getBranchID() {
		return branchID;
	}

	public void setBranchID(Long branchID) {
		this.branchID = branchID;
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

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		BranchDTO branchDTO = (BranchDTO) o;
		if (branchDTO.getBranchID() == null || getBranchID() == null) {
			return false;
		}
		return Objects.equals(getBranchID(), branchDTO.getBranchID());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getBranchID());
	}

	@Override
	public String toString() {
		return "BranchDTO{" +
			"branchID=" + getBranchID() +
			", address='" + getAddress() + "'" +
			", city='" + getCity() + "'" +
			", state='" + getState() + "'" +
			", pincode='" + getPinCode() + "'" +
			"}";
	}
}
