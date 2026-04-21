package se.deved.SpringFileProjectFinal.security;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import se.deved.SpringFileProjectFinal.models.User;
import se.deved.SpringFileProjectFinal.services.UserService;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        System.out.println("---> " + authorization);
        if (authorization != null && authorization.startsWith("Bearer")) {

            String token = extractToken(authorization);
            System.out.println("Token:" + token);
            String userid;
            try {
                User user = userService.authenticateUser(token);
                userid = user.getOidcId();

            } catch (AuthException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Not authorized\"}");
                return;
            }

            // "Berättar" för sping vem som är usern
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            userid, token, List.of()
                    ));
        }

        filterChain.doFilter(request, response);
    }

    protected String extractToken (String authorization) { //TODO
        // input token -- (verify dvs look-up) --> user --> setAuthentication(user)
        // select username from users where token == token; -> username
        // catch "no user found"
        return authorization.substring(7);
    }
}
