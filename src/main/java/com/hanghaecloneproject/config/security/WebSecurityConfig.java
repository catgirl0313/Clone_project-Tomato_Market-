package com.hanghaecloneproject.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.hanghaecloneproject.config.security.filter.FormLoginFilter;
import com.hanghaecloneproject.config.security.filter.JwtAuthFilter;
import com.hanghaecloneproject.config.security.jwt.JwtUtils;
import com.hanghaecloneproject.config.security.provider.FormLoginProvider;
import com.hanghaecloneproject.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(JwtUtils jwtUtils, UserService userService,
          PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager(), jwtUtils);
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(authenticationManager(), userService,
              jwtUtils);
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors(withDefaults());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
              .authorizeRequests(request -> request
                    .antMatchers("/",
                          "/user/signup", "/user/login",
                          "/user/idCheck/**", "/user/nicknameCheck/**",
                          "/api/posts").permitAll()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .anyRequest().authenticated())
              .addFilterAt(formLoginFilter, UsernamePasswordAuthenticationFilter.class)
              .addFilterAt(jwtAuthFilter, BasicAuthenticationFilter.class);
    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
              "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(formLoginProvider());
    }

    @Bean
    public FormLoginProvider formLoginProvider() {
        return new FormLoginProvider(userService, passwordEncoder);
    }
}
