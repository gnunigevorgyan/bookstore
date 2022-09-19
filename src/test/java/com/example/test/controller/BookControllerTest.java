package com.example.test.controller;

import com.example.test.converter.BookConverter;
import com.example.test.domain.Book;
import com.example.test.dto.BookDto;
import com.example.test.exception.EntityNotFoundException;
import com.example.test.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    private final static String BASE_PATH = "/api/v1/books";
    private final static String ITEM_PATH = "/api/v1/books/%s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookConverter bookConverter;

    @MockBean
    private BookService bookService;

    @Test
    void givenBookDto_whenCreateBook_thenCreated() throws Exception {
        final BookDto bookDto = createDummyBookDto();
        doReturn(bookDto).when(this.bookService).create(bookDto, this.bookConverter);
        this.mockMvc.perform(post(BASE_PATH)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(this.bookService, times(1))
                .create(bookDto, this.bookConverter);
    }

    @Test
    void givenBookDto_whenCreateBook_andWrongData_thenBadRequest() throws Exception {
        BookDto bookDto = createDummyBookDto();
        bookDto.setTitle(null);
        this.mockMvc.perform(post(BASE_PATH)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(this.bookService, times(0))
                .create(bookDto, this.bookConverter);
    }

    @Test
    void givenBookIdAndBookDto_whenUpdateBook_andNotFound_thenNotFound() throws Exception {
        final BookDto bookDto = createDummyBookDto();
        doThrow(new EntityNotFoundException())
                .when(this.bookService).update(bookDto.getId(), bookDto, this.bookConverter);
        this.mockMvc.perform(put(String.format(ITEM_PATH, bookDto.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isNotFound());
        verify(this.bookService, times(1))
                .update(bookDto.getId(), bookDto, this.bookConverter);
    }

    @Test
    void givenBookIdAndBookDto_whenUpdateBook_andWrongData_thenBadRequest() throws Exception {
        final BookDto bookDto = createDummyBookDto();
        bookDto.setTitle(null);
        this.mockMvc.perform(put(String.format(ITEM_PATH, bookDto.getId()))
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(this.bookService, times(0))
                .update(bookDto.getId(), bookDto, this.bookConverter);
    }

    @Test
    void givenBookIdAndBookDto_whenUpdateBook_andUpdated_thenOk() throws Exception {
        final BookDto bookDto = createDummyBookDto();
        doReturn(bookDto).when(this.bookService)
                .update(bookDto.getId(), bookDto, this.bookConverter);
        this.mockMvc.perform(put(String.format(ITEM_PATH, bookDto.getId()))
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(this.bookService, times(1))
                .update(bookDto.getId(), bookDto, this.bookConverter);
    }

    @Test
    void givenNone_whenGetBooks_andEmpty_thenOk_andBodyEmptyList() throws Exception {
        doReturn(List.of()).when(bookService).getBooks(bookConverter);
        final String strResponseBody = this.mockMvc.perform(get(BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(this.bookService, times(1))
                .getBooks(this.bookConverter);

        final List<BookDto> books = this.objectMapper.readValue(strResponseBody, new TypeReference<>() {
        });

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void givenNone_whenGetBooks_thenOk_andBodyNotEmpty() throws Exception {
        final List<BookDto> expectedBooks =
                new ArrayList<>(List.of(createDummyBookDto(), createDummyBookDto()));
        doReturn(expectedBooks).when(this.bookService)
                .getBooks(this.bookConverter);
        final String strResponseBody = this.mockMvc.perform(get(BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(this.bookService, times(1))
                .getBooks(this.bookConverter);

        final List<BookDto> actualBooks = this.objectMapper
                .readValue(strResponseBody, new TypeReference<>() {
                });

        assertNotNull(actualBooks);
        assertEquals(2, actualBooks.size());

        actualBooks.sort(Comparator.comparing(BookDto::getTitle));
        expectedBooks.sort(Comparator.comparing(BookDto::getTitle));

        assertEquals(actualBooks.get(0), expectedBooks.get(0));
        assertEquals(actualBooks.get(1), expectedBooks.get(1));
    }

    @Test
    void givenBookId_whenGetBook_andNotFound_thenNotfound() throws Exception {
        final Long id = 1L;
        doThrow(new EntityNotFoundException())
                .when(this.bookService).getById(id, this.bookConverter);
        this.mockMvc.perform(get(String.format(ITEM_PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(this.bookService, times(1))
                .getById(id, this.bookConverter);
    }

    @Test
    void givenBookId_whenGetBook_andFound_thenOk() throws Exception {
        final BookDto expectedBook = createDummyBookDto();
        doReturn(expectedBook).when(this.bookService)
                .getById(expectedBook.getId(), this.bookConverter);
        final String strResponseBody = this.mockMvc.perform(get(String.format(ITEM_PATH, expectedBook.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(this.bookService, times(1))
                .getById(expectedBook.getId(), this.bookConverter);
        final BookDto actualBook = this.objectMapper.readValue(strResponseBody, new TypeReference<>() {
        });
        assertNotNull(actualBook);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    void givenBookId_whenDeleteBook_andNotFound_thenNotfound() throws Exception {
        final Long id = 1L;
        doThrow(new EntityNotFoundException())
                .when(this.bookService).deleteById(id);
        this.mockMvc.perform(delete(String.format(ITEM_PATH, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(this.bookService, times(1)).deleteById(id);
    }

    @Test
    void givenBookId_whenDeleteBook_andFound_thenAccepted() throws Exception {
        final Book book = createDummyBook();
        doNothing().when(this.bookService).deleteById(book.getId());
        this.mockMvc.perform(delete(String.format(ITEM_PATH, book.getId())))
                .andExpect(status().isAccepted());
        verify(this.bookService, times(1)).deleteById(book.getId());
    }

    private static BookDto createDummyBookDto() {
        return BookDto.builder()
                .id(1L)
                .title("book")
                .description("book")
                .author("book")
                .isbn("book")
                .build();
    }

    private static Book createDummyBook() {
        return Book.builder()
                .id(1L)
                .title("book")
                .description("book")
                .author("book")
                .isbn("book")
                .build();
    }
}
