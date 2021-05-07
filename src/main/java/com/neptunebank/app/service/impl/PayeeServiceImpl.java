package com.neptunebank.app.service.impl;

import com.neptunebank.app.domain.Customer;
import com.neptunebank.app.domain.Payee;
import com.neptunebank.app.repository.PayeeRepository;
import com.neptunebank.app.service.PayeeService;
import com.neptunebank.app.service.dto.PayeeDTO;
import com.neptunebank.app.service.mapper.CustomerMapper;
import com.neptunebank.app.service.mapper.PayeeMapper;
import com.neptunebank.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing {@link Payee}.
 */
@Service
@Transactional
public class PayeeServiceImpl implements PayeeService {

	private final Logger log = LoggerFactory.getLogger(PayeeServiceImpl.class);

	private final PayeeRepository payeeRepository;

	private final PayeeMapper payeeMapper;

	private final CustomerMapper customerMapper;

	public PayeeServiceImpl(PayeeRepository payeeRepository, PayeeMapper payeeMapper, CustomerMapper customerMapper) {
		this.payeeRepository = payeeRepository;
		this.payeeMapper = payeeMapper;
		this.customerMapper = customerMapper;
	}

	/**
	 * Save a payees.
	 *
	 * @param payeeDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public PayeeDTO save(PayeeDTO payeeDTO) {
		log.debug("Request to save Payees : {}", payeeDTO);
		Payee payees = payeeMapper.toEntity(payeeDTO);
		try {
			payees = payeeRepository.save(payees);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestAlertException("Please Register Customer details before adding any payee", ENTITY_NAME, "Customer Registration");
		} catch (Exception e) {
			throw new BadRequestAlertException("Please Contact your Branch Manager", ENTITY_NAME, "Generic Exception");
		}

		return payeeMapper.toDto(payees);
	}

	/**
	 * Get all the payees.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PayeeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Payees");
		return payeeRepository.findAll(pageable)
			.map(payeeMapper::toDto);
	}

	/**
	 * Get one payees by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PayeeDTO> findOne(Long id) {
		log.debug("Request to get Payees : {}", id);
		return payeeRepository.findById(id)
			.map(payeeMapper::toDto);
	}

	/**
	 * Delete the payees by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Payees : {}", id);
		payeeRepository.deleteById(id);
	}

	@Override
	public Page<PayeeDTO> findByCustomer(Pageable pageable, Customer customer) {
		return payeeRepository.findByCustomer(pageable, customer).map(payeeMapper::toDto);
	}
}
