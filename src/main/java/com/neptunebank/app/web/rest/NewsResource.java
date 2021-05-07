package com.neptunebank.app.web.rest;

import com.neptunebank.app.domain.News;
import com.neptunebank.app.service.NewsService;
import com.neptunebank.app.service.dto.NewsDTO;
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
 * REST controller for managing {@link News}.
 */
@RestController
@RequestMapping("/api")
public class NewsResource {

	private final Logger log = LoggerFactory.getLogger(NewsResource.class);

	private static final String ENTITY_NAME = "news";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final NewsService newsService;

	public NewsResource(NewsService newsService) {
		this.newsService = newsService;
	}

	/**
	 * {@code POST  /news} : Create a new news.
	 *
	 * @param newsDTO the newsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsDTO, or with status {@code 400 (Bad Request)} if the news has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/news")
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER'})")
	public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody NewsDTO newsDTO) throws URISyntaxException {
		log.debug("REST request to save News : {}", newsDTO);
		if (newsDTO.getId() != null) {
			throw new BadRequestAlertException("A new news cannot already have an ID", ENTITY_NAME, "idexists");
		}
		NewsDTO result = newsService.save(newsDTO);
		return ResponseEntity.created(new URI("/api/news/" + result.getId()))
			.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
			.body(result);
	}

	/**
	 * {@code PUT  /news} : Updates an existing news.
	 *
	 * @param newsDTO the newsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsDTO,
	 * or with status {@code 400 (Bad Request)} if the newsDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the newsDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER'})")
	@PutMapping("/news")
	public ResponseEntity<NewsDTO> updateNews(@Valid @RequestBody NewsDTO newsDTO) throws URISyntaxException {
		log.debug("REST request to update News : {}", newsDTO);
		if (newsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		NewsDTO result = newsService.save(newsDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, newsDTO.getId().toString()))
			.body(result);
	}

	/**
	 * {@code GET  /news} : get all the news.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of news in body.
	 */
	@GetMapping("/news")
	public ResponseEntity<List<NewsDTO>> getAllNews(Pageable pageable) {
		log.debug("REST request to get a page of News");
		Page<NewsDTO> page = newsService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /news/:id} : get the "id" news.
	 *
	 * @param id the id of the newsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/news/{id}")
	public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
		log.debug("REST request to get News : {}", id);
		Optional<NewsDTO> newsDTO = newsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(newsDTO);
	}

	/**
	 * {@code DELETE  /news/:id} : delete the "id" news.
	 *
	 * @param id the id of the newsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PreAuthorize("hasAnyAuthority({'ROLE_MANAGER'})")
	@DeleteMapping("/news/{id}")
	public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
		log.debug("REST request to delete News : {}", id);
		newsService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
}
