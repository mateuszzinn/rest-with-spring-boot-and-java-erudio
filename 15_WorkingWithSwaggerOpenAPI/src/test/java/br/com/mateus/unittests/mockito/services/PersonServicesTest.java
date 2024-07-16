package br.com.mateus.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import br.com.mateus.data.vo.v1.PersonVO;
import br.com.mateus.exception.ResourceNotFoundException;
import br.com.mateus.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mateus.domain.Person;
import br.com.mateus.repositories.PersonRepository;
import br.com.mateus.services.PersonServices;
import br.com.mateus.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

	MockPerson input;
	private PersonServices service;
	private PersonRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
		repository = mock(PersonRepository.class);
		service = new PersonServices(repository);
	}
	
	@Test
	void testFindById() {
		Person entity = new Person();
		var id = 1L;
		entity.setId(id);

		when(repository.findById(any())).thenReturn(Optional.of(entity));
		
		var result = assertDoesNotThrow(() -> service.findById(id));
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println((result.toString()));
		assertTrue(result.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
	}
	
	@Test
	void testFindAll() {
		List<Person> entityList = input.mockEntityList();
		when(repository.findAll()).thenReturn(entityList);
		List<PersonVO> result = assertDoesNotThrow(() -> service.findAll());
		assertFalse(result.isEmpty());
	}

	@Test
	void testCreate() {
		PersonVO personVO = new PersonVO();
		Person personDB = new Person();
		personVO.setKey(1L);
		personDB.setId(1L);
		when(repository.save(personDB)).thenReturn(personDB);
		var result = assertDoesNotThrow(() -> service.create(personVO));
		assertEquals(result.getKey(), personVO.getKey());
	}

	@Test
	void testUpdate() {
		PersonVO personVO = new PersonVO();
		Person personDB = new Person();
		personVO.setKey(1L);
		personDB.setId(1L);
		when(repository.findById(personDB.getId())).thenReturn(Optional.of(personDB));
		when(repository.save(personDB)).thenReturn(personDB);
		var result = assertDoesNotThrow(() -> service.update(personVO));
		assertEquals(result.getKey(), personVO.getKey());
	}

	@Test
	void testDelete() {
		Person person = new Person();
		Long id = 1L;
		person.setId(id);
		when(repository.findById(id)).thenReturn(Optional.of(person));
		doNothing().when(repository).delete(person);
		assertDoesNotThrow(() -> service.delete(id));
	}
}
