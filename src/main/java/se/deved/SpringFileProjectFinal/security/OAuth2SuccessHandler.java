package se.deved.SpringFileProjectFinal.security;

import jakarta.annotation.Nonnull;
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
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    public void onAuthenticationSuccess(
            @NonNull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

        String oidcId = oauth2Token.getName();

        User user = userService.registerOAuthUser(oidcId);

        response.getWriter().println("Success! Your id: " + oidcId + " Your token: " + user.getPassword());
    }
}
