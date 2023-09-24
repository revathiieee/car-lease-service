package com.sogeti.customerservice.filter;

import com.sogeti.customerservice.util.JWTValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

  private List<String> excludes = Arrays.asList("authenticate", "swagger-ui.html", "swagger-ui/**", "customer-api-docs/**");
  private final String errorUnauthorizedFormat = "Unauthorized: %s";
  private final JWTValidator validator;
  private AntPathMatcher pathMatcher;

  public JWTFilter(JWTValidator validator) {
    this.validator = validator;
    this.pathMatcher = new AntPathMatcher();
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    String header = httpServletRequest.getHeader("Authorization");
    if (header != null) {
      String[] content = httpServletRequest.getHeader("Authorization").split(" ");

      if (content[0].equals("Bearer") && validator.isValid(content[1])) {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
      } else {
        httpServletResponse.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            String.format(errorUnauthorizedFormat, "The token is not valid."));
      }
    } else {
      httpServletResponse.sendError(
          HttpServletResponse.SC_UNAUTHORIZED,
          String.format(errorUnauthorizedFormat, "Authorization header is missing."));
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return excludes.stream().anyMatch(path -> pathMatcher.match("/" + path, request.getServletPath()));
  }
}