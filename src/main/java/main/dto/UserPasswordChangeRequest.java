package main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPasswordChangeRequest {

    private String newPassword;
}
