package com.niceactimize.workshop.workshop.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceactimize.workshop.controller.AuthenticationController;
import com.niceactimize.workshop.dto.UserCreateDto;
import com.niceactimize.workshop.exception.UserExistsException;
import com.niceactimize.workshop.exception.UserExistsException.Reason;
import com.niceactimize.workshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthenticationController.class)
public class AuthenticationControllerTest {

  @MockBean
  private UserService userService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void registrationProcess_Success() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    doNothing().when(userService).registrationProcess(userCreateDto);

    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void registrationProcess_MobileIsNull_Success() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", null);

    // When
    doNothing().when(userService).registrationProcess(userCreateDto);

    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void registrationProcess_MobileIsBlank_Success() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "   ");

    // When
    doNothing().when(userService).registrationProcess(userCreateDto);

    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void registrationProcess_UsernameIsNull_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto(null, "Johndoe@gmail.com", "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("Username is not specified"))
        );
  }

  @Test
  void registrationProcess_UsernameIsBlank_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("   ", "Johndoe@gmail.com", "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("Username is not specified"))
        );
  }

  @Test
  void registrationProcess_EmailIsNull_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", null, "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("Email is not specified or is not valid (Example: johndoe@gmail.com)"))
        );
  }

  @Test
  void registrationProcess_EmailIsBlank_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "   ", "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("Email is not specified or is not valid (Example: johndoe@gmail.com)"))
        );
  }

  @Test
  void registrationProcess_EmailIsNotValid_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoegmailcom", "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("Email is not specified or is not valid (Example: johndoe@gmail.com)"))
        );
  }

  @Test
  void registrationProcess_UsernameAndEmailAreNull_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto(null, null, "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(2)),
            jsonPath("$.messages").value(containsInAnyOrder(
                "Username is not specified",
                "Email is not specified or is not valid (Example: johndoe@gmail.com)"
            ))
        );
  }

  @Test
  void registrationProcess_UsernameAndEmailAreBlank_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("  ", "", "+380661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(2)),
            jsonPath("$.messages").value(containsInAnyOrder(
                "Username is not specified",
                "Email is not specified or is not valid (Example: johndoe@gmail.com)"
            ))
        );
  }

  @Test
  void registrationProcess_MobileIsNotValid_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "661886930");

    // When
    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("Mobile is not valid (Example: +40751234876)"))
        );
  }

  @Test
  void registrationProcess_UserExistsByUsername_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    doThrow(new UserExistsException(Reason.USERNAME)).when(userService).registrationProcess(userCreateDto);

    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("User is already exists by specified username"))
        );
  }

  @Test
  void registrationProcess_UserExistsByEmail_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    doThrow(new UserExistsException(Reason.EMAIL)).when(userService).registrationProcess(userCreateDto);

    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("User is already exists by specified email"))
        );
  }

  @Test
  void registrationProcess_UserExistsByMobile_Failure() throws Exception {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    doThrow(new UserExistsException(Reason.MOBILE)).when(userService).registrationProcess(userCreateDto);

    ResultActions result = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDto)));

    // Then
    result
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
            jsonPath("$.timestamp").value(notNullValue()),
            jsonPath("$.messages").value(hasSize(1)),
            jsonPath("$.messages").value(contains("User is already exists by specified mobile number"))
        );
  }
}
