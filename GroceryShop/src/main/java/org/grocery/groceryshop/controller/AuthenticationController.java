package org.grocery.groceryshop.controller;

import com.nimbusds.jose.JOSEException;
import org.grocery.groceryshop.dto.request.AuthenticationRequest;
import org.grocery.groceryshop.dto.request.IntrospectRequest;
import org.grocery.groceryshop.dto.response.ApiResponse;
import org.grocery.groceryshop.dto.response.AuthenticationResponse;
import org.grocery.groceryshop.dto.response.IntrospectResponse;
import org.grocery.groceryshop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        var result = authenticationService.authenticate(authenticationRequest);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectRequest);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }



}
