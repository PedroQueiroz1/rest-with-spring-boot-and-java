package br.com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.data.vo.v1.BookVO;
import br.com.project.services.BookService;
import br.com.project.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Endpoints for Managins Books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	
	//GET MAPPING
	@GetMapping
	@Operation(summary = "Finds all books", description = "Finds all books",
	tags= {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
					)
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}
)
	public List<BookVO> findAll(){
		return bookService.findAll();
	}
	
	
	//GET MAPPING
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a book", description = "Finds a book",
	tags = {"Books"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = BookVO.class))
		),
		@ApiResponse(description = "No Content"),
		@ApiResponse(description = "Bad Request"),
		@ApiResponse(description = "Unauthorized"),
		@ApiResponse(description = "Not Found"),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}
)
	public BookVO findById(@PathVariable(value = "id") Long id) {
		return bookService.findById(id);
	}
	
	
	// POST MAPPING
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a book",
	description = "Adds a new book by passing a JSON, XML or YML representation of the person",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse
	})
	public BookVO create(@RequestBody BookVO book) {
	return bookService.create(book);
	}
	
	@PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary="Updates a book",
	description = "Updates a book by passing a Json, XML, YML representation of the person",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}
)
	public BookVO update(@RequestBody BookVO book) {
		return bookService.update(book);
	}
	
}
