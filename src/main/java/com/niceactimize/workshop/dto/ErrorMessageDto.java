package com.niceactimize.workshop.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorMessageDto(
    int status,
    LocalDateTime timestamp,
    List<String> messages
) {

}
