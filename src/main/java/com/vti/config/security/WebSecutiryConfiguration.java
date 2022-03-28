package com.vti.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.vti.config.exception.AuthExceptionHandler;
import com.vti.service.IAccountService;

@Component
@EnableWebSecurity
public class WebSecutiryConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private AuthExceptionHandler authExceptionHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.exceptionHandling()
        .authenticationEntryPoint(authExceptionHandler)
        .accessDeniedHandler(authExceptionHandler)
        .and()
		.authorizeRequests()
			.antMatchers("/api/v1/departments/admin/**").hasAuthority("ADMIN")
			.antMatchers("/api/v1/accounts/admin/**").hasAuthority("ADMIN")
			.antMatchers("/api/v1/departments").hasAnyAuthority("ADMIN", "MANAGER")
			.antMatchers("/api/v1/accounts").hasAnyAuthority("ADMIN", "MANAGER")
//			.antMatchers("/api/v1/accounts/user/").hasAnyAuthority("ADMIN", "MANAGER","EMPLOYEE")
			.anyRequest().authenticated()
//			.anyRequest().permitAll()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
	    configuration.applyPermitDefaultValues();
	    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
