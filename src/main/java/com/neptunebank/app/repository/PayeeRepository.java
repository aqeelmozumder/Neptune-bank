package com.neptunebank.app.repository;

import com.neptunebank.app.domain.Customer;
import com.neptunebank.app.domain.Payee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Payees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayeeRepository extends JpaRepository<Payee, Long> {

	Page<Payee> findByCustomer(Pageable pageable, Customer customer);
}
