package com.neptunebank.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Payees.
 */
@Entity
@Table(name = "payee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payee_id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "email_id", nullable = false)
	private String emailID;

	@NotNull
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotNull
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "telephone")
	private String telephone;

	@ManyToOne(optional = false)
	@NotNull
	private Customer customer;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

	public Long getPayeeID() {
		return id;
	}

	public Payee payeeID(Long id) {
		this.id = id;
		return this;
	}

	public void setPayeeID(Long id) {
		this.id = id;
	}

	public String getEmailID() {
		return emailID;
	}

	public Payee emailID(String emailID) {
		this.emailID = emailID;
		return this;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getFirstName() {
		return firstName;
	}

	public Payee firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Payee lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public Payee telephone(String telephone) {
		this.telephone = telephone;
		return this;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Payee customer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Payee)) {
			return false;
		}
		return id != null && id.equals(((Payee) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Payee{" +
			"payeeID=" + getPayeeID() +
			", emailID='" + getEmailID() + "'" +
			", firstName='" + getFirstName() + "'" +
			", lastName='" + getLastName() + "'" +
			", telephone='" + getTelephone() + "'" +
			"}";
	}
}
