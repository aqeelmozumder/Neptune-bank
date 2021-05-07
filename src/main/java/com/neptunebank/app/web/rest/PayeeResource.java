package com.neptunebank.app.web.rest;

import com.neptunebank.app.domain.Payee;
import com.neptunebank.app.service.CustomerService;
import com.neptunebank.app.service.PayeeService;
import com.neptunebank.app.service.UserService;
import com.neptunebank.app.service.dto.CustomerDTO;
import com.neptunebank.app.service.dto.PayeeDTO;
import com.neptunebank.app.service.dto.UserDTO;
import com.neptunebank.app.service.mapper.CustomerMapper;
import com.neptunebank.app.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Payee}.
 */
@RestController
@RequestMapping("/api")
public class PayeeResource {

	private final Logger log = LoggerFactory.getLogger(PayeeResource.class);

	private static final String ENTITY_NAME = "payee";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PayeeService payeeService;

	private final UserService userService;

	private final CustomerService customerService;

	private final CustomerMapper customerMapper;

	private static class PayeeResourceException extends RuntimeException {
		private PayeeResourceException(String message) {
			super(message);
		}
	}

	public PayeeResource(PayeeService payeeService, UserService userService, CustomerService customerService, CustomerMapper customerMapper) {
		this.payeeService = payeeService;
		this.userService = userService;
		this.customerService = customerService;
		this.customerMapper = customerMapper;
	}

	/**
	 * {@code POST  /payees} : Create a new payees.
	 *
	 * @param payeeDTO the payeesDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payeesDTO, or with status {@code 400 (Bad Request)} if the payees has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/payee")
	public ResponseEntity<PayeeDTO> createPayees(@Valid @RequestBody PayeeDTO payeeDTO) throws URISyntaxException {
		log.debug("REST request to save Payee : {}", payeeDTO);
		if (payeeDTO.getPayeeID() != null) {
			throw new BadRequestAlertException("A new payees cannot already have an ID", ENTITY_NAME, "idexists");
		}
		UserDTO userDTO = userService.getUserWithAuthorities()
			.map(UserDTO::new)
			.orElseThrow(() -> new PayeeResource.PayeeResourceException("User related to payee could not be found"));
		payeeDTO.setCustomerID(userDTO.getId());
		PayeeDTO result = payeeService.save(payeeDTO);
		return ResponseEntity.created(new URI("/api/payee/" + result.getPayeeID()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getPayeeID().toString()))
			.body(result);
	}

	/**
	 * {@code PUT  /payees} : Updates an existing payees.
	 *
	 * @param payeeDTO the payeesDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payeesDTO,
	 * or with status {@code 400 (Bad Request)} if the payeesDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the payeesDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/payee")
	public ResponseEntity<PayeeDTO> updatePayees(@Valid @RequestBody PayeeDTO payeeDTO) throws URISyntaxException {
		log.debug("REST request to update Payees : {}", payeeDTO);
		if (payeeDTO.getPayeeID() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PayeeDTO result = payeeService.save(payeeDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, payeeDTO.getPayeeID().toString()))
			.body(result);
	}

	/**
	 * {@code GET  /payees} : get all the payees.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payees in body.
	 */
	@GetMapping("/payee")
	public ResponseEntity<List<PayeeDTO>> getAllPayees(Pageable pageable) {
		log.debug("REST request to get a page of Payees");
		UserDTO userDTO = userService.getUserWithAuthorities()
			.map(UserDTO::new)
			.orElseThrow(() -> new PayeeResource.PayeeResourceException("User could not be found"));
		CustomerDTO customerDTO = customerService.findOneById(userDTO.getId());
		Page<PayeeDTO> page = payeeService.findByCustomer(pageable, customerMapper.toEntity(customerDTO));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /payee/:id} : get the "id" payees.
	 *
	 * @param id the id of the payeesDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payeesDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/payee/{id}")
	public ResponseEntity<PayeeDTO> getPayees(@PathVariable Long id) {
		log.debug("REST request to get Payees : {}", id);
		Optional<PayeeDTO> payeesDTO = payeeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(payeesDTO);
	}

	/**
	 * {@code DELETE  /payee/:id} : delete the "payeeID" payees.
	 *
	 * @param id the id of the payeesDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/payee/{id}")
	public ResponseEntity<Void> deletePayees(@PathVariable Long id) {
		log.debug("REST request to delete Payees : {}", id);
		payeeService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
}
