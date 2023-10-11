package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> getUserByEmail(String email);

    @Query("select new zhanuzak.dto.response.UserResponse(u.id,concat(u.firstName,' ',u.lastName) ,u.dateOfBirth," +
            "u.email,u.password,u.phoneNumber,u.role,u.experience)from User u")
    List<UserResponse> getAll();

    @Query("select new zhanuzak.dto.response.UserResponse(u.id,concat(u.firstName,' ',u.lastName) ,u.dateOfBirth," +
            "u.email,u.password,u.phoneNumber,u.role,u.experience)from User u join Restaurant  r on u.restaurant.id=r.id where r.id=?1")
    List<UserResponse> getAllUsers(Long restaurantId);

}
