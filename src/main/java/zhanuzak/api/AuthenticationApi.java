package zhanuzak.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.SignInRequest;
import zhanuzak.dto.request.SignUpRequest;
import zhanuzak.dto.response.AuthenticationResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "AUTHENTICATION_API")
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    @Secured("ADMIN")
    @Operation(summary = "signUp", description = "Here Admin can add employees in his restaurant")
    AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/registration")
    SimpleResponse registration(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authenticationService.registration(signUpRequest);
    }

    @PostMapping("/acceptOrNotAccepted")
    @Secured("ADMIN")
    SimpleResponse acceptOrNotAccepted(@RequestParam String email,
                                       @RequestParam String acceptOrNotAccepted) {
        return authenticationService.acceptOrNotAccepted(email, acceptOrNotAccepted);
    }
}
