package br.com.project.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Service;

import br.com.project.controllers.BookController;
import br.com.project.data.vo.v1.BookVO;
import br.com.project.exceptions.RequiredObjectIsNullException;
import br.com.project.exceptions.ResourceNotFoundException;
import br.com.project.mapper.DozerMapper;
import br.com.project.mapper.custom.BookMapper;
import br.com.project.model.Book;
import br.com.project.repositories.BookRepository;

@Service
public class BookService {

	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookMapper mapper;
	
	
	// --------- FIND-ALL ------------
	public List<BookVO> findAll(){
		logger.info("Finding all people!");
		
		List<BookVO> books = DozerMapper.parseListObject(bookRepository.findAll(), BookVO.class);
		books.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		
		return books;
	}
	
	// --------- FIND-BY-ID ------------
	public BookVO findById(Long id) {
		
		logger.info("Finding one book!");
		
		Book entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	// --------- CREATE ------------
	public BookVO create(BookVO book) {
		
		if(book == null) throw new RequiredObjectIsNullException();
		logger.info("Creating a Book!");
		
		var entity = DozerMapper.parseObject(book, Book.class);
		BookVO vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	// --------- UPDATE ------------
	public BookVO update(BookVO book) {
		logger.info("Updating one Book!");
		
		var entity = bookRepository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setTitle(book.getTitle());
		entity.setTheme(book.getTheme());
		entity.setAuthor(book.getAuthor());
		entity.setPageQuantity(book.getPageQuantity());
		entity.setDifficultyToRead(book.getDifficultyToRead());
		
		BookVO vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
				
		return vo;
	}
	
	// --------- DELETE ------------
	public void delete(Long id) {
		logger.info("Deleting one Book!");
		
		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		bookRepository.delete(entity);
	}
}
