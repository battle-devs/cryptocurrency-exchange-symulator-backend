package main.dto;

import lombok.Data;

@Data
public class AuthenticationTransferObject {

    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String role;
    private String token;
}
