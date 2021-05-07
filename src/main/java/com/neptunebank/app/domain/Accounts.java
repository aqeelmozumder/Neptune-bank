package com.neptunebank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accounts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;

	@NotNull
	@Column(name = "account_type", nullable = false)
	private String accountType;

	@NotNull
	@Column(name = "balance", nullable = false)
	private Double balance;

	@NotNull
	@Column(name = "activated", nullable = false)
	private Boolean activated;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("accounts")
	private Customer customer;

	@ManyToOne(optional = false)
	@NotNull
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnoreProperties("accounts")
	private Branch branch;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
	public Long getAccountID() {
		return id;
	}

	public void setAccountID(Long id) {
		this.id = id;
	}

	public Accounts accountID(Long accountID) {
		this.id = accountID;
		return this;
	}

	public String getAccountType() {
		return accountType;
	}

	public Accounts accountType(String accountType) {
		this.accountType = accountType;
		return this;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public Accounts balance(Double balance) {
		this.balance = balance;
		return this;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean isActivated() {
		return activated;
	}

	public Accounts activated(Boolean activated) {
		this.activated = activated;
		return this;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Accounts customer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Branch getBranch() {
		return branch;
	}

	public Accounts branch(Branch branch) {
		this.branch = branch;
		return this;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Accounts)) {
			return false;
		}
		return id != null && id.equals(((Accounts) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Accounts{" +
			"accountID=" + getAccountID() +
			", accountType='" + getAccountType() + "'" +
			", balance=" + getBalance() +
			", activated='" + isActivated() + "'" +
			"}";
	}
}
