package com.neptunebank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotNull
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "telephone")
	private Long telephone;

	@NotNull
	@Column(name = "gender", nullable = false)
	@Size(min = 1, max = 1)
	private String gender;

	@NotNull
	@Column(name = "address", nullable = false)
	private String address;

	@NotNull
	@Column(name = "city", nullable = false)
	private String city;

	@NotNull
	@Column(name = "state", nullable = false)
	private String state;

	@NotNull
	@Column(name = "zipcode", nullable = false)
	private String zipCode;

	@NotNull
	@JsonIgnore
	@Column(name = "pin", nullable = false)
	private Long pin;

	@OneToOne(optional = false)
	@NotNull
	@MapsId
	@JoinColumn(name = "id")
	private User user;

	public Long getID() {
		return id;
	}

	public Customer ID(Long id) {
		this.id = id;
		return this;
	}

	public void setID(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public Customer firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Customer lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getTelephone() {
		return telephone;
	}

	public Customer telephone(Long telephone) {
		this.telephone = telephone;
		return this;
	}

	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	public String getGender() {
		return gender;
	}

	public Customer gender(String gender) {
		this.gender = gender;
		return this;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public Customer address(String address) {
		this.address = address;
		return this;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public Customer city(String city) {
		this.city = city;
		return this;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public Customer state(String state) {
		this.state = state;
		return this;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public Customer zipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getPin() {
		return pin;
	}

	public Customer state(Long pin) {
		this.pin = pin;
		return this;
	}

	public void setPin(Long pin) {
		this.pin = pin;
	}

	public User getUser() {
		return user;
	}

	public Customer user(User user) {
		this.user = user;
		return this;
	}

	public void setUser(User user) {
		this.user = user;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Customer)) {
			return false;
		}
		return id != null && id.equals(((Customer) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Customer{" +
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
			"}";
	}
}
