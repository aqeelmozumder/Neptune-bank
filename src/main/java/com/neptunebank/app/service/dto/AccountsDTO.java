package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.Accounts;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Accounts} entity.
 */
public class AccountsDTO implements Serializable {

	private Long accountID;

	@NotNull
	private String accountType;

	@NotNull
	private Double balance;

	@NotNull
	private Boolean activated;

	private Long customerID;

	private Long branchID;

	private String userLogin;

	private String branchAddress;

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean isActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerId) {
		this.customerID = customerId;
	}

	public Long getBranchID() {
		return branchID;
	}

	public void setBranchID(Long branchID) {
		this.branchID = branchID;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getBranchAddress() {
		return this.branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		AccountsDTO accountsDTO = (AccountsDTO) o;
		if (accountsDTO.getAccountID() == null || getAccountID() == null) {
			return false;
		}
		return Objects.equals(getAccountID(), accountsDTO.getAccountID());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getAccountID());
	}

	@Override
	public String toString() {
		return "AccountsDTO{" +
			"accountID=" + getAccountID() +
			", accountType='" + getAccountType() + "'" +
			", balance=" + getBalance() +
			", activated='" + isActivated() + "'" +
			", customerID=" + getCustomerID() +
			", branchID='" + getBranchID() +
			", userID='" + getUserLogin() +
			", branchAddress='" + getBranchAddress() + "'" +
			"}";
	}
}
