package br.com.project.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="books")
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="title", nullable=false)
	@Length(min = 1, max = 80)
	@NotBlank
	private String title;

	@Column(name="theme", nullable=false)
	@Length(min = 1, max = 80)
	@NotBlank
	private String theme;

	@Column(name="author", nullable=false)
	@Length(min = 1, max = 80)
	@NotBlank
	private String author;

	@Column(name="pageQuantity", nullable=false)
	@NotNull
	@Min(value = 1)
	@Max(value = 50560)
	private short pageQuantity;
	
	@Column(name="difficultyToRead", nullable=false)
	@NotNull
	@Min(value = 0)
	@Max(value = 10)
	private byte difficultyToRead;

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

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public short getPageQuantity() {
		return pageQuantity;
	}

	public void setPageQuantity(short pageQuantity) {
		this.pageQuantity = pageQuantity;
	}

	public byte getDifficultyToRead() {
		return difficultyToRead;
	}

	public void setDifficultyToRead(byte difficultyToRead) {
		this.difficultyToRead = difficultyToRead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + difficultyToRead;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + pageQuantity;
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (difficultyToRead != other.difficultyToRead)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pageQuantity != other.pageQuantity)
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	
}
