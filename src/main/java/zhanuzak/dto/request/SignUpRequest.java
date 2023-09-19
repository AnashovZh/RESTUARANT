package zhanuzak.dto.request;

import lombok.*;
import zhanuzak.enums.Role;
import zhanuzak.validation.EmailValidation;
import zhanuzak.validation.PhoneNumberValidation;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SignUpRequest {
    private  String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @EmailValidation
    private String email;
    private String password;
    @PhoneNumberValidation
    private String phoneNumber;
    private Role role;
    private int experience;


}
