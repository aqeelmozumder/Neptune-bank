package com.neptunebank.app.service.mapper;

import com.neptunebank.app.domain.Branch;
import com.neptunebank.app.service.dto.BranchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {

	@Mapping(target = "accounts", ignore = true)
	@Mapping(target = "removeAccounts", ignore = true)
	Branch toEntity(BranchDTO branchDTO);

	default Branch fromId(Long id) {
		if (id == null) {
			return null;
		}
		Branch branch = new Branch();
		branch.setBranchID(id);
		return branch;
	}
}
