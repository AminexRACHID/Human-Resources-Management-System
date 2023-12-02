package miaad.sgrh.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassDto {
    String pass;
    BCryptPasswordEncoder encoder;
}
