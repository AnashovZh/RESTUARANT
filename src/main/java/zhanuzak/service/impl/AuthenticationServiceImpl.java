package zhanuzak.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zhanuzak.dto.request.SignInRequest;
import zhanuzak.dto.request.SignUpRequest;
import zhanuzak.dto.response.AuthenticationResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.enums.RestaurantType;
import zhanuzak.enums.Role;
import zhanuzak.exceptions.exception.*;
import zhanuzak.models.Restaurant;
import zhanuzak.models.User;
import zhanuzak.repository.RestaurantRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.security.jwt.JwtService;
import zhanuzak.service.AuthenticationService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistException("User with email:" + signUpRequest.getEmail() + "already exists !");
        }
        if (signUpRequest.getRole().name().equals("CHEF")) {
            LocalDate dateOfBirth = signUpRequest.getDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(dateOfBirth, currentDate).getYears();
            if (age >= 25 && age <= 45) {
                if (signUpRequest.getExperience() >= 2) {
                    String password = signUpRequest.getPassword();
                    if (password.length() >= 4) {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String email = authentication.getName();
                        User user1 = userRepository.getUserByEmail(email).orElseThrow(() ->
                                new NotFoundException("User with emil:" + " not found !!!"));
                        Restaurant restaurant = user1.getRestaurant();
                        restaurantRepository.save(restaurant);
                        User user = new User();
                        user.setFirstName(signUpRequest.getFirstName());
                        user.setLastName(signUpRequest.getLastName());
                        user.setDateOfBirth(signUpRequest.getDateOfBirth());
                        user.setEmail(signUpRequest.getEmail());
                        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                        user.setPhoneNumber(signUpRequest.getPhoneNumber());
                        user.setRole(signUpRequest.getRole());
                        user.setExperience(signUpRequest.getExperience());
                        if (restaurant.getUsers().size() < 16) {
                            user.setRestaurant(restaurant);
                            restaurantRepository.save(restaurant);
                            String token = jwtService.generateToken(user);
                            userRepository.save(user);
                            return AuthenticationResponse.builder()
                                    .role(user.getRole())
                                    .email(user.getEmail())
                                    .token(token)
                                    .build();
                        } else {
                            throw new MaxUsersExceededException("The restaurant has reached its maximum capacity of 15 users !!!");
                        }
                    } else {
                        throw new BadRequest("Password should be minimum 4 characters !!!");
                    }
                } else {
                    throw new BadRequest("Chef's experience should be 2 years !!! ");
                }
            } else {
                throw new BadRequest("Chef's age should be between 25 and 45 years !!! ");
            }
        } else if (signUpRequest.getRole().name().equals("WAITER")) {
            LocalDate dateOfBirth = signUpRequest.getDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(dateOfBirth, currentDate).getYears();
            if (age >= 18 && age <= 30) {
                if (signUpRequest.getExperience() >= 1) {
                    if (signUpRequest.getPassword().length() > 4) {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String email = authentication.getName();
                        User user1 = userRepository.getUserByEmail(email).orElseThrow(() ->
                                new NotFoundException("User with emil:" + " not found !!!"));
                        Restaurant restaurant = user1.getRestaurant();
                        restaurantRepository.save(restaurant);
                        User user = new User();
                        user.setFirstName(signUpRequest.getFirstName());
                        user.setLastName(signUpRequest.getLastName());
                        user.setDateOfBirth(signUpRequest.getDateOfBirth());
                        user.setEmail(signUpRequest.getEmail());
                        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                        user.setPhoneNumber(signUpRequest.getPhoneNumber());
                        user.setRole(signUpRequest.getRole());
                        user.setExperience(signUpRequest.getExperience());
                        if (restaurant.getUsers().size() < 16) {
                            user.setRestaurant(restaurant);
                            restaurantRepository.save(restaurant);
                            userRepository.save(user);
                            String token = jwtService.generateToken(user);
                            return AuthenticationResponse.builder()
                                    .role(user.getRole())
                                    .email(user.getEmail())
                                    .token(token)
                                    .build();
                        } else {
                            throw new MaxUsersExceededException("The restaurant has reached its maximum capacity of 15 users !!!");
                        }
                    } else {
                        throw new BadRequest("Password should be minimum 4 characters !!!");
                    }
                } else throw new BadRequest("Waiter's experience should be 1 years");
            } else {
                throw new BadRequest("Waiter's age should be between 18 and 30 years !!! ");
            }
        }
        return null;
    }

    @PostConstruct
    @Override
    public void init() {
        User user = new User();
        user.setFirstName("Zhanuzak");
        user.setLastName("Anashov");
        user.setDateOfBirth(LocalDate.of(1999, 2, 25));
        user.setEmail("zh@gmail.com");
        user.setPassword(passwordEncoder.encode("zhanuzak"));
        user.setPhoneNumber("+996990250299");
        user.setRole(Role.ADMIN);
        user.setExperience(10);
        if (!userRepository.existsByEmail("zh@gmail.com")) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Flavors of the Tian-Shan");
            restaurant.setLocation("Republic of Kyrgyzstan, Issyk-Kol region, Kara-Kol city");
            restaurant.setRestType(RestaurantType.FINE_DINING);
            restaurant.setNumberOfEmployees(15);
//            restaurant.setService(15);
            restaurant.setUsers(List.of(user));
            restaurantRepository.save(restaurant);
            user.setRestaurant(restaurant);
            userRepository.save(user);
        }
        System.err.println("Successfully saved Admin  √  ☺");
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email:" + signInRequest.getEmail() + " not found !!!"));
        if (signInRequest.getEmail().isBlank()) {
            throw new BadCreadentialException("Email is blank ");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadCreadentialException("Wrong password");
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public SimpleResponse registration(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistException("User with email:" + signUpRequest.getEmail() + "already exists !");
        }
        if (signUpRequest.getRole().name().equals("CHEF")) {
            LocalDate dateOfBirth = signUpRequest.getDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(dateOfBirth, currentDate).getYears();
            if (age >= 25 && age <= 45) {
                if (signUpRequest.getExperience() >= 2) {
                    String password = signUpRequest.getPassword();
                    if (password.length() >= 4) {
                        User user = new User();
                        user.setFirstName(signUpRequest.getFirstName());
                        user.setLastName(signUpRequest.getLastName());
                        user.setDateOfBirth(signUpRequest.getDateOfBirth());
                        user.setEmail(signUpRequest.getEmail());
                        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                        user.setPhoneNumber(signUpRequest.getPhoneNumber());
                        user.setRole(signUpRequest.getRole());
                        user.setExperience(signUpRequest.getExperience());
                        userRepository.save(user);
                        return SimpleResponse.builder()
                                .httpStatus(HttpStatus.CREATED)
                                .message("Your application for the job of Chef 👨‍🍳 has been successfully sent ☺")
                                .build();
                    } else {
                        throw new BadRequest("Password should be minimum 4 characters !!!");
                    }
                } else {
                    throw new BadRequest("Chef's experience should be 2 years !!! ");
                }
            } else {
                throw new BadRequest("Chef's age should be between 25 and 45 years !!! ");
            }
        } else if (signUpRequest.getRole().name().equals("WAITER")) {
            LocalDate dateOfBirth = signUpRequest.getDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(dateOfBirth, currentDate).getYears();
            if (age >= 18 && age <= 30) {
                if (signUpRequest.getExperience() >= 1) {
                    if (signUpRequest.getPassword().length() >= 4) {
                        User user = new User();
                        user.setFirstName(signUpRequest.getFirstName());
                        user.setLastName(signUpRequest.getLastName());
                        user.setDateOfBirth(signUpRequest.getDateOfBirth());
                        user.setEmail(signUpRequest.getEmail());
                        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                        user.setPhoneNumber(signUpRequest.getPhoneNumber());
                        user.setRole(signUpRequest.getRole());
                        user.setExperience(signUpRequest.getExperience());
                        userRepository.save(user);
                        return SimpleResponse.builder()
                                .httpStatus(HttpStatus.CREATED)
                                .message("Your application for the job of Waiter 💁‍♂️ has been successfully sent ☺")
                                .build();
                    } else {
                        throw new BadRequest("Password should be minimum 4 characters !!!");
                    }
                } else throw new BadRequest("Waiter's experience should be 1 years");
            } else {
                throw new BadRequest("Waiter's age should be between 18 and 30 years !!! ");
            }
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("You probably spelled 'CHEF' and 'WAITER' incorrectly !!!")
                    .build();
        }
    }

    @Override
    public SimpleResponse acceptOrNotAccepted(String email, String acceptOrNotAccepted) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUser = authentication.getName();
        User user1 = userRepository.getUserByEmail(emailUser).orElseThrow(() ->
                new NotFoundException("User with emil:" + " not found !!!"));
        if (acceptOrNotAccepted.equalsIgnoreCase("accept")) {
            User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                    new NotFoundException("User with email :" + email + " not found !!!"));
            if (user1.getRestaurant().getUsers().size() < 16) {
                user1.getRestaurant().setUsers(List.of(user));
                user.setRestaurant(user1.getRestaurant());
                userRepository.save(user);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message(user.getRole() + " " + user.getFirstName() + " " + user.getLastName() + " " + " successfully ☺ got a job in your restaurant")
                        .build();
            }
        } else if (acceptOrNotAccepted.equalsIgnoreCase("notAccepted")) {
            User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                    new NotFoundException("User with email :" + email + " not found !!!"));
            userRepository.delete(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("You have not successfully accepted ☺ √")
                    .build();
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("You entered the functions incorrectly (notAccepted,accept) !!!")
                .build();
    }
}
