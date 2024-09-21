package com.niceactimize.workshop.dto;

import com.niceactimize.workshop.dto.validation.Email;
import com.niceactimize.workshop.dto.validation.Mobile;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
    @NotBlank(message = "{validation.user.not-blank.username}")
    String username,

    @Email
    String email,

    @Mobile
    String mobile
) {

}
