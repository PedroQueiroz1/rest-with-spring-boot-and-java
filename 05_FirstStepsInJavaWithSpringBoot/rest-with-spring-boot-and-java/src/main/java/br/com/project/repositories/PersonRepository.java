package br.com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.project.model.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Modifying
	@Query("UPDATE Person p p.enabled = false WHERE p.id =:id")
	Person disablePerson(@Param("id") Long id);
	
}
