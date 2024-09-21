package com.niceactimize.workshop.service;

import com.niceactimize.workshop.dto.UserCreateDto;
import com.niceactimize.workshop.exception.UserExistsException;
import com.niceactimize.workshop.exception.UserExistsException.Reason;
import com.niceactimize.workshop.model.User;
import com.niceactimize.workshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public void registrationProcess(UserCreateDto userCreateDto) {
    if (userRepository.existsByUsernameIgnoreCase(userCreateDto.username())) {
      throw new UserExistsException(Reason.USERNAME);
    } else if (userRepository.existsByEmailIgnoreCase(userCreateDto.email())) {
      throw new UserExistsException(Reason.EMAIL);
    } else if (userCreateDto.mobile() != null && !userCreateDto.mobile().isBlank() &&
        userRepository.existsByMobile(userCreateDto.mobile())) {
      throw new UserExistsException(Reason.MOBILE);
    }

    User user = User.builder()
        .username(userCreateDto.username())
        .email(userCreateDto.email())
        .build();
    if (userCreateDto.mobile() != null && !userCreateDto.mobile().isBlank()) {
      user.setMobile(userCreateDto.mobile());
    }

    userRepository.save(user);
  }
}
