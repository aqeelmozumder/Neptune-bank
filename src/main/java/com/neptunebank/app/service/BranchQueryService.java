package com.neptunebank.app.service;

import com.neptunebank.app.domain.Accounts_;
import com.neptunebank.app.domain.Branch;
import com.neptunebank.app.domain.Branch_;
import com.neptunebank.app.repository.BranchRepository;
import com.neptunebank.app.service.dto.BranchCriteria;
import com.neptunebank.app.service.dto.BranchDTO;
import com.neptunebank.app.service.mapper.BranchMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link Branch} entities in the database.
 * The main input is a {@link BranchCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BranchDTO} or a {@link Page} of {@link BranchDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BranchQueryService extends QueryService<Branch> {

	private final Logger log = LoggerFactory.getLogger(BranchQueryService.class);

	private final BranchRepository branchRepository;

	private final BranchMapper branchMapper;

	public BranchQueryService(BranchRepository branchRepository, BranchMapper branchMapper) {
		this.branchRepository = branchRepository;
		this.branchMapper = branchMapper;
	}

	/**
	 * Return a {@link List} of {@link BranchDTO} which matches the criteria from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<BranchDTO> findByCriteria(BranchCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<Branch> specification = createSpecification(criteria);
		return branchMapper.toDto(branchRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link BranchDTO} which matches the criteria from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities should match.
	 * @param page The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<BranchDTO> findByCriteria(BranchCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<Branch> specification = createSpecification(criteria);
		return branchRepository.findAll(specification, page)
			.map(branchMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(BranchCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<Branch> specification = createSpecification(criteria);
		return branchRepository.count(specification);
	}

	/**
	 * Function to convert ConsumerCriteria to a {@link Specification}
	 *
	 * @param criteria The object which holds all the filters, which the entities should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<Branch> createSpecification(BranchCriteria criteria) {
		Specification<Branch> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getBranchID() != null) {
				specification = specification.and(buildSpecification(criteria.getBranchID(), Branch_.id));
			}
			if (criteria.getAddress() != null) {
				specification = specification.and(buildStringSpecification(criteria.getAddress(), Branch_.address));
			}
			if (criteria.getCity() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCity(), Branch_.city));
			}
			if (criteria.getState() != null) {
				specification = specification.and(buildStringSpecification(criteria.getState(), Branch_.city));
			}
			if (criteria.getPinCode() != null) {
				specification = specification.and(buildStringSpecification(criteria.getPinCode(), Branch_.pinCode));
			}
			if (criteria.getBranchToAccountsId() != null) {
				specification = specification.and(buildSpecification(criteria.getBranchToAccountsId(),
					root -> root.join(Branch_.ACCOUNTS, JoinType.LEFT).get(Accounts_.ID)));
			}
		}
		return specification;
	}
}
