package com.neptunebank.app.service;

import com.neptunebank.app.domain.Branch;
import com.neptunebank.app.service.dto.BranchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Branch}.
 */
public interface BranchService {

	/**
	 * Save a branch.
	 *
	 * @param branchDTO the entity to save.
	 * @return the persisted entity.
	 */
	BranchDTO save(BranchDTO branchDTO);

	/**
	 * Get all the branches.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BranchDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" branch.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<BranchDTO> findOne(Long id);

	/**
	 * Delete the "id" branch.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
