package com.niceactimize.workshop.controller;

import com.niceactimize.workshop.dto.UserCreateDto;
import com.niceactimize.workshop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;

  @GetMapping("/register")
  public String registrationPage() {
    return "registration";
  }

  @PostMapping("/register")
  @ResponseBody
  public void registrationProcess(@RequestBody @Valid UserCreateDto userCreateDto) {
    userService.registrationProcess(userCreateDto);
  }
}
