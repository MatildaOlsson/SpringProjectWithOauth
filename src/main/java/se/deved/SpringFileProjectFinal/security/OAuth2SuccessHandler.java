package se.deved.SpringFileProjectFinal.security;

import ch.qos.logback.core.subst.Token;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.services.UserService;

import java.io.IOException;
import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
//    private final UserService userService;
//
//    public void onAuthenticationSuccess(@NonNull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) throws IOException {
//    var oauth2Token = (OAuth2AuthenticationToken) authentication;
//
//    var oidcProvider = oauth2Token.getAuthorizedClientRegistrationId();
//    var oidcId = oauth2Token.getName();
//
//    System.out.println("Provider: " + oidcProvider);
//    System.out.println("Id: " + oidcId);
//
//    Optional<User> user = userService.getUserByOidc(oidcId);
//
//    if (user.isEmpty()) {
//        User createdUser = userService.createOidcUser(oauth2Token.getName(),oidcId, oidcProvider);
//        if (createdUser == null) {
//            response.getWriter().println("Couldn't create new user");
//        }
//    } else {
//        response.getWriter().println("Logged in as: " + user.get().getUsername());
//    }
//}
//
//}
