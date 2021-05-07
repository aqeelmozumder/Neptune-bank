package com.neptunebank.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Branch.
 */
@Entity
@Table(name = "branch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@NotNull
	@Column(name = "pincode", nullable = false)
	private String pinCode;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Accounts> accounts = new HashSet<>();

	public Long getBranchID() {
		return this.id;
	}

	public Branch branchID(Long branchID) {
		this.id = branchID;
		return this;
	}

	public void setBranchID(Long branchID) {
		this.id = branchID;
	}

	public String getAddress() {
		return address;
	}

	public Branch address(String address) {
		this.address = address;
		return this;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public Branch city(String city) {
		this.city = city;
		return this;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public Branch state(String state) {
		this.state = state;
		return this;
	}

	public String getPinCode() {
		return pinCode;
	}

	public Branch pinCode(String pinCode) {
		this.pinCode = pinCode;
		return this;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Set<Accounts> getAccounts() {
		return accounts;
	}

	public Branch accounts(Set<Accounts> accounts) {
		this.accounts = accounts;
		return this;
	}

	public Branch addBAccounts(Accounts accounts) {
		this.accounts.add(accounts);
		accounts.setBranch(this);
		return this;
	}

	public Branch removeAccounts(Accounts accounts) {
		this.accounts.remove(accounts);
		accounts.setBranch(null);
		return this;
	}

	public void setAccounts(Set<Accounts> accounts) {
		this.accounts = accounts;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Branch)) {
			return false;
		}
		return id != null && id.equals(((Branch) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Branch{" +
			"branchID=" + getBranchID() +
			", address='" + getAddress() + "'" +
			", city='" + getCity() + "'" +
			", state='" + getState() + "'" +
			", pinCode='" + getPinCode() + "'" +
			"}";
	}
}
