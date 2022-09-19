package com.example.test.controller;

import com.example.test.converter.BookConverter;
import com.example.test.dto.BookDto;
import com.example.test.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = "BookController")
@ApiResponses(value = {
        @ApiResponse(code = 404, message = "Not Found")
})
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success|Created")
    })
    @ApiOperation(value = "Create new Book.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookDto bookDto) {
        return this.bookService.create(bookDto, this.bookConverter);
    }

    @ApiResponse(code = 200, message = "Success|OK")
    @ApiOperation(value = "Update existing Book by Id.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable("id") Long id,
                              @Valid @RequestBody BookDto bookDto) {
        return this.bookService.update(id, bookDto, this.bookConverter);
    }


    @ApiResponse(code = 200, message = "Success|OK")
    @ApiOperation(value = "Get list of Books.")
    @GetMapping
    public List<BookDto> getBooks() {
        return this.bookService.getBooks(this.bookConverter);
    }

    @ApiResponse(code = 200, message = "Success|OK")
    @ApiOperation(value = "Get existing Book by Id.")
    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return this.bookService.getById(id, this.bookConverter);
    }

    @ApiResponse(code = 202, message = "Success|Accepted")
    @ApiOperation(value = "Delete existing Book by Id.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable("id") Long id) {
        this.bookService.deleteById(id);
    }
}
