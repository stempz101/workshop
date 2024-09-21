package com.niceactimize.workshop.controller.error;

import com.niceactimize.workshop.dto.ErrorMessageDto;
import com.niceactimize.workshop.exception.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

  private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessageDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    LOG.error("handleMethodArgumentNotValidException: {}", ex.getMessage(), ex);

    List<String> messages = ex.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();

    return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), messages);
  }

  @ExceptionHandler(UserExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessageDto handleUserExistsException(Exception ex) {
    LOG.error("handleUserExistsException: {}", ex.getMessage(), ex);

    return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
        List.of(ex.getMessage()));
  }
}
