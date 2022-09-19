package com.example.test.converter.impl;

import com.example.test.converter.BookConverter;
import com.example.test.domain.Book;
import com.example.test.dto.BookDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class BookConverterImpl implements BookConverter {

    @NonNull
    @Override
    public BookDto toDto(@NonNull Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    @NonNull
    @Override
    public Book toEntity(@NonNull BookDto book) {
        return Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
