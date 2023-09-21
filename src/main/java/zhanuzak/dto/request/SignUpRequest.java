package zhanuzak.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "must not be null firstName !!!")
    @NotEmpty(message = "must not be empty firstName !!!")
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @EmailValidation
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    @PhoneNumberValidation
    private String phoneNumber;
    @NotNull
    private Role role;
    @NotNull
    private int experience;


}
