package com.neptunebank.app.service.impl;

import com.neptunebank.app.domain.Branch;
import com.neptunebank.app.repository.BranchRepository;
import com.neptunebank.app.service.BranchService;
import com.neptunebank.app.service.dto.BranchDTO;
import com.neptunebank.app.service.mapper.BranchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

	private final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);

	private final BranchRepository branchRepository;

	private final BranchMapper branchMapper;

	public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper) {
		this.branchRepository = branchRepository;
		this.branchMapper = branchMapper;
	}

	/**
	 * Save a branch.
	 *
	 * @param branchDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public BranchDTO save(BranchDTO branchDTO) {
		log.debug("Request to save Branch : {}", branchDTO);
		Branch branch = branchMapper.toEntity(branchDTO);
		branch = branchRepository.save(branch);
		return branchMapper.toDto(branch);
	}

	/**
	 * Get all the branches.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<BranchDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Branches");
		return branchRepository.findAll(pageable)
			.map(branchMapper::toDto);
	}

	/**
	 * Get one branch by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<BranchDTO> findOne(Long id) {
		log.debug("Request to get Branch : {}", id);
		return branchRepository.findById(id)
			.map(branchMapper::toDto);
	}

	/**
	 * Delete the branch by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Branch : {}", id);
		branchRepository.deleteById(id);
	}
}
