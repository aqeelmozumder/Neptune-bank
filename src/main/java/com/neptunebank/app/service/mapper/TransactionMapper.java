package com.neptunebank.app.service.mapper;

import com.neptunebank.app.domain.Transaction;
import com.neptunebank.app.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountsMapper.class, CustomerMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

	@Mapping(source = "fromAccount.accountID", target = "fromAccount")
	@Mapping(source = "toAccount.accountID", target = "toAccount")
	@Mapping(source = "customer.ID", target = "customerID")
	TransactionDTO toDto(Transaction transaction);

	@Mapping(source = "fromAccount", target = "fromAccount")
	@Mapping(source = "toAccount", target = "toAccount")
	@Mapping(source = "customerID", target = "customer")
	Transaction toEntity(TransactionDTO transactionDTO);

	default Transaction fromId(Long id) {
		if (id == null) {
			return null;
		}
		Transaction transaction = new Transaction();
		transaction.setTranID(id);
		return transaction;
	}
}
