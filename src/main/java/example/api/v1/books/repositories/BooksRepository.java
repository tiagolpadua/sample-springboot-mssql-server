package example.api.v1.books.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import example.api.v1.books.entities.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {

	List<Book> findByAuthor(String author);

	List<Book> findByTitle(String title);
}
