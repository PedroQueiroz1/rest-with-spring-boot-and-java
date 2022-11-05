package br.com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.services.PersonServices;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonServices service;
//	private PersonServices service = new PersonServices();

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonVO> findAll() {
		return service.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable(value = "id") Long id) {
		service.delete(id);
	}
}