package br.com.project.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.project.controllers.PersonController;
import br.com.project.data.vo.v1.PersonVO;
import br.com.project.exceptions.RequiredObjectIsNullException;
import br.com.project.exceptions.ResourceNotFoundException;
import br.com.project.mapper.DozerMapper;
import br.com.project.mapper.custom.PersonMapper;
import br.com.project.model.Person;
import br.com.project.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PagedResourcesAssembler<PersonVO> assembler;
	
	@Autowired
	PersonMapper mapper;

	// --------- FIND-ALL ------------
	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

		logger.info("Finding all people!");

		var personPage = personRepository.findAll(pageable);
		
		var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personVosPage.map(
				p -> p.add(
						linkTo(methodOn(PersonController.class)
								.findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(PersonController.class)
					.findAll(pageable.getPageNumber(),
							pageable.getPageSize(),
							"asc")).withSelfRel();
			
			return assembler.toModel(personVosPage, link);
	}

	// --------- FIND-BY-ID ------------
	public PersonVO findById(Long id) {

		logger.info("Finding one person!");

		Person entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
				
		return vo;
	}

	// --------- CREATE ------------
	public PersonVO create(PersonVO person) {
		
		if (person == null) throw new RequiredObjectIsNullException();
		logger.info("Creating one Person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	// --------- UPDATE ------------
	public PersonVO update(PersonVO person) {
		
		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one Person!");

		var entity = personRepository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	// --------- DISABLE PERSON ------------
	@Transactional
	public PersonVO disablePerson(Long id) {
		
		logger.info("Disabling one Person!");
		personRepository.disablePerson(id);
		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	// --------- DELETE ------------
	public void delete(Long id) {
		logger.info("Deleting one Person!");

		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(entity);
	}

	
	// trash \/
	// create V2 
/*	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one Person! (V2)");
		
		var entity = mapper.convertVOToEntity(person);
		var vo = mapper.convertEntityToVO(personRepository.save(entity));
		return vo; 
	}*/
	// - - - - - -
}
