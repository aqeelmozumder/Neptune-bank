package com.neptunebank.app.repository;

import com.neptunebank.app.domain.Customer;
import com.neptunebank.app.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Transactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Page<Transaction> findAllByCustomer(Pageable pageable, Customer customer);

	List<Transaction> findAllByCustomer(Customer customer);
}
