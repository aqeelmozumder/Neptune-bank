package com.neptunebank.app.service;

import com.neptunebank.app.domain.Customer;
import com.neptunebank.app.domain.Payee;
import com.neptunebank.app.service.dto.PayeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Payee}.
 */
public interface PayeeService {

	/**
	 * Save a payees.
	 *
	 * @param payeeDTO the entity to save.
	 * @return the persisted entity.
	 */
	PayeeDTO save(PayeeDTO payeeDTO);

	/**
	 * Get all the payees.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<PayeeDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" payees.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */

	Optional<PayeeDTO> findOne(Long id);

	/**
	 * Delete the "id" payees.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	Page<PayeeDTO> findByCustomer(Pageable pageable, Customer customer);
}
