package com.wat.flatfinder.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wat.flatfinder.auth.FailureHandler;
import com.wat.flatfinder.auth.SuccessHandler;
import com.wat.flatfinder.filters.JsonObjectAuthFilter;
import com.wat.flatfinder.filters.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final ObjectMapper objectMapper;
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;
    private final String secret;

    public SecurityConfiguration(DataSource dataSource, ObjectMapper objectMapper, SuccessHandler successHandler, FailureHandler failureHandler, @Value("${jwt.secret}") String secret) {
        this.dataSource = dataSource;
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/register").permitAll()
//.antMatchers("/hello").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(authFilter()).addFilter(new JwtAuthFilter(authenticationManager(), userDetailsService(), secret)).exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint((HttpStatus.UNAUTHORIZED)));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//
//                .withUser("kingus16")
//
//                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("kinga1234"))
//                .roles("USER")
//                ;
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());

    }
    @Bean
    public UserDetailsManager userDatailsManager(){
        return new JdbcUserDetailsManager(dataSource);
    }

    public JsonObjectAuthFilter authFilter() throws Exception{
        JsonObjectAuthFilter authFilter = new JsonObjectAuthFilter(objectMapper);
        authFilter.setAuthenticationSuccessHandler((successHandler));
        authFilter.setAuthenticationFailureHandler((failureHandler));
authFilter.setAuthenticationManager(super.authenticationManager());
        return authFilter;
    }


}
