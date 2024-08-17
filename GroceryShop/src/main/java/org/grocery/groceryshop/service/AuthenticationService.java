package org.grocery.groceryshop.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.grocery.groceryshop.dto.request.AuthenticationRequest;
import org.grocery.groceryshop.dto.request.IntrospectRequest;
import org.grocery.groceryshop.dto.response.AuthenticationResponse;
import org.grocery.groceryshop.dto.response.IntrospectResponse;
import org.grocery.groceryshop.entity.Users;
import org.grocery.groceryshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Value("${jwt.signerKey}")
    private String signerKey;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        var users = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() ->
                new RuntimeException("Not found username!"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean authenticated =  passwordEncoder.matches(authenticationRequest.getPassword() , users.getPassword());

        if(!authenticated)
            throw new RuntimeException("Not authenticated!");

        var token = generateToken(users);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();

    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(verifier);

        Date expityRime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .isValid(verified && expityRime.after(new Date()))
                .build();

    }

    private String generateToken(Users users){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .claim("idUser", users.getUserId())
                .issuer("GroceryShop")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
                .claim("scope", builtScope(users))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {

            throw new RuntimeException(e);
        }


    }

    private String builtScope(Users user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}
