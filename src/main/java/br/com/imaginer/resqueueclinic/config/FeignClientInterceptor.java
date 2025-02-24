package br.com.imaginer.resqueueclinic.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_TOKEN_PREFIX = "Bearer ";

  @Override
  public void apply(RequestTemplate requestTemplate) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String token = request.getHeader(AUTHORIZATION_HEADER);

      if (token != null && !token.startsWith(BEARER_TOKEN_PREFIX)) {
        token = BEARER_TOKEN_PREFIX + token;
      }

      if (token != null) {
        requestTemplate.header(AUTHORIZATION_HEADER, token);
      }
    }
  }
}
