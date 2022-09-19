package com.example.test.converter;

import com.example.test.domain.Book;
import com.example.test.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BookConverterTest {

    @Autowired
    private BookConverter bookConverter;

    @Test
    void givenBook_whenToDto_andParameterNull_thenThrowException() {
        assertThrows(NullPointerException.class, () -> this.bookConverter.toDto(null));
    }

    @Test
    void givenBook_whenToDto_thenReturnBookDto() {
        final Book book = createDummyBook();
        final BookDto bookDto = this.bookConverter.toDto(book);

        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        assertEquals(book.getDescription(), bookDto.getDescription());
        assertEquals(book.getTitle(), bookDto.getDescription());
    }

    @Test
    void givenBookDto_whenToEntity_andParameterNull_thenThrowException() {
        assertThrows(NullPointerException.class, () -> this.bookConverter.toDto(null));
    }

    @Test
    void givenBookDto_whenToEntity_thenReturnBookEntity() {
        final BookDto bookDto = createDummyBookDto();
        final Book book = this.bookConverter.toEntity(bookDto);

        assertNotNull(book);
        assertEquals(bookDto.getId(), book.getId());
        assertEquals(bookDto.getAuthor(), book.getAuthor());
        assertEquals(bookDto.getDescription(), book.getDescription());
        assertEquals(bookDto.getTitle(), book.getDescription());
    }

    private static Book createDummyBook() {
        return Book.builder()
                .id(1L)
                .title("Test")
                .description("Test")
                .author("Test")
                .isbn("Test")
                .build();
    }

    private static BookDto createDummyBookDto() {
        return BookDto.builder()
                .id(1L)
                .title("Test")
                .description("Test")
                .author("Test")
                .isbn("Test")
                .build();
    }
}
