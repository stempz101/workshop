package com.niceactimize.workshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "mobile")
  private String mobile;
}
