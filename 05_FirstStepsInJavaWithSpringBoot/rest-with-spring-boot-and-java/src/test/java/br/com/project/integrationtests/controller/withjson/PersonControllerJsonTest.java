package br.com.project.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.project.configs.TestConfigs;
import br.com.project.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.project.integrationtests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonVO person;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_PROJECT)
				.setBasePath("/api/person/v1").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(person).when().post()
				.then().statusCode(200).extract().body().asString();

		PersonVO PersistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = PersistedPerson;

		assertNotNull(PersistedPerson);
		assertNotNull(PersistedPerson.getId());
		assertNotNull(PersistedPerson.getFirstName());
		assertNotNull(PersistedPerson.getLastName());
		assertNotNull(PersistedPerson.getAddress());
		assertNotNull(PersistedPerson.getGender());
		assertTrue(PersistedPerson.getId() > 0);

		assertEquals("Richard", PersistedPerson.getFirstName());
		assertEquals("Stallman", PersistedPerson.getLastName());
		assertEquals("New York City, New York, US", PersistedPerson.getAddress());
		assertEquals("Male", PersistedPerson.getGender());
	}
	
	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_PROJECTERROR)
				.setBasePath("/api/person/v1").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(person).when().post()
				.then().statusCode(403).extract().body().asString();

		assertNotNull(content);	
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_PROJECT)
				.setBasePath("/api/person/v1").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).pathParam("id", person.getId()).when().get("{id}")
				.then().statusCode(200).extract().body().asString();

		PersonVO PersistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = PersistedPerson;

		assertNotNull(PersistedPerson);
		assertNotNull(PersistedPerson.getId());
		assertNotNull(PersistedPerson.getFirstName());
		assertNotNull(PersistedPerson.getLastName());
		assertNotNull(PersistedPerson.getAddress());
		assertNotNull(PersistedPerson.getGender());
		assertTrue(PersistedPerson.getId() > 0);

		assertEquals("Richard", PersistedPerson.getFirstName());
		assertEquals("Stallman", PersistedPerson.getLastName());
		assertEquals("New York City, New York, US", PersistedPerson.getAddress());
		assertEquals("Male", PersistedPerson.getGender());
	}
	
	@Test
	@Order(3)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder().addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_PROJECTERROR)
				.setBasePath("/api/person/v1").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).pathParam("id", person.getId()).when().get("{id}")
				.then().statusCode(403).extract().body().asString();
		
		assertNotNull(content);	
		assertEquals("Invalid CORS request", content);
	}
	
	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York City, New York, US");
		person.setGender("Male");
	}

}