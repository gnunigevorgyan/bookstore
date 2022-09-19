package com.example.test.service.impl;

import com.example.test.converter.DataConverter;
import com.example.test.domain.Book;
import com.example.test.dto.BookDto;
import com.example.test.exception.EntityNotFoundException;
import com.example.test.repository.BookRepository;
import com.example.test.service.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @NonNull
    @Override
    @Transactional
    public BookDto create(@NonNull BookDto BookDto,
                          @NonNull DataConverter<Book, BookDto> converter) {
        log.info("Create BookService was invoked with BookDto={}", BookDto);
        final Book book = converter.toEntity(BookDto);
        final BookDto savedBookDto = converter.toDto(this.bookRepository.save(book));
        log.info("Create BookService ended savedBookDto={}", savedBookDto);
        return savedBookDto;
    }

    @NonNull
    @Override
    @Transactional
    public BookDto update(@NonNull Long id,
                          @NonNull BookDto bookDto,
                          @NonNull DataConverter<Book, BookDto> converter) {
        log.info("Update BookService was invoked with id={} and bookDto={}", id, bookDto);
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update BookService book with id={} was not found", id);
                    return new EntityNotFoundException(String.format("Book with id=%s was not found.", id));
                });
        BookServiceImpl.updateBookFromDto(book, bookDto);
        final BookDto savedBookDto = converter.toDto(bookRepository.save(book));
        log.info("Update BookService ended savedBookDto={}", savedBookDto);
        return savedBookDto;
    }

    public static void updateBookFromDto(final Book entity, BookDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setAuthor(dto.getAuthor());
        entity.setIsbn(dto.getIsbn());
    }

    @NonNull
    @Override
    @Transactional
    public BookDto getById(@NonNull Long id,
                           @NonNull DataConverter<Book, BookDto> converter) {
        log.info("GetById BookService was invoked with id={}", id);
        final Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("GetById BookService book with id={} was not found", id);
                    return new EntityNotFoundException(String.format("Book with id=%s was not found.", id));
                });
        final BookDto bookDto = converter.toDto(book);
        log.info("GetById BookService ended bookDto={}", bookDto);
        return bookDto;
    }

    @Override
    public @NonNull List<BookDto> getBooks(@NonNull DataConverter<Book, BookDto> converter) {
        log.info("GetBooks BookService was invoked");
        final List<BookDto> dtoList = converter.toDtoList(this.bookRepository.findAll());
        log.info("GetBooks BookService ended dtoListSize={}", dtoList.size());
        return dtoList;
    }

    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        log.info("DeleteById BookService was invoked with id={}", id);
        if (!this.bookRepository.existsById(id)) {
            log.info("DeleteById BookService book with id={} was not found", id);
            throw new EntityNotFoundException(String.format("Book with id=%s was not found.", id));
        }
        this.bookRepository.deleteById(id);
        log.info("DeleteById BookService ended");
    }
}
