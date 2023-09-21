package zhanuzak.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zhanuzak.enums.Role;

import java.time.LocalDate;
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;

    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int experience;

    public UserResponse(Long id, String fullName,
                        LocalDate dateOfBirth, String email, String password,
                        String phoneNumber, Role role, int experience) {
        this.id = id;
        this.fullName = fullName;

        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.experience = experience;
    }
}
