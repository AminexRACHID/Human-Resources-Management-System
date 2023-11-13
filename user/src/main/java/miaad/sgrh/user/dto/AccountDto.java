package miaad.sgrh.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String login;
    private String password;
    private String role;
    private boolean confirmation;
}