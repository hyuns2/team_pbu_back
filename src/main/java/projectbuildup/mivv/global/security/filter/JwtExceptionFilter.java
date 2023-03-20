package projectbuildup.mivv.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import projectbuildup.mivv.global.error.ErrorCode;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException | CUserNotFoundException e) {
            setErrorResponse(response, e);
        }
    }

    public void setErrorResponse(HttpServletResponse response, Throwable e) throws IOException {
        final Map<String, Object> body = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();

        ErrorCode errorCode = extractErrorCode(e);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        body.put("status", errorCode.getStatusCode().value());
        body.put("code", errorCode.getCode());
        body.put("name", errorCode.name());
        body.put("message", errorCode.getMessage());
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private ErrorCode extractErrorCode(Throwable e) {
        if (e instanceof CUserNotFoundException) {
            return ((CUserNotFoundException) e).getErrorCode();
        }
        return ErrorCode.findByName(e.getMessage());
    }
}
