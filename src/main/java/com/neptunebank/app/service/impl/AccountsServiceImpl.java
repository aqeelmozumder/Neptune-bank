package com.neptunebank.app.service.impl;

import com.neptunebank.app.domain.Accounts;
import com.neptunebank.app.repository.AccountsRepository;
import com.neptunebank.app.service.AccountsService;
import com.neptunebank.app.service.dto.AccountsDTO;
import com.neptunebank.app.service.dto.CustomerDTO;
import com.neptunebank.app.service.mapper.AccountsMapper;
import com.neptunebank.app.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Accounts}.
 */
@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

	private final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

	private final AccountsRepository accountsRepository;

	private final AccountsMapper accountsMapper;

	private final CustomerMapper customerMapper;

	public AccountsServiceImpl(AccountsRepository accountsRepository, AccountsMapper accountsMapper, CustomerMapper customerMapper) {
		this.accountsRepository = accountsRepository;
		this.accountsMapper = accountsMapper;
		this.customerMapper = customerMapper;
	}

	/**
	 * Save a accounts.
	 *
	 * @param accountsDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public AccountsDTO save(AccountsDTO accountsDTO) {
		log.debug("Request to save Accounts : {}", accountsDTO);
		Accounts accounts = accountsMapper.toEntity(accountsDTO);
		accounts = accountsRepository.save(accounts);
		return accountsMapper.toDto(accounts);
	}

	/**
	 * Get all the accounts.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<AccountsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Accounts");
		return accountsRepository.findAll(pageable)
			.map(accountsMapper::toDto);
	}

	/**
	 * Get one accounts by id.
	 *
	 * @param accountID the accountID of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AccountsDTO> findOne(Long accountID) {
		log.debug("Request to get Accounts : {}", accountID);
		return accountsRepository.findById(accountID)
			.map(accountsMapper::toDto);
	}

	/**
	 * Delete the accounts by id.
	 *
	 * @param accountID the accountID of the entity.
	 */
	@Override
	public void delete(Long accountID) {
		log.debug("Request to delete Accounts : {}", accountID);
		accountsRepository.deleteById(accountID);
	}

	@Override
	public Page<AccountsDTO> findAllByCustomer(Pageable pageable, CustomerDTO customerDTO) {
		log.debug("Request to get all customer accounts of id : " + customerDTO.getID());
		return accountsRepository.findAllByCustomer(pageable, customerMapper.toEntity(customerDTO)).map(accountsMapper::toDto);
	}

	@Override
	public List<AccountsDTO> findAllByCustomer(CustomerDTO customerDTO) {
		log.debug("Request to get all customer accounts of id : " + customerDTO.getID());
		List<Accounts> accounts = accountsRepository.findAllByCustomer(customerMapper.toEntity(customerDTO));
		List<AccountsDTO> accountsDTOS = new ArrayList<AccountsDTO>();
		for (Accounts account : accounts) {
			accountsDTOS.add(accountsMapper.toDto(account));
		}
		return accountsDTOS;
	}

	@Override
	public Page<AccountsDTO> findByIdStartingWith(Pageable pageable, String accountID) {
		log.debug("Request to get accounts by id starting with : " + accountID);
		return accountsRepository.findByIdStartingWith(pageable, accountID)
			.map(accountsMapper::toDto);
	}

	@Override
	public Page<AccountsDTO> findAllLCAccountsByActivated(Pageable pageable, Boolean activated) {
		log.debug("Request to get all accounts Loan and credit accounts with status activated");
		return accountsRepository.findAllLCAccountsByActivated(pageable, activated)
			.map(accountsMapper::toDto);
	}
}
