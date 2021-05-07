package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.Transaction;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link Transaction} entity.
 */
public class TransactionDTO implements Serializable {

	private Long tranID;

	private LocalDate createdDate;

	private String type;

	@NotNull
	private Double amount;

	private String fromAccount;

	private String toAccount;

	private String customerID;

	public Long getTranID() {
		return tranID;
	}

	public void setTranID(Long tranID) {
		this.tranID = tranID;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerId) {
		this.customerID = customerId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TransactionDTO transactionDTO = (TransactionDTO) o;
		if (transactionDTO.getTranID() == null || getTranID() == null) {
			return false;
		}
		return Objects.equals(getTranID(), transactionDTO.getTranID());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getTranID());
	}

	@Override
	public String toString() {
		return "TransactionDTO{" +
			"tranID=" + getTranID() +
			", createdDate='" + getCreatedDate() + "'" +
			", type='" + getType() + "'" +
			", amount=" + getAmount() + "'" +
			", toAccount='" + getToAccount() + "'" +
			", fromAccount=" + getFromAccount() +
			", customerID=" + getCustomerID() + "'" +
			"}";
	}
}
