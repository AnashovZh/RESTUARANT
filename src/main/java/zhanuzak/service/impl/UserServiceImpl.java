package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.enums.Role;
import zhanuzak.exceptions.exception.BadCreadentialException;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Restaurant;
import zhanuzak.models.User;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.UserService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.getAll();
    }

    @Override
    public List<UserResponse> getAllOnlyByRestaurants() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with emil:" + " not found !!!"));

        Restaurant restaurant = user.getRestaurant();
        Long restaurantId = restaurant.getId();
        return userRepository.getAllUsers(restaurantId);
    }

    @Override
    public List<UserResponse> getAllWaitingEmployees() {
        List<User> all1 = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : all1) {
            if (user.getRestaurant() == null) {
                userResponses.add(new UserResponse(user.getId(), user.getFirstName(), user.getDateOfBirth()
        ,user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getRole(), user.getExperience()));
            }
        }
        return userResponses;
    }
 

    @Override
    public SimpleResponse delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id:" + id + "not found!!"));
        Restaurant restaurant = user.getRestaurant();
        restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees()-1);
        userRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id:" + id + " successfully deleted ☺")
                .build();
    }

    @Override
    public SimpleResponse updateMap(Long id, Map<String, Object> fields) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id:" + id + " not found !!!"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object fieldValue = null;
                if (value instanceof String && field.getType() == String.class) {
                    fieldValue = value;
                } else if (value instanceof Role && field.getType() == Role.class) {
                    fieldValue = value;
                }
                if (fieldValue != null) {
                    ReflectionUtils.setField(field, user, fieldValue);
                }
            }
        });
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id:" + id + " successfully updated ☺")
                .build();
    }
}
