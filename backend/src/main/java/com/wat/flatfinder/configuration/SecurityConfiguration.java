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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

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
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept",
                "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods",
                "Access-Control-Allow-Origin", "Access-Control-Expose-Headers", "Access-Control-Max-Age",
                "Access-Control-Request-Headers", "Access-Control-Request-Method", "Age", "Allow", "Alternates",
                "Content-Range", "Content-Disposition", "Content-Description"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);



        http.csrf().disable();
        http.cors().configurationSource(source);
        http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/register").permitAll()
                .antMatchers("/api/offers").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(authFilter()).addFilter(new JwtAuthFilter(authenticationManager(), userDetailsService(), secret)).exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint((HttpStatus.UNAUTHORIZED)));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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
