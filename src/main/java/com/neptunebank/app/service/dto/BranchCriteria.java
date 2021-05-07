package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.Branch;
import com.neptunebank.app.web.rest.BranchResource;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link Branch} entity. This class is used
 * in {@link BranchResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /branches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BranchCriteria implements Serializable, Criteria {

	private static final long serialVersionUID = 1L;

	private LongFilter branchID;

	private StringFilter address;

	private StringFilter city;

	private StringFilter state;

	private StringFilter pinCode;

	private LongFilter branchToAccountsId;

	public BranchCriteria() {
	}

	public BranchCriteria(BranchCriteria other) {
		this.branchID = other.branchID == null ? null : other.branchID.copy();
		this.address = other.address == null ? null : other.address.copy();
		this.city = other.city == null ? null : other.city.copy();
		this.state = other.state == null ? null : other.state.copy();
		this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
		this.branchToAccountsId = other.branchToAccountsId == null ? null : other.branchToAccountsId.copy();
	}

	@Override
	public BranchCriteria copy() {
		return new BranchCriteria(this);
	}

	public LongFilter getBranchID() {
		return branchID;
	}

	public void setBranchID(LongFilter id) {
		this.branchID = id;
	}

	public StringFilter getAddress() {
		return address;
	}

	public void setAddress(StringFilter address) {
		this.address = address;
	}

	public StringFilter getCity() {
		return city;
	}

	public void setCity(StringFilter addressLine2) {
		this.city = city;
	}

	public StringFilter getState() {
		return state;
	}

	public void setState(StringFilter state) {
		this.state = state;
	}

	public StringFilter getPinCode() {
		return pinCode;
	}

	public void setPinCode(StringFilter pinCode) {
		this.pinCode = pinCode;
	}

	public LongFilter getBranchToAccountsId() {
		return branchToAccountsId;
	}

	public void setBranchToAccountsId(LongFilter branchToAccountsId) {
		this.branchToAccountsId = branchToAccountsId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final BranchCriteria that = (BranchCriteria) o;
		return
			Objects.equals(branchID, that.branchID) &&
				Objects.equals(address, that.address) &&
				Objects.equals(city, that.city) &&
				Objects.equals(state, that.state) &&
				Objects.equals(pinCode, that.pinCode) &&
				Objects.equals(branchToAccountsId, that.branchToAccountsId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			branchID,
			address,
			city,
			state,
			pinCode,
			branchToAccountsId
		);
	}

	@Override
	public String toString() {
		return "BranchCriteria{" +
			(branchID != null ? "id=" + branchID + ", " : "") +
			(address != null ? "address=" + address + ", " : "") +
			(city != null ? "city=" + city + ", " : "") +
			(state != null ? "state=" + state + ", " : "") +
			(pinCode != null ? "pincode=" + pinCode + ", " : "") +
			(branchToAccountsId != null ? "branchToAccountsId=" + branchToAccountsId + ", " : "") +
			"}";
	}
}
