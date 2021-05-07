package com.neptunebank.app.service.mapper;

import com.neptunebank.app.domain.News;
import com.neptunebank.app.service.dto.NewsDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link News} and its DTO {@link NewsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsMapper extends EntityMapper<NewsDTO, News> {

	default News fromId(Long id) {
		if (id == null) {
			return null;
		}
		News news = new News();
		news.setId(id);
		return news;
	}
}
