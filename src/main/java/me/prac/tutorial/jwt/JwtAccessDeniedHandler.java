package me.prac.tutorial.jwt;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    // 필요한 권한이 존재하지 않는 경우 403 Forbidden 에러 리턴하는 클래스

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}