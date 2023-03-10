package br.com.project.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.project.integrationtests.vo.PersonVO;

public class PersonEmbeddedVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("personVOList")
	private List<PersonVO> embedded;

	
	public PersonEmbeddedVO() {
	}


	public PersonEmbeddedVO(List<PersonVO> embedded) {
		this.embedded = embedded;
	}

	
	public List<PersonVO> getEmbedded() {
		return embedded;
	}

	public void setEmbedded(List<PersonVO> embedded) {
		this.embedded = embedded;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((embedded == null) ? 0 : embedded.hashCode());
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
		PersonEmbeddedVO other = (PersonEmbeddedVO) obj;
		if (embedded == null) {
			if (other.embedded != null)
				return false;
		} else if (!embedded.equals(other.embedded))
			return false;
		return true;
	}
	
	
	
}
