package com.neptunebank.app.web.rest;

import com.neptunebank.app.domain.Accounts;
import com.neptunebank.app.security.AuthoritiesConstants;
import com.neptunebank.app.service.AccountsService;
import com.neptunebank.app.service.CustomerService;
import com.neptunebank.app.service.UserService;
import com.neptunebank.app.service.dto.AccountsDTO;
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
 * REST controller for managing {@link Accounts}.
 */
@RestController
@RequestMapping("/api")
public class AccountsResource {

	private final Logger log = LoggerFactory.getLogger(AccountsResource.class);

	private static final String ENTITY_NAME = "accounts";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AccountsService accountsService;

	private final UserService userService;

	private final CustomerService customerService;

	private static class AccountsResourceException extends RuntimeException {
		private AccountsResourceException(String message) {
			super(message);
		}
	}

	public AccountsResource(AccountsService accountsService, UserService userService, CustomerService customerService) {
		this.accountsService = accountsService;
		this.userService = userService;
		this.customerService = customerService;
	}

	/**
	 * {@code POST  /accounts} : Create a new accounts.
	 *
	 * @param accountsDTO the accountsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountsDTO, or with status {@code 400 (Bad Request)} if the accounts has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/accounts")
	public ResponseEntity<AccountsDTO> createAccounts(@Valid @RequestBody AccountsDTO accountsDTO) throws URISyntaxException {
		log.debug("REST request to save Accounts : {}", accountsDTO);
		if (accountsDTO.getAccountID() != null) {
			throw new BadRequestAlertException("A new accounts cannot already have an ID", ENTITY_NAME, "idexists");
		}
		accountsDTO.setActivated(false);
		AccountsDTO result = accountsService.save(accountsDTO);
		return ResponseEntity.created(new URI("/api/accounts/" + result.getAccountID()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getAccountID().toString()))
			.body(result);
	}

	/**
	 * {@code PUT  /accounts} : Updates an existing accounts.
	 *
	 * @param accountsDTO the accountsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountsDTO,
	 * or with status {@code 400 (Bad Request)} if the accountsDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the accountsDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/accounts")
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER', 'ROLE_STAFF'})")
	public ResponseEntity<AccountsDTO> updateAccounts(@Valid @RequestBody AccountsDTO accountsDTO) throws URISyntaxException {
		log.debug("REST request to update Accounts : {}", accountsDTO);
		if (accountsDTO.getAccountID() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AccountsDTO result = accountsService.save(accountsDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accountsDTO.getAccountID().toString()))
			.body(result);
	}

	/**
	 * {@code GET  /accounts} : get all the accounts.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accounts in body.
	 */
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountsDTO>> getAllAccounts(Pageable pageable) {
		log.debug("REST request to get a page of Accounts");
		Page<AccountsDTO> page = null;
		UserDTO userDTO = userService.getUserWithAuthorities()
			.map(UserDTO::new)
			.orElseThrow(() -> new AccountsResource.AccountsResourceException("User could not be found"));
		CustomerDTO customerDTO = customerService.findOneById(userDTO.getId());
		if (customerDTO == null) {
			throw new BadRequestAlertException("Please Register Customer details for account creation", ENTITY_NAME, "Customer Creation");
		}
		if (userDTO.getAuthorities().contains(AuthoritiesConstants.ADMIN) || userDTO.getAuthorities().contains(AuthoritiesConstants.STAFF) || userDTO.getAuthorities().contains(AuthoritiesConstants.MANAGER)) {
			pageable = Pageable.unpaged();
			page = accountsService.findAll(pageable);
		} else {
			page = accountsService.findAllByCustomer(pageable, customerDTO);
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /accounts/:id} : get the "id" accounts.
	 *
	 * @param accountID the id of the accountsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountsDTO, or with status {@code 404 (Not Found)}.
	 */

	@GetMapping("/accounts/{accountID}")
	public ResponseEntity<AccountsDTO> getAccounts(@PathVariable Long accountID) {
		log.debug("REST request to get Accounts : {}", accountID);
		Optional<AccountsDTO> accountsDTO = accountsService.findOne(accountID);
		return ResponseUtil.wrapOrNotFound(accountsDTO);
	}

	/**
	 * {@code DELETE  /accounts/:id} : delete the "id" accounts.
	 *
	 * @param accountID the id of the accountsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER', 'ROLE_STAFF'})")
	@DeleteMapping("/accounts/{accountID}")
	public ResponseEntity<Void> deleteAccounts(@PathVariable Long accountID) {
		log.debug("REST request to delete Accounts : {}", accountID);
		accountsService.delete(accountID);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, accountID.toString())).build();
	}

	@GetMapping("/accounts/filter/accountId/{accountID}")
	public ResponseEntity<List<AccountsDTO>> findByIdStartingWith(Pageable pageable, @PathVariable String accountID) {
		log.debug("REST request to filter an account by account ID " + accountID);
		Page<AccountsDTO> page = accountsService.findByIdStartingWith(pageable, accountID);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER'})")
	@GetMapping("/accounts/creditLoanAccounts")
	public ResponseEntity<List<AccountsDTO>> getAllNonApprovedLCAccounts(Pageable pageable) {
		log.debug("Request to get accounts with Credit or Loan Approval pending");
		Page<AccountsDTO> page = accountsService.findAllLCAccountsByActivated(pageable, false);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
