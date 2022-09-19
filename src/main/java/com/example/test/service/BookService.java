package com.example.test.service;

import com.example.test.converter.DataConverter;
import com.example.test.domain.Book;
import com.example.test.dto.BookDto;
import lombok.NonNull;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface BookService {

    @NonNull
    BookDto getById(@NonNull Long id,
                    @NonNull DataConverter<Book, BookDto> converter);

    @NonNull
    BookDto create(@NonNull BookDto bookDto,
                   @NonNull DataConverter<Book, BookDto> converter);

    @NonNull
    BookDto update(@NonNull Long id,
                   @NonNull BookDto bookDto,
                   @NonNull DataConverter<Book, BookDto> converter);

    @NonNull List<BookDto> getBooks(@NonNull DataConverter<Book, BookDto> converter);

    void deleteById(@NonNull Long id);

}
