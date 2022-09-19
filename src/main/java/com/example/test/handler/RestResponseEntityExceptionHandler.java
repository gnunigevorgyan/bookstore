package com.example.test.handler;

import com.example.test.exception.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * ControllerAdvice class for controlling the exceptions which happened in other components.
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle the exception which is happened in another component create not ok http response.
     *
     * @param ex      current Exception.
     * @param headers current request Headers.
     * @param status  current HttpStatus.
     * @param request current request.
     * @return the response to the client.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        final Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .message("Not correct response body.")
                .errors(errors)
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class, NoSuchElementException.class})
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final RuntimeException ex) {
        log.error("ERROR:", ex);
        return new ResponseEntity<>(
                RestResponseEntityExceptionHandler.createBody(ex, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
//
//    @ExceptionHandler(value = BadRequestException.class)
//    protected ResponseEntity<ErrorResponse> handleBadRequest(final RuntimeException ex) {
//        log.error("ERROR:", ex);
//        return new ResponseEntity<>(
//                RestResponseEntityExceptionHandler.createBody(ex, HttpStatus.BAD_REQUEST),
//                HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = InternalServerException.class)
//    public ResponseEntity<ErrorResponse> handleInternalServerException(final RuntimeException ex) {
//        log.error("ERROR:", ex);
//        return new ResponseEntity<>(
//                RestResponseEntityExceptionHandler.createBody(ex, HttpStatus.INTERNAL_SERVER_ERROR),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable ex) {
        log.error("ERROR:", ex);
        return new ResponseEntity<>(
                RestResponseEntityExceptionHandler.createBody(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorResponse createBody(final Throwable obj, final HttpStatus status) {
        return ErrorResponse.builder()
                .status(status.value())
                .timestamp(System.currentTimeMillis())
                .message(obj.getMessage())
                .build();
    }

    private static ErrorResponse createBody() {
        return ErrorResponse.builder()
                .status(500)
                .timestamp(System.currentTimeMillis())
                .message("Server can't process the request.")
                .build();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class ErrorResponse {

        private String message;

        private Integer status;

        private Long timestamp;

        private Map<String, String> errors;

    }
}
