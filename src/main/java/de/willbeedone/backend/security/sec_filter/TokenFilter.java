package de.willbeedone.backend.security.sec_filter;

import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.security.AuthInfo;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {

    private final TokenService service;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenFilter(TokenService service, JwtService jwtService, UserRepository userRepository) {
        this.service = service;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().equals("/api/auth/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = getTokenFromRequest((HttpServletRequest) servletRequest);


        if (token != null) {
            if (service.validateAccessToken(token)) {
                Claims claims = service.getAccessClaims(token);
                // Устанавливаем аутентификацию
            } else if (service.validateRefreshToken(token)) {
                String email = service.getRefreshClaims(token).getSubject();

                // Получаем пользователя по email
                User user = userRepository.findByEmail(email);
                // Генерируем новый токен с использованием объекта User
                String newAccessToken = service.generateAccessToken(user);

                response.setHeader("New-Access-Token", newAccessToken);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
                return;
            }
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
