package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PermitAll
    @GetMapping
    List<UserResponse> getAll() {
        return userService.getAll();
    }

    @Secured("ADMIN")
    @GetMapping("/getAllOnlyByRestaurants")
    List<UserResponse> getAllOnlyByRestaurants() {
        return userService.getAllOnlyByRestaurants();
    }

    @PermitAll
    @GetMapping("/getAllWaitingEmployees")
    List<UserResponse> getAllWaitingEmployees() {
        return userService.getAllWaitingEmployees();
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @Secured("ADMIN")
    @PutMapping("/{id}")
    SimpleResponse updateMap(@PathVariable Long id,
                             @RequestBody Map<String, Object> fields) {
        return userService.updateMap(id, fields);
    }

}
