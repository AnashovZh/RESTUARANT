package zhanuzak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhanuzak.dto.request.SignInRequest;
import zhanuzak.dto.request.SignUpRequest;
import zhanuzak.dto.response.AuthenticationResponse;
import zhanuzak.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    @Secured("ADMIN")
    AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }
}
