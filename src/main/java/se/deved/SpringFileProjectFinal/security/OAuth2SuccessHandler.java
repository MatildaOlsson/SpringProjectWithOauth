package se.deved.SpringFileProjectFinal.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // Extract user details
        String oidcId = oauth2Token.getName();

        // Generate token
        String token = generateToken();

        // Create/overwrite user
        User user = userService.registerOAuthUser(oidcId, token);
        // write user details to database
        //writeUserToDB(user);

//        Authentication newAuth =
//                new UsernamePasswordAuthenticationToken(
//                        user,
//                        null,
//                        oauth2Token.getAuthorities()
//                );
//
//        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // add the token in the response
        response.getWriter().println("Success! Your id: " + oidcId + " Your token: " + user.getPassword());
    }

    // Metod som kommer att retunera en sträng på 15 tecken som ska användas för identifikation/lösenord till åtkomst till
    public String generateToken() {
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
        return token;
    }
}
