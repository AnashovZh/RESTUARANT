package zhanuzak.service;

import zhanuzak.dto.request.SignInRequest;
import zhanuzak.dto.request.SignUpRequest;
import zhanuzak.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    void init();

    AuthenticationResponse signIn(SignInRequest signInRequest);

}
