package com.neptunebank.app.service.mapper;

import com.neptunebank.app.domain.Accounts;
import com.neptunebank.app.service.dto.AccountsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, BranchMapper.class, UserMapper.class})
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {

	@Mapping(source = "customer.ID", target = "customerID")
	@Mapping(source = "branch.branchID", target = "branchID")
	@Mapping(source = "branch.address", target = "branchAddress")
	@Mapping(source = "customer.user.login", target = "userLogin")
	AccountsDTO toDto(Accounts accounts);

	@Mapping(source = "customerID", target = "customer")
	@Mapping(source = "branchID", target = "branch")
		// @Mapping(source = "userLogin", target = "customer.user")
	Accounts toEntity(AccountsDTO accountsDTO);

	default Accounts fromId(Long id) {
		if (id == null) {
			return null;
		}
		Accounts accounts = new Accounts();
		accounts.setAccountID(id);
		return accounts;
	}
}
