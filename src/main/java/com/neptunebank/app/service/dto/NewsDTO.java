package com.neptunebank.app.service.dto;

import com.neptunebank.app.domain.News;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link News} entity.
 */
public class NewsDTO implements Serializable {

	private Long id;

	@NotNull
	private String title;

	@NotNull
	private LocalDate date;

	@NotNull
	private String content;

	private String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		NewsDTO newsDTO = (NewsDTO) o;
		if (newsDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), newsDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "NewsDTO{" +
			"id=" + getId() +
			", title='" + getTitle() + "'" +
			", date='" + getDate() + "'" +
			", content='" + getContent() + "'" +
			", location='" + getLocation() + "'" +
			"}";
	}
}
