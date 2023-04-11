package projectbuildup.mivv.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.global.error.ErrorCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    // 권한이 없을 경우, AuthorizationException을 처리
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        final Map<String, Object> body = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        body.put("status", ErrorCode.NOT_AUTHORIZED.getStatusCode().value());
        body.put("code", ErrorCode.NOT_AUTHORIZED.getCode());
        body.put("name", ErrorCode.NOT_AUTHORIZED.name());
        body.put("message", ErrorCode.NOT_AUTHORIZED.getMessage());
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
