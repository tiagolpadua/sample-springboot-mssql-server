package example.api.v1.books;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import example.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookRestControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private Book book;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();

		bookRepository.deleteAllInBatch();

		book = bookRepository.save(new Book(null, "Alice's Adventures in Wonderland", "Charles Lutwidge Dodgson",
				"Tells of a girl named Alice falling through a rabbit hole into a fantasy world populated by peculiar, anthropomorphic creatures"));
	}
	
//	@Test
//	public void createABook() throws Exception {
//		mockMvc.perform(get("/v1/books/" + book.getId()))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(contentType))
//		.andExpect(jsonPath("$.data.id", greaterThan(0)))
//		.andExpect(jsonPath("$.data.title", is(book.getTitle())))
//		.andExpect(jsonPath("$.data.author", is(book.getAuthor())))
//		.andExpect(jsonPath("$.data.description", is(book.getDescription())));
//	}

	@Test
	public void getAllBooks() throws Exception {
		mockMvc.perform(get("/v1/books"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.data", hasSize(1)))
			.andExpect(jsonPath("$.data[0].id", greaterThan(0)))
			.andExpect(jsonPath("$.data[0].title", is(book.getTitle())))
			.andExpect(jsonPath("$.data[0].author", is(book.getAuthor())))
			.andExpect(jsonPath("$.data[0].description", is(book.getDescription())));
	}
	
	@Test
	public void getAnExistingBook() throws Exception {
		mockMvc.perform(get("/v1/books/" + book.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.data.id", greaterThan(0)))
			.andExpect(jsonPath("$.data.title", is(book.getTitle())))
			.andExpect(jsonPath("$.data.author", is(book.getAuthor())))
			.andExpect(jsonPath("$.data.description", is(book.getDescription())));
	}
	
	public void getAnInexistingBook() throws Exception {
		mockMvc.perform(get("/v1/books/" + (book.getId() + 1)))
			.andExpect(status().isNotFound());
	}

//	@Test
//	public void bookNotFound() throws Exception {
//		mockMvc.perform(
//				post("/george/bookmarks/").content(this.json(new Bookmark(null, null, null))).contentType(contentType))
//				.andExpect(status().isNotFound());
//	}
//
//	@Test
//	public void readSingleBookmark() throws Exception {
//		mockMvc.perform(get("/bookmarks/" + userName + "/" + this.bookmarkList.get(0).getId()))
//				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
//				.andExpect(jsonPath("$.id", is(this.bookmarkList.get(0).getId().intValue())))
//				.andExpect(jsonPath("$.uri", is("http://bookmark.com/1/" + userName)))
//				.andExpect(jsonPath("$.description", is("A description")));
//	}
//
//	@Test
//	public void readBookmarks() throws Exception {
//		mockMvc.perform(get("/bookmarks/" + userName)).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(2)))
//				.andExpect(jsonPath("$[0].id", is(this.bookmarkList.get(0).getId().intValue())))
//				.andExpect(jsonPath("$[0].uri", is("http://bookmark.com/1/" + userName)))
//				.andExpect(jsonPath("$[0].description", is("A description")))
//				.andExpect(jsonPath("$[1].id", is(this.bookmarkList.get(1).getId().intValue())))
//				.andExpect(jsonPath("$[1].uri", is("http://bookmark.com/2/" + userName)))
//				.andExpect(jsonPath("$[1].description", is("A description")));
//	}
//
//	@Test
//	public void createBookmark() throws Exception {
//		String bookmarkJson = json(new Bookmark(this.account, "http://spring.io",
//				"a bookmark to the best resource for Spring news and information"));
//
//		this.mockMvc.perform(post("/bookmarks/" + userName).contentType(contentType).content(bookmarkJson))
//				.andExpect(status().isCreated());
//	}
//
//	protected String json(Object o) throws IOException {
//		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
//		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
//		return mockHttpOutputMessage.getBodyAsString();
//	}

}
