package com.example.SimpleMMO.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
}
