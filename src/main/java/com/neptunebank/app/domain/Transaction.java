package com.neptunebank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tran_id")
	private Long id;

	@NotNull
	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	@NotNull
	@Column(name = "type", nullable = false)
	private String type;

	@NotNull
	@Column(name = "amount", nullable = false)
	private Double amount;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("transactions")
	private Accounts toAccount;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("transactions")
	private Accounts fromAccount;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("transactions")
	private Customer customer;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

	public Long getTranID() {
		return id;
	}

	public Transaction tranID(Long tranID) {
		this.id = tranID;
		return this;
	}

	public void setTranID(Long tranID) {
		this.id = tranID;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public Transaction createdDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getType() {
		return type;
	}

	public Transaction type(String type) {
		this.type = type;
		return this;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public Transaction amount(Double amount) {
		this.amount = amount;
		return this;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Accounts getToAccount() {
		return toAccount;
	}

	public Transaction toAccount(Accounts accounts) {
		this.toAccount = accounts;
		return this;
	}

	public void setToAccount(Accounts account) {
		this.toAccount = account;
	}

	public Accounts getFromAccount() {
		return fromAccount;
	}

	public Transaction fromAccount(Accounts accounts) {
		this.fromAccount = accounts;
		return this;
	}

	public void setFromAccount(Accounts accounts) {
		this.fromAccount = accounts;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	public Customer getCustomer() {
		return customer;
	}

	public Transaction customer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Transaction)) {
			return false;
		}
		return id != null && id.equals(((Transaction) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Transaction{" +
			"tranID=" + getTranID() +
			", createdDate='" + getCreatedDate() + "'" +
			", type='" + getType() + "'" +
			", amount=" + getAmount() + "'" +
			", fromAccount=" + getFromAccount() + "'" +
			", toAccount=" + getToAccount() + "'" +
			"}";
	}
}
