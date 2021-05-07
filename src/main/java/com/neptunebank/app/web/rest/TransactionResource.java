package com.neptunebank.app.web.rest;

import com.neptunebank.app.domain.Transaction;
import com.neptunebank.app.domain.User;
import com.neptunebank.app.repository.UserRepository;
import com.neptunebank.app.security.AuthoritiesConstants;
import com.neptunebank.app.service.AccountsService;
import com.neptunebank.app.service.CustomerService;
import com.neptunebank.app.service.TransactionService;
import com.neptunebank.app.service.UserService;
import com.neptunebank.app.service.dto.AccountsDTO;
import com.neptunebank.app.service.dto.CustomerDTO;
import com.neptunebank.app.service.dto.TransactionDTO;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Transaction}.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {

	private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

	private static final String ENTITY_NAME = "transactions";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TransactionService transactionService;

	private final UserService userService;

	private final CustomerService customerService;

	private final AccountsService accountsService;

	private final UserRepository userRepository;

	private static class TransactionResourceException extends RuntimeException {
		private TransactionResourceException(String message) {
			super(message);
		}
	}

	public TransactionResource(TransactionService transactionService, UserService userService, CustomerService customerService,
		AccountsService accountsService, UserRepository userRepository) {
		this.transactionService = transactionService;
		this.userService = userService;
		this.customerService = customerService;
		this.accountsService = accountsService;
		this.userRepository = userRepository;
	}

	/**
	 * {@code POST  /transactions} : Create a new transactions.
	 *
	 * @param transactionDTO the transactionsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionsDTO, or with status {@code 400 (Bad Request)} if the transactions has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/transactions")
	public ResponseEntity<TransactionDTO> createTransactions(@Valid @RequestBody TransactionDTO transactionDTO) throws URISyntaxException {
		log.debug("REST request to save Transactions : {}", transactionDTO);
		AccountsDTO fromAccountDTO;
		AccountsDTO toAccountDTO;
		Long fromAccount;
		Long toAccount;
		if (transactionDTO.getTranID() != null) {
			throw new BadRequestAlertException("A new transactions cannot already have an ID", ENTITY_NAME, "idexists");
		}
		try {
			fromAccount = Long.parseLong(transactionDTO.getFromAccount());
			toAccount = Long.parseLong(transactionDTO.getToAccount());
		} catch (Exception e) {
			throw new BadRequestAlertException("Please Enter Valid Account Number", ENTITY_NAME, "Invalid Account ID");
		}

		try {
			fromAccountDTO = accountsService.findOne(fromAccount)
				.orElseThrow(() -> new TransactionResource.TransactionResourceException("Account detail could not be found: " + fromAccount));
			toAccountDTO = accountsService.findOne(toAccount)
				.orElseThrow(() -> new TransactionResource.TransactionResourceException("Account detail could not be found: " + toAccount));
		} catch (Exception e) {
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "Invalid Account");
		}
		if (!fromAccountDTO.isActivated()) {
			throw new BadRequestAlertException("Please visit your branch for account Activation", ENTITY_NAME, "Account Activation");
		}
		if (!toAccountDTO.isActivated()) {
			throw new BadRequestAlertException("Receivers Account is Not Active", ENTITY_NAME, "Account Activation");
		}
		Double balance = fromAccountDTO.getBalance();
		Double amount = transactionDTO.getAmount();
		if (amount > balance) {
			throw new BadRequestAlertException("Insufficient funds to process this transaction", ENTITY_NAME, "insufficient funds");
		}
		transactionDTO.setType("D");
		transactionDTO.setCreatedDate(LocalDate.now());
		transactionDTO.setCustomerID(Long.toString(fromAccountDTO.getCustomerID()));
		TransactionDTO result = transactionService.save(transactionDTO);
		fromAccountDTO.setBalance(balance - amount);
		accountsService.save(fromAccountDTO);

		transactionDTO.setType("C");
		transactionDTO.setCustomerID(Long.toString(toAccountDTO.getCustomerID()));
		transactionService.save(transactionDTO);
		toAccountDTO.setBalance(toAccountDTO.getBalance() + amount);
		accountsService.save(toAccountDTO);

		return ResponseEntity.created(new URI("/api/transactions/" + result.getTranID()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getTranID().toString()))
			.body(result);
	}

	@PostMapping("transactions/payeeTransfer")
	public ResponseEntity<TransactionDTO> payeeTransfer(@Valid @RequestBody TransactionDTO transactionDTO) throws URISyntaxException {
		log.debug("REST request to save Transactions : {}", transactionDTO);
		AccountsDTO fromAccountDTO = null;
		AccountsDTO toAccountDTO = null;
		Long fromAccount;
		Long toAccount;
		TransactionDTO result = null;

		if (transactionDTO.getTranID() != null) {
			throw new BadRequestAlertException("A new transactions cannot already have an ID", ENTITY_NAME, "idexists");
		}

		try {
			fromAccount = Long.parseLong(transactionDTO.getFromAccount());
		} catch (Exception e) {
			throw new BadRequestAlertException("Please Enter Valid Account Number", ENTITY_NAME, "Invalid Account ID");
		}

		try {
			fromAccountDTO = accountsService.findOne(fromAccount)
				.orElseThrow(() -> new TransactionResource.TransactionResourceException("Account detail could not be found: " + fromAccount));
		} catch (Exception e) {
			throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "Invalid Account");
		}
		if (!fromAccountDTO.isActivated()) {
			throw new BadRequestAlertException("Please visit your branch for account Activation", ENTITY_NAME, "Account Activation");
		}

		String payee = transactionDTO.getToAccount();
		User user = userRepository.findOneByEmailIgnoreCase(payee).orElseGet(User::new);
		if (user.getId() == null) {
			toAccount = new Long(1111111);
			try {
				toAccountDTO = accountsService.findOne(toAccount)
					.orElseThrow(() -> new TransactionResource.TransactionResourceException("Bank Account for Payee transfer doesn't exists: " + toAccount));
			} catch (Exception e) {
				throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "Invalid Account");
			}
			Double balance = fromAccountDTO.getBalance();
			Double amount = transactionDTO.getAmount();
			if (amount > balance) {
				throw new BadRequestAlertException("Insufficient funds to process this transaction", ENTITY_NAME, "insufficient funds");
			}
			transactionDTO.setType("D");
			transactionDTO.setCreatedDate(LocalDate.now());
			transactionDTO.setCustomerID(Long.toString(fromAccountDTO.getCustomerID()));
			transactionDTO.setToAccount(toAccountDTO.getAccountID().toString());
			result = transactionService.save(transactionDTO);
			fromAccountDTO.setBalance(balance - amount);
			accountsService.save(fromAccountDTO);

			transactionDTO.setType("C");
			transactionDTO.setCustomerID(Long.toString(toAccountDTO.getCustomerID()));
			transactionService.save(transactionDTO);
			toAccountDTO.setBalance(toAccountDTO.getBalance() + amount);
			accountsService.save(toAccountDTO);
		} else {
			CustomerDTO customerDTO = customerService.findOneById(user.getId());
			List<AccountsDTO> accountsDTOS = accountsService.findAllByCustomer(customerDTO);
			if (accountsDTOS.isEmpty()) {
				throw new BadRequestAlertException("User Doesn't have any active accounts", ENTITY_NAME, "No Active Accounts");
			}
			for (AccountsDTO accountDTO : accountsDTOS) {
				if (accountDTO.getAccountType().equalsIgnoreCase("Checking")) {
					toAccountDTO = accountDTO;
				}
			}
			if (toAccountDTO == null) {
				for (AccountsDTO accountDTO : accountsDTOS) {
					if (accountDTO.getAccountType().equalsIgnoreCase("Savings")) {
						toAccountDTO = accountDTO;
					}
				}
			}
			if (toAccountDTO == null) {
				throw new BadRequestAlertException("User Doesn't have any Checking or Savings Account to Transfer Money", ENTITY_NAME, "No Active Accounts");
			}

			if (!toAccountDTO.isActivated()) {
				throw new BadRequestAlertException("Receivers Account is Not Active", ENTITY_NAME, "Account Activation");
			}
			Double balance = fromAccountDTO.getBalance();
			Double amount = transactionDTO.getAmount();
			if (amount > balance) {
				throw new BadRequestAlertException("Insufficient funds to process this transaction", ENTITY_NAME, "insufficient funds");
			}
			transactionDTO.setType("D");
			transactionDTO.setCreatedDate(LocalDate.now());
			transactionDTO.setToAccount(toAccountDTO.getAccountID().toString());
			transactionDTO.setCustomerID(Long.toString(fromAccountDTO.getCustomerID()));
			result = transactionService.save(transactionDTO);
			fromAccountDTO.setBalance(balance - amount);
			accountsService.save(fromAccountDTO);

			transactionDTO.setType("C");
			transactionDTO.setCustomerID(Long.toString(toAccountDTO.getCustomerID()));
			transactionService.save(transactionDTO);
			toAccountDTO.setBalance(toAccountDTO.getBalance() + amount);
			accountsService.save(toAccountDTO);
		}

		return ResponseEntity.created(new URI("api/transactions/payeeTransfer" + result.getTranID()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getTranID().toString()))
			.body(result);
	}

	/**
	 * {@code PUT  /transactions} : Updates an existing transactions.
	 *
	 * @param transactionDTO the transactionsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionsDTO,
	 * or with status {@code 400 (Bad Request)} if the transactionsDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the transactionsDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/transactions")
	public ResponseEntity<TransactionDTO> updateTransactions(@Valid @RequestBody TransactionDTO transactionDTO) throws URISyntaxException {
		log.debug("REST request to update Transactions : {}", transactionDTO);
		if (transactionDTO.getTranID() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		TransactionDTO result = transactionService.save(transactionDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionDTO.getTranID().toString()))
			.body(result);
	}

	/**
	 * {@code GET  /transactions} : get all the transactions.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
	 */
	@GetMapping("/transactions")
	public ResponseEntity<List<TransactionDTO>> getAllTransactions(Pageable pageable) {
		log.debug("REST request to get a page of Transactions");
		UserDTO userDTO = userService.getUserWithAuthorities()
			.map(UserDTO::new)
			.orElseThrow(() -> new TransactionResource.TransactionResourceException("User could not be found"));
		Transaction t = new Transaction();
		CustomerDTO customerDTO = customerService.findOneById(userDTO.getId());
		if (customerDTO == null) {
			throw new BadRequestAlertException("Please Register Customer details", ENTITY_NAME, "Customer Creation");
		}
		Page<TransactionDTO> page = null;
		if (userDTO.getAuthorities().contains(AuthoritiesConstants.ADMIN) || userDTO.getAuthorities().contains(AuthoritiesConstants.STAFF) || userDTO.getAuthorities().contains(AuthoritiesConstants.MANAGER)) {
			page = transactionService.findAll(pageable);
		} else {
			page = transactionService.findAllByCustomer(pageable, customerDTO);
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /transactions/:id} : get the "id" transactions.
	 *
	 * @param id the id of the transactionsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionsDTO, or with status {@code 404 (Not Found)}.
	 */

	@GetMapping("/transactions/{id}")
	public ResponseEntity<TransactionDTO> getTransactions(@PathVariable Long id) {
		log.debug("REST request to get Transactions : {}", id);
		Optional<TransactionDTO> transactionsDTO = transactionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(transactionsDTO);
	}

	/**
	 * {@code DELETE  /transactions/:id} : delete the "id" transactions.
	 *
	 * @param id the id of the transactionsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER'})")
	@DeleteMapping("/transactions/{id}")
	public ResponseEntity<Void> deleteTransactions(@PathVariable Long id) {
		log.debug("REST request to delete Transactions : {}", id);
		transactionService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
}
