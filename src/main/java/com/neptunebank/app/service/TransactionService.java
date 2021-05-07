package com.neptunebank.app.service;

import com.neptunebank.app.domain.Transaction;
import com.neptunebank.app.service.dto.CustomerDTO;
import com.neptunebank.app.service.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Transaction}.
 */
public interface TransactionService {

	/**
	 * Save a transactions.
	 *
	 * @param transactionDTO the entity to save.
	 * @return the persisted entity.
	 */
	TransactionDTO save(TransactionDTO transactionDTO);

	/**
	 * Get all the transactions.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<TransactionDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" transactions.
	 *
	 * @param tranID the id of the entity.
	 * @return the entity.
	 */
	Optional<TransactionDTO> findOne(Long tranID);

	/**
	 * Delete the "id" transactions.
	 *
	 * @param tranID the id of the entity.
	 */
	void delete(Long tranID);

	Page<TransactionDTO> findAllByCustomer(Pageable pageable, CustomerDTO customerDTO);
}
