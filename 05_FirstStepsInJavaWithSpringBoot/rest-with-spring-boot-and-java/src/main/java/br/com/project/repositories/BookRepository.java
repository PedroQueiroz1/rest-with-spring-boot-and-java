package br.com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.project.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
