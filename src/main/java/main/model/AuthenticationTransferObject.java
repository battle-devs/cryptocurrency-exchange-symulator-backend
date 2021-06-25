package main.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthenticationTransferObject {

    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String role;
    private String token;
}
