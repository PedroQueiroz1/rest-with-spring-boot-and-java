package br.com.project.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.project.data.vo.v1.PersonVO;
import br.com.project.exceptions.ResourceNotFoundException;
import br.com.project.mapper.DozerMapper;
import br.com.project.mapper.custom.PersonMapper;
import br.com.project.model.Person;
import br.com.project.repositories.PersonRepository;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {

		logger.info("Finding all people!");

		return DozerMapper.parseListObject(personRepository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one person!");

		Person entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(PersonVO person) {
		logger.info("Creating one Person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		return vo;
	}
	
	// create V2 
/*	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one Person! (V2)");
		
		var entity = mapper.convertVOToEntity(person);
		var vo = mapper.convertEntityToVO(personRepository.save(entity));
		return vo; 
	}*/
	// - - - - - -

	public PersonVO update(PersonVO person) {
		logger.info("Updating one Person!");

		var entity = personRepository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one Person!");

		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(entity);
	}

}
