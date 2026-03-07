package se.deved.SpringFileProjectFinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.deved.SpringFileProjectFinal.services.UserService;


//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, UserService userService, OAuth2SuccessHandler oauth2SuccessHandler) throws Exception {
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/user/register").permitAll()
//                        .requestMatchers("/oauth2/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .successHandler(oauth2SuccessHandler)
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}


//@Bean
//public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http.oauth2Login(oauth -> {
//        oauth.successHandler(((request, response, authentication) -> {
//            System.out.println("AUTHORIZED");
//            //response.sendRedirect("/");
//        }));
//    });
//    return http.build();
//}
//}
