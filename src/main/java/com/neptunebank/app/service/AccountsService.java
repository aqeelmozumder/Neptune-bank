package com.neptunebank.app.service;

import com.neptunebank.app.domain.Accounts;
import com.neptunebank.app.service.dto.AccountsDTO;
import com.neptunebank.app.service.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Accounts}.
 */
public interface AccountsService {

	/**
	 * Save a accounts.
	 *
	 * @param accountsDTO the entity to save.
	 * @return the persisted entity.
	 */
	AccountsDTO save(AccountsDTO accountsDTO);

	/**
	 * Get all the accounts.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<AccountsDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" accounts.
	 *
	 * @param accountID the id of the entity.
	 * @return the entity.
	 */
	Optional<AccountsDTO> findOne(Long accountID);

	/**
	 * Delete the "id" accounts.
	 *
	 * @param accountID the id of the entity.
	 */
	void delete(Long accountID);

	/**
	 * Get all Customer accounts
	 *
	 * @param customerDTO the id of the entity.
	 */

	Page<AccountsDTO> findAllByCustomer(Pageable pageable, CustomerDTO customerDTO);

	Page<AccountsDTO> findByIdStartingWith(Pageable pageable, String accountID);

	Page<AccountsDTO> findAllLCAccountsByActivated(Pageable pageable, Boolean activated);

	List<AccountsDTO> findAllByCustomer(CustomerDTO customerDTO);
}
