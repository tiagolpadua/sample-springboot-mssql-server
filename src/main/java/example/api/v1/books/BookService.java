package example.api.v1.books;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	private static final Logger log = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookRepository bookRepository;

	public BookService() {
	}

	public Book createBook(Book book) {
		log.debug("Creating book: " + book.toString());
		return bookRepository.save(book);
	}

	public Optional<Book> getBook(Long id) {
		log.debug("Getting book: " + id);
		return bookRepository.findById(id);
	}

	public void updateBook(Book book) {
		log.debug("Updateing book: " + book.toString());
		bookRepository.save(book);
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	// http://goo.gl/7fxvVf
	// public Page<Book> getAllBooks(Integer page, Integer size) {
	// Page pageOfBooks = BookRepository.findAll(new PageRequest(page, size));
	// // example of adding to the /metrics
	// if (size > 50) {
	// counterService.increment("Khoubyari.BookService.getAll.largePayload");
	// }
	// return pageOfBooks;
	// }

}
