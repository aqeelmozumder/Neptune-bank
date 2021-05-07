package com.neptunebank.app.service.mapper;

import com.neptunebank.app.domain.Payee;
import com.neptunebank.app.service.dto.PayeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Payee} and its DTO {@link PayeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PayeeMapper extends EntityMapper<PayeeDTO, Payee> {

	@Mapping(source = "customer.ID", target = "customerID")
	PayeeDTO toDto(Payee payee);

	@Mapping(source = "customerID", target = "customer")
	Payee toEntity(PayeeDTO payeeDTO);

	default Payee fromId(Long id) {
		if (id == null) {
			return null;
		}
		Payee payee = new Payee();
		payee.setPayeeID(id);
		return payee;
	}
}
