package gumuwka.crud_on_boot.web.config;


import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.repository.UserRepository;
import gumuwka.crud_on_boot.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).stream()
                .findFirst()
                .orElseThrow(() -> {
                    SecurityContextHolder.clearContext();
                return new UsernameNotFoundException("Username not found");
                });
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));

        String redirectUrl = isAdmin ? "/admin/users" : "user/"+ user.getId();
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
    }
