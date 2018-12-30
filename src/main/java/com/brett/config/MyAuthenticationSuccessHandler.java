package com.brett.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired  
    private ObjectMapper objectMapper;  
  
    @Override  
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  
            throws IOException, ServletException {  
        //什么都不做的话，那就直接调用父类的方法  
        super.onAuthenticationSuccess(request, response, authentication);  
  
        String url=request.getRequestURI();  
  
        //如果是要跳转到某个页面的  
        new DefaultRedirectStrategy().sendRedirect(request, response, url);  
  
    } 
}
