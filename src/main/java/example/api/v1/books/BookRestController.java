package example.api.v1.books;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import example.api.infra.MessageType;
import example.api.infra.ServiceMessage;
import example.api.infra.ServiceResponse;

@RestController
@RequestMapping("/v1/books")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public ResponseEntity<ServiceResponse<Book>> createBook(@RequestBody Book book) {
		book = bookService.createBook(book);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId())
				.toUri();
		headers.setLocation(location);

		ServiceMessage message = new ServiceMessage("Livro cadastrado com sucesso!");

		return new ResponseEntity<ServiceResponse<Book>>(new ServiceResponse<Book>(book, message), headers,
				HttpStatus.CREATED);
	}

	// http://localhost:8080/api/v1/books/1
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse<Book>> getBook(@PathVariable Long id) {
		Optional<Book> book = bookService.getBook(id);

		if (book.isPresent()) {
			return ResponseEntity.ok(new ServiceResponse<Book>(book.get()));
		} else {
			return new ResponseEntity<ServiceResponse<Book>>(
					new ServiceResponse<Book>(null, new ServiceMessage(MessageType.ERROR, "Livro não encontrado")),
					HttpStatus.NOT_FOUND);
		}
	}

	// http://localhost:8080/api/v1/books
	@GetMapping
	public ServiceResponse<List<Book>> listBooks() {
		return new ServiceResponse<List<Book>>(bookService.getAllBooks());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ServiceResponse<Book>> updateBook(@PathVariable Long id, @RequestBody Book book) {

		// Valida se o ID passado é igual ao do Livro informado
		if (book.getId() != id) {
			return new ResponseEntity<ServiceResponse<Book>>(new ServiceResponse<Book>(null,
					new ServiceMessage(MessageType.ERROR, "ID da URL não corresponde ao ID do livro informado.")),
					HttpStatus.BAD_REQUEST);
		}

		Optional<Book> currentBook = bookService.getBook(id);

		if (!currentBook.isPresent()) {
			return new ResponseEntity<ServiceResponse<Book>>(
					new ServiceResponse<Book>(null, new ServiceMessage(MessageType.ERROR, "Livro não encontrado")),
					HttpStatus.NOT_FOUND);
		}

		ServiceMessage message = new ServiceMessage("Livro alterado com sucesso!");

		bookService.updateBook(book);

		return new ResponseEntity<ServiceResponse<Book>>(
				new ServiceResponse<Book>(bookService.getBook(id).get(), message), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceResponse<Void>> deleteBook(@PathVariable Long id) {

		bookService.deleteBook(id);

		ServiceMessage message = new ServiceMessage("Livro excluído com sucesso!");

		return new ResponseEntity<ServiceResponse<Void>>(new ServiceResponse<Void>(null, message), HttpStatus.OK);
	}

}
