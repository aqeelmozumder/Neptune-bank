package com.neptunebank.app.service.mapper;

import com.neptunebank.app.domain.Customer;
import com.neptunebank.app.service.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

	@Mapping(source = "user.id", target = "userID")
	@Mapping(source = "user.login", target = "userLogin")
	CustomerDTO toDto(Customer customer);

	@Mapping(source = "userID", target = "user")
	Customer toEntity(CustomerDTO customerDTO);

	default Customer fromId(Long id) {
		if (id == null) {
			return null;
		}
		Customer customer = new Customer();
		customer.setID(id);
		return customer;
	}
}
