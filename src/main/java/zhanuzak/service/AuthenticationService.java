package zhanuzak.service;

import zhanuzak.dto.request.SignInRequest;
import zhanuzak.dto.request.SignUpRequest;
import zhanuzak.dto.response.AuthenticationResponse;
import zhanuzak.dto.response.SimpleResponse;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    void init();

    AuthenticationResponse signIn(SignInRequest signInRequest);

    SimpleResponse registration(SignUpRequest signUpRequest);

    SimpleResponse acceptOrNotAccepted(String email, String acceptOrNotAccepted);
}
