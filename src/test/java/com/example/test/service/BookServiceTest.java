package com.example.test.service;

import com.example.test.converter.BookConverter;
import com.example.test.domain.Book;
import com.example.test.dto.BookDto;
import com.example.test.exception.EntityNotFoundException;
import com.example.test.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    @BeforeEach
    public void beforeTest() {
        this.bookRepository.deleteAll();
    }

    @Test
    void givenBook_whenCreate_andParameterNull_thenThrowException() {
        final BookDto BookDto = new BookDto();
        assertThrows(NullPointerException.class,
                () -> this.bookService.create(null, this.bookConverter));
        assertThrows(NullPointerException.class,
                () -> this.bookService.create(BookDto, null));
    }

    @Test
    void givenBook_whenCreate_andFoundAfterSave_thenOk() {
        BookDto actualBook = createDummyBookDto();
        actualBook = this.bookService.create(actualBook, this.bookConverter);
        final Long id = actualBook.getId();

        final Optional<Book> expectedBook = this.bookRepository.findById(id);

        assertTrue(expectedBook.isPresent());
        assertEquals(expectedBook.get().getId(), actualBook.getId());
        assertEquals(expectedBook.get().getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.get().getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.get().getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.get().getIsbn(), actualBook.getIsbn());
    }

    @Test
    void givenBook_whenUpdate_andParameterNull_thenThrowException() {
        final BookDto BookDto = new BookDto();
        final Long id = 1L;
        assertThrows(NullPointerException.class,
                () -> this.bookService.update(null, BookDto, this.bookConverter));
        assertThrows(NullPointerException.class,
                () -> this.bookService.update(id, null, this.bookConverter));
        assertThrows(NullPointerException.class,
                () -> this.bookService.update(id, BookDto, null));
    }

    @Test
    void givenBook_whenUpdate_andNotFoundById_thenThrowException() {
        final Long id = 1L;
        final BookDto BookDto = createDummyBookDto();
        assertThrows(EntityNotFoundException.class,
                () -> this.bookService.update(id, BookDto, this.bookConverter));
    }

    @Test
    void givenBook_whenUpdate_andFoundAfterSave_andUpdated_thenOk() {
        final Book book = createDummyBook("Test");
        this.bookRepository.save(book);
        final Long bookId = book.getId();
        final BookDto actualBook = this.bookConverter.toDto(book);

        actualBook.setTitle("UpdatedTest");
        actualBook.setAuthor("UpdatedTest");
        actualBook.setDescription("UpdatedTest");
        actualBook.setIsbn("UpdatedTest");
        this.bookService.update(bookId, actualBook, this.bookConverter);

        final Optional<Book> expectedBook = this.bookRepository.findById(bookId);

        assertTrue(expectedBook.isPresent());
        assertEquals(expectedBook.get().getId(), actualBook.getId());
        assertEquals(expectedBook.get().getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.get().getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.get().getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.get().getIsbn(), actualBook.getIsbn());
    }


    @Test
    void givenBookId_whenDeleteById_andParameterNull_thenThrowException() {
        assertThrows(NullPointerException.class, () -> this.bookService.deleteById(null));
    }

    @Test
    void givenBookId_whenDeleteById_andNotFound_thenThrowException() {
        final Long id = 1L;
        assertThrows(EntityNotFoundException.class, () -> this.bookService.deleteById(id));
    }

    @Test
    void givenBookId_whenDeleteById_andAfterDeleteNotFound_thenOk() {
        final Book expectedBook = createDummyBook("Test");
        this.bookRepository.save(expectedBook);
        final Long bookId = expectedBook.getId();
        this.bookService.deleteById(bookId);

        final Optional<Book> actualBook = this.bookRepository.findById(bookId);
        assertTrue(actualBook.isEmpty());
    }

    @Test
    void givenBookId_whenGetById_andParameterNull_thenThrowException() {
        final Long id = 1L;
        assertThrows(NullPointerException.class,
                () -> this.bookService.getById(null, this.bookConverter));
        assertThrows(NullPointerException.class,
                () -> this.bookService.getById(id, null));
    }

    @Test
    void givenBookId_whenGetById_andNotFound_thenThrowException() {
        final Long id = 1L;
        assertThrows(EntityNotFoundException.class,
                () -> this.bookService.getById(id, this.bookConverter));
    }

    @Test
    void givenBookId_whenGetById_andFound_thenOk() {
        final Book expectedBook = createDummyBook("Test");
        this.bookRepository.save(expectedBook);
        final Long bookId = expectedBook.getId();

        final BookDto actualBook =
                this.bookService.getById(bookId, this.bookConverter);

        assertNotNull(actualBook);
        assertEquals(expectedBook.getId(), actualBook.getId());
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
    }

    @Test
    void givenNone_whenGetBooks_andNothingExists_andFoundEmptyList_thenOk() {
        final List<BookDto> books = this.bookService
                .getBooks(this.bookConverter);
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void givenNone_whenGetBooks_andExists_andFoundNonEmptyList_thenOk() {
        this.bookRepository.save(createDummyBook("Test"));
        this.bookRepository.save(createDummyBook("Test2"));

        final List<BookDto> books = this.bookService
                .getBooks(this.bookConverter);
        assertNotNull(books);
        assertEquals(2, books.size());
    }

    private static BookDto createDummyBookDto() {
        return BookDto.builder()
                .title("book")
                .description("book")
                .author("book")
                .isbn("book")
                .build();
    }

    private static Book createDummyBook(final String book) {
        return Book.builder()
                .title(book)
                .description(book)
                .author(book)
                .isbn(book)
                .build();
    }
}
