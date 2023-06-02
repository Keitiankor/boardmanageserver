package com.keitian.boardmanageserver.global.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // actions when request has no permission //
        // bellow is example code //
        if (accessDeniedException instanceof AccessDeniedException) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null &&
                    ((User) authentication.getPrincipal()).getAuthorities()
                            .contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
                request.setAttribute("msg", "have no access authority");
                request.setAttribute("nextPage", "/v");
            } else {
                request.setAttribute("msg", "this account have no authority to login");
                request.setAttribute("nextPage", "/login");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                SecurityContextHolder.clearContext();
            }
        } else {
            // make log //
        }
        request.getRequestDispatcher("/err/denied-page").forward(request, response);
    }
}
