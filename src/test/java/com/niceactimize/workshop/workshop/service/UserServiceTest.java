package com.niceactimize.workshop.workshop.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.niceactimize.workshop.dto.UserCreateDto;
import com.niceactimize.workshop.exception.UserExistsException;
import com.niceactimize.workshop.model.User;
import com.niceactimize.workshop.repository.UserRepository;
import com.niceactimize.workshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Test
  void registrationProcess_Success() {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");
    User user = new User(1L, userCreateDto.username(), userCreateDto.email(), userCreateDto.mobile());

    // When
    when(userRepository.existsByUsernameIgnoreCase(userCreateDto.username())).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase(userCreateDto.email())).thenReturn(false);
    when(userRepository.existsByMobile(userCreateDto.mobile())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.registrationProcess(userCreateDto);

    // Then
    verify(userRepository, times(1)).existsByUsernameIgnoreCase(userCreateDto.username());
    verify(userRepository, times(1)).existsByEmailIgnoreCase(userCreateDto.email());
    verify(userRepository, times(1)).existsByMobile(userCreateDto.mobile());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void registrationProcess_MobileIsNull_Success() {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", null);
    User user = new User(1L, userCreateDto.username(), userCreateDto.email(), userCreateDto.mobile());

    // When
    when(userRepository.existsByUsernameIgnoreCase(userCreateDto.username())).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase(userCreateDto.email())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.registrationProcess(userCreateDto);

    // Then
    verify(userRepository, times(1)).existsByUsernameIgnoreCase(userCreateDto.username());
    verify(userRepository, times(1)).existsByEmailIgnoreCase(userCreateDto.email());
    verify(userRepository, times(0)).existsByMobile(userCreateDto.mobile());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void registrationProcess_MobileIsBlank_Success() {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "  ");
    User user = new User(1L, userCreateDto.username(), userCreateDto.email(), userCreateDto.mobile());

    // When
    when(userRepository.existsByUsernameIgnoreCase(userCreateDto.username())).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase(userCreateDto.email())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.registrationProcess(userCreateDto);

    // Then
    verify(userRepository, times(1)).existsByUsernameIgnoreCase(userCreateDto.username());
    verify(userRepository, times(1)).existsByEmailIgnoreCase(userCreateDto.email());
    verify(userRepository, times(0)).existsByMobile(userCreateDto.mobile());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void registrationProcess_ExistsByUsername_Failure() {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    when(userRepository.existsByUsernameIgnoreCase(userCreateDto.username())).thenReturn(true);

    // Then
    assertThrows(UserExistsException.class, () -> userService.registrationProcess(userCreateDto));
  }

  @Test
  void registrationProcess_ExistsByEmail_Failure() {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    when(userRepository.existsByUsernameIgnoreCase(userCreateDto.username())).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase(userCreateDto.email())).thenReturn(true);

    // Then
    assertThrows(UserExistsException.class, () -> userService.registrationProcess(userCreateDto));
  }

  @Test
  void registrationProcess_ExistsByMobile_Failure() {
    // Given
    UserCreateDto userCreateDto = new UserCreateDto("jDoe", "Johndoe@gmail.com", "+380661886930");

    // When
    when(userRepository.existsByUsernameIgnoreCase(userCreateDto.username())).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase(userCreateDto.email())).thenReturn(false);
    when(userRepository.existsByMobile(userCreateDto.mobile())).thenReturn(true);

    // Then
    assertThrows(UserExistsException.class, () -> userService.registrationProcess(userCreateDto));
  }
}
