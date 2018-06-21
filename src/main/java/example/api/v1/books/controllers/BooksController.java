package example.api.v1.books.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.api.v1.books.entities.Book;

@RestController
public class BooksController {

	// http://localhost:8080/api/v1/books
	@RequestMapping("/v1/books")
	public List<Book> listBooks() {
		List<Book> books = new ArrayList<Book>();
		books.add(new Book(1L, "Foo0", "Bar0", "FooBar0"));
		books.add(new Book(2L, "Foo1", "Bar1", "FooBar1"));
		return books;
	}
}
