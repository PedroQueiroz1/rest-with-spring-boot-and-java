package br.com.project.unittests.mockito.services;


import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.project.data.vo.v1.PersonVO;
import br.com.project.exceptions.RequiredObjectIsNullException;
import br.com.project.model.Person;
import br.com.project.repositories.PersonRepository;
import br.com.project.services.PersonService;
import br.com.project.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository personRepository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1",result.getAddress());
		assertEquals("First Name Test1",result.getFirstName());
		assertEquals("Last Name Test1",result.getLastName());
		assertEquals("Female",result.getGender());
	}
	
	
	// Not functioning correctly... :(
	// 'save' method is getting an error
	@Test
	void testCreate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
	
		when(personRepository.save(entity)).thenReturn(persisted);
//		doReturn(persisted).when(repository.save(entity));
		
		var result = service.create(vo);
	
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1",result.getAddress());
		assertEquals("First Name Test1",result.getFirstName());
		assertEquals("Last Name Test1",result.getLastName());
		assertEquals("Female",result.getGender());
	}

	@Test
	void testCreateWithNulPerson() {
	
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->{
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	void testUpdate() {	
		Person entity = input.mockEntity(1);	
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
		when(personRepository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
	
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1",result.getAddress());
		assertEquals("First Name Test1",result.getFirstName());
		assertEquals("Last Name Test1",result.getLastName());
		assertEquals("Female",result.getGender());
	}
	
	
	@Test
	void testUpdateWithNullPerson() {			
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->{
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}


	@Test
	void testDelete() {
	Person entity = input.mockEntity(1);
	entity.setId(1L);
	
	when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
	
	service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();
		
		when(personRepository.findAll()).thenReturn(list);
		
		var people = service.findAll();
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		// index: 1
		var personOne = people.get(1);
		
		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		
		assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1",personOne.getAddress());
		assertEquals("First Name Test1",personOne.getFirstName());
		assertEquals("Last Name Test1",personOne.getLastName());
		assertEquals("Female",personOne.getGender());
		
		// index: 4
		var personFour = people.get(4);
		
		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());
		
		assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
		assertEquals("Address Test4",personFour.getAddress());
		assertEquals("First Name Test4",personFour.getFirstName());
		assertEquals("Last Name Test4",personFour.getLastName());
		assertEquals("Male",personFour.getGender());
		
		// index: 7
		var personSeven = people.get(7);
		
		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());
		
		assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
		assertEquals("Address Test7",personSeven.getAddress());
		assertEquals("First Name Test7",personSeven.getFirstName());
		assertEquals("Last Name Test7",personSeven.getLastName());
		assertEquals("Female",personSeven.getGender());
	}
	
	

}
