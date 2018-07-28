package example.api.v1.books;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/api/v1/books")
public class BookRestController {

  @Autowired
  private BookService bookService;

  @PostMapping
  public ResponseEntity<ServiceResponse<Book>> createBook(@RequestBody @Valid Book book) {
    book = bookService.createBook(book);

    HttpHeaders headers = new HttpHeaders();

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(book.getId()).toUri();
    headers.setLocation(location);

    ServiceMessage message = new ServiceMessage("Book successfuly created!");

    return new ResponseEntity<>(new ServiceResponse<>(book, message), headers, HttpStatus.CREATED);
  }

  // http://localhost:8080/api/v1/books/1
  @GetMapping("/{id}")
  public ResponseEntity<ServiceResponse<Book>> getBook(@PathVariable Long id) {
    return ResponseEntity.ok(new ServiceResponse<>(bookService.getBook(id)));
  }

  // http://localhost:8080/api/v1/books
  @GetMapping
  public ServiceResponse<List<Book>> listBooks() {
    return new ServiceResponse<>(bookService.getAllBooks());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ServiceResponse<Book>> updateBook(@PathVariable Long id,
      @Valid @RequestBody Book book) {
    if (!book.getId().equals(id)) {
      return new ResponseEntity<ServiceResponse<Book>>(
          new ServiceResponse<>(null,
              new ServiceMessage(MessageType.ERROR,
                  "URL ID: '" + id + "'doesn't match book ID: '" + book.getId() + "'.")),
          HttpStatus.BAD_REQUEST);
    }

    ServiceMessage message = new ServiceMessage("Book successfuly updated!");

    return new ResponseEntity<ServiceResponse<Book>>(
        new ServiceResponse<>(bookService.updateBook(book), message), HttpStatus.OK);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ServiceResponse<Void>> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    ServiceMessage message = new ServiceMessage("Book sucessfully deleted!");
    return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
  }

}
