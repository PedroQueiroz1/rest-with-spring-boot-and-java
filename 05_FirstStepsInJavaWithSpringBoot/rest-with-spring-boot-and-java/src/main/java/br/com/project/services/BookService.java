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

import br.com.project.controllers.BookController;
import br.com.project.data.vo.v1.BookVO;
import br.com.project.exceptions.RequiredObjectIsNullException;
import br.com.project.exceptions.ResourceNotFoundException;
import br.com.project.mapper.DozerMapper;
import br.com.project.model.Book;
import br.com.project.repositories.BookRepository;

@Service
public class BookService {
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PagedResourcesAssembler<BookVO> assembler;
	
	// --------- FIND-ALL ------------
	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

		logger.info("Finding all books!");

		var bookPage = bookRepository.findAll(pageable);
		
		var bookVosPage = bookPage.map(b -> DozerMapper.parseObject(b, BookVO.class));
		bookVosPage.map(b -> b.add(linkTo(methodOn(BookController.class)
				.findById(b.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(BookController.class)
				.findAll(pageable.getPageNumber(), 
						pageable.getPageSize(), 
						"asc")).withSelfRel();
				
		
			return assembler.toModel(bookVosPage, link);
	}
	
	// --------- FIND-BY-ID ------------
	public BookVO findById(Long id) {
		
		logger.info("Finding one book!");
		
		var entity = bookRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}

	// --------- CREATE ------------
	public BookVO create(BookVO book) {

		if (book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one book!");
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo =  DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	// --------- UPDATE ------------
	public BookVO update(BookVO book) {

		if (book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one book!");
		
		var entity = bookRepository.findById(book.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo =  DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	// --------- DELETE ------------
	public void delete(Long id) {
		
		logger.info("Deleting one book!");
		
		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		bookRepository.delete(entity);
	}
}
