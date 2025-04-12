package com.residencia.backend.shareds.seguranca;

import com.residencia.backend.shareds.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    SecurityContextHolder.getContext().setAuthentication(null);
    if(header != null){
      var subjectToken = this.jwtProvider.validarToken(header);
      if(subjectToken.isEmpty()){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      request.setAttribute("id_usuario",subjectToken);
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request,response);
  }
}
