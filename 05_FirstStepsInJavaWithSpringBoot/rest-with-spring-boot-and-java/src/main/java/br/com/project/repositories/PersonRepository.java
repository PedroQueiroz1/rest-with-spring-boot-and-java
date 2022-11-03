package br.com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.project.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
