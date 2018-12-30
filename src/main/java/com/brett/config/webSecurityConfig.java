package com.brett.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brett.service.customUserService;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class webSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	UserDetailsService customUserService() {
		// TODO Auto-generated method stub
		return new customUserService();
	}
	
	//@Override
	//protected void configure(AuthenticationManagerBuilder builder) throws Exception{
		//builder.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
	//}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeRequests()
		.antMatchers("/room").anonymous()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
		.and()
		.logout()
		.permitAll();
		httpSecurity.addFilterAt(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); 
		httpSecurity.csrf().disable();
	}
	
	private Filter qqAuthenticationFilter() {
		QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/login/qq");  
        //SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();  
        //successHandler.setAlwaysUseDefaultTargetUrl(true);  
        //successHandler.setDefaultTargetUrl("/user");  
        MyAuthenticationSuccessHandler successHandler = new MyAuthenticationSuccessHandler();  
        authenticationFilter.setAuthenticationManager(new QQAuthenticationManager());  
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);  
        return authenticationFilter; 
	}

	@Override
	public void configure(WebSecurity webSecurity){
		webSecurity.ignoring().antMatchers("/css/**","/js/**");
	}
}
