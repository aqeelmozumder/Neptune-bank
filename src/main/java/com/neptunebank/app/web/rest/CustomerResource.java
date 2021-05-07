package com.neptunebank.app.web.rest;

import com.neptunebank.app.domain.Customer;
import com.neptunebank.app.security.AuthoritiesConstants;
import com.neptunebank.app.service.CustomerService;
import com.neptunebank.app.service.UserService;
import com.neptunebank.app.service.dto.CustomerDTO;
import com.neptunebank.app.service.dto.UserDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Customer}.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

	private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

	private static final String ENTITY_NAME = "customer";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CustomerService customerService;

	private final UserService userService;

	private static class CustomerResourceException extends RuntimeException {
		private CustomerResourceException(String message) {
			super(message);
		}
	}

	public CustomerResource(CustomerService customerService, UserService userService) {

		this.customerService = customerService;
		this.userService = userService;
	}

	/**
	 * {@code POST  /customers} : Create a new customer.
	 *
	 * @param customerDTO the customerDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerDTO, or with status {@code 400 (Bad Request)} if the customer has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/customer")
	@PreAuthorize("hasAnyAuthority({'ROLE_USER', 'ROLE_MANAGER', 'ROLE_STAFF'})")
	public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
		log.debug("REST request to save Customer : {}", customerDTO);
		if (customerDTO.getID() != null) {
			throw new BadRequestAlertException("A new customer cannot already have an ID", ENTITY_NAME, "idexists");
		}
		if (customerDTO.getUserID() == null) {
			UserDTO userDTO = userService.getUserWithAuthorities()
				.map(UserDTO::new)
				.orElseThrow(() -> new CustomerResource.CustomerResourceException("User could not be found"));
			customerDTO.setUserID(userDTO.getId());
			customerDTO.setUserLogin(userDTO.getLogin());
		}
		// if (Objects.isNull(customerDTO.getID())) {
		//    throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
		//}

		CustomerDTO result = customerService.save(customerDTO);
		return ResponseEntity.created(new URI("/api/customer/" + result.getID()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getID().toString()))
			.body(result);
	}

	/**
	 * {@code PUT  /customers} : Updates an existing customer.
	 *
	 * @param customerDTO the customerDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerDTO,
	 * or with status {@code 400 (Bad Request)} if the customerDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the customerDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_USER', 'ROLE_MANAGER', 'ROLE_STAFF'})")
	@PutMapping("/customer")
	public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
		log.debug("REST request to update Customer : {}", customerDTO);
		if (customerDTO.getID() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		CustomerDTO result = customerService.save(customerDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerDTO.getID().toString()))
			.body(result);
	}

	/**
	 * {@code GET  /customers} : get all the customers.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
	 */
	@GetMapping("/customer")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers(Pageable pageable) {
		log.debug("REST request to get a page of Customers");
		UserDTO userDTO = userService.getUserWithAuthorities()
			.map(UserDTO::new)
			.orElseThrow(() -> new CustomerResource.CustomerResourceException("User could not be found"));
		Page<CustomerDTO> page = null;
		if (userDTO.getAuthorities().contains(AuthoritiesConstants.ADMIN) || userDTO.getAuthorities().contains(AuthoritiesConstants.STAFF) || userDTO.getAuthorities().contains(AuthoritiesConstants.MANAGER)) {
			pageable = Pageable.unpaged();
			page = customerService.findAll(pageable);
		} else {
			page = customerService.findAllById(pageable, userDTO.getId());
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /customers/:id} : get the "id" customer.
	 *
	 * @param id the id of the customerDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerDTO, or with status {@code 404 (Not Found)}.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER', 'ROLE_STAFF'})")
	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
		log.debug("REST request to get Customer : {}", id);
		Optional<CustomerDTO> customerDTO = customerService.findOne(id);
		return ResponseUtil.wrapOrNotFound(customerDTO);
	}

	/**
	 * {@code DELETE  /customers/:id} : delete the "id" customer.
	 *
	 * @param id the id of the customerDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER', 'ROLE_STAFF'})")
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		log.debug("REST request to delete Customer : {}", id);
		customerService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
}
