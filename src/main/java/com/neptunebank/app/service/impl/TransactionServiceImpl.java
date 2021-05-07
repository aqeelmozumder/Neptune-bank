package com.neptunebank.app.service.impl;

import com.neptunebank.app.domain.Transaction;
import com.neptunebank.app.repository.TransactionRepository;
import com.neptunebank.app.service.TransactionService;
import com.neptunebank.app.service.dto.CustomerDTO;
import com.neptunebank.app.service.dto.TransactionDTO;
import com.neptunebank.app.service.mapper.CustomerMapper;
import com.neptunebank.app.service.mapper.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private final TransactionRepository transactionRepository;

	private final TransactionMapper transactionMapper;

	private final CustomerMapper customerMapper;

	public TransactionServiceImpl(TransactionRepository transactionsRepository, TransactionMapper transactionMapper, CustomerMapper customerMapper) {
		this.transactionRepository = transactionsRepository;
		this.transactionMapper = transactionMapper;
		this.customerMapper = customerMapper;
	}

	/**
	 * Save a transactions.
	 *
	 * @param transactionDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public TransactionDTO save(TransactionDTO transactionDTO) {
		log.debug("Request to save Transactions : {}", transactionDTO);
		Transaction transaction = transactionMapper.toEntity(transactionDTO);
		transaction = transactionRepository.save(transaction);
		return transactionMapper.toDto(transaction);
	}

	/**
	 * Get all the transactions.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<TransactionDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Transactions");
		return transactionRepository.findAll(pageable)
			.map(transactionMapper::toDto);
	}

	/**
	 * Get one transactions by id.
	 *
	 * @param tranID the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<TransactionDTO> findOne(Long tranID) {
		log.debug("Request to get Transactions : {}", tranID);
		return transactionRepository.findById(tranID)
			.map(transactionMapper::toDto);
	}

	/**
	 * Delete the transactions by id.
	 *
	 * @param tranID the id of the entity.
	 */
	@Override
	public void delete(Long tranID) {
		log.debug("Request to delete Transactions : {}", tranID);
		transactionRepository.deleteById(tranID);
	}

	@Override
	public Page<TransactionDTO> findAllByCustomer(Pageable pageable, CustomerDTO customerDTO) {
		log.debug("Request to get all Transactions for customer of id : " + customerDTO.getID());
		return transactionRepository.findAllByCustomer(pageable, customerMapper.toEntity(customerDTO)).map(transactionMapper::toDto);
	}
}
