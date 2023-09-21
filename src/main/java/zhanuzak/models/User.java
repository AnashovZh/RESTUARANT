package zhanuzak.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zhanuzak.enums.Role;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private int experience;
    @ManyToOne(cascade = {MERGE,REFRESH,DETACH,PERSIST})
    private Restaurant restaurant;
    @OneToMany(mappedBy = "user",cascade = {MERGE,REFRESH,DETACH,PERSIST})
    private List<Cheque> cheques;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}