package com.neptunebank.app.repository;

import com.neptunebank.app.domain.Accounts;
import com.neptunebank.app.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Accounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

	Page<Accounts> findAllByCustomer(Pageable pageable, Customer customer);

	List<Accounts> findAllByCustomer(Customer customer);

	@Query("select a from Accounts a where CAST(a.id as text) like :accountID%")
	Page<Accounts> findByIdStartingWith(Pageable pageable, @Param("accountID") String accountID);

	@Query("select a from Accounts a where activated=false")
	Page<Accounts> findAllLCAccountsByActivated(Pageable pageable, Boolean activated);
}
