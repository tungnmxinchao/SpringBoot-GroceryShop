package org.grocery.groceryshop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecuriryConfig {


    private final String[]  PUBLIC_ENDPOINS_POST = {"/auth/log-in", "/auth/introspect", "/users"};
    private final String[]  PUBLIC_ENDPOINS_GET = {"/category", "/product/public", "/product/id/*", "/product/category/*"};
    private final String[]  ADMIN_ENDPOINS_GET = {"/order", "/order/*","/category/top-selling", "/product/admin", "/users",
            "/users/id/*"};
    private final String[]  ADMIN_ENDPOINS_POST = {"/product"};
    private final String[]  USER_ENDPOINS_POST = {"/order", "/product"};
    private final String[]  USER_ENDPOINS_GET = {"/order/user/*", "/order/user/trackOrder/*"};
    private final String[]  ADMIN_ENDPOINS_PUT = {"/product/delete/*", "/order/update/*",  "/users/delete/*",  "/users/activeUser/*"};

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINS_POST).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINS_GET).permitAll()
                        .requestMatchers(HttpMethod.GET, ADMIN_ENDPOINS_GET).hasAnyAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, USER_ENDPOINS_GET).hasAnyAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.POST, ADMIN_ENDPOINS_POST).hasAnyAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, USER_ENDPOINS_POST).hasAnyAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.PUT, ADMIN_ENDPOINS_PUT).hasAnyAuthority("SCOPE_ADMIN")
                        .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
