package com.project.poblog.global.config;

import com.project.poblog.global.auth.authenticationfilter.JwtAuthenticationFilter;
import com.project.poblog.global.auth.authenticationfilter.LoginAuthenticationFilter;
import com.project.poblog.global.auth.authenticationfilter.PasswordAuthenticationFilter;
import com.project.poblog.global.auth.authenticationprovider.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public AuthenticationManager authenticationManager(LoginAuthenticationProvider loginAuthenticationProvider) {
        return new ProviderManager(List.of(
                loginAuthenticationProvider
        ));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector,
                                                   LoginAuthenticationFilter loginAuthenticationFilter,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   PasswordAuthenticationFilter updatePasswordAuthenticationFilter) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        MvcRequestMatcher[] permitWhiteList = {
                mvc.pattern("/user/update"),
                mvc.pattern("/user/update/password"),
                mvc.pattern("/user/delete"),
                mvc.pattern("/user/get"),
                
                mvc.pattern("/post"),
                mvc.pattern("/post/{postId}")

        };

        http
                .addFilterBefore(loginAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(updatePasswordAuthenticationFilter, JwtAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //JWT를 사용하기 때문에 세션 미사용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permitWhiteList).authenticated()
                        .anyRequest().permitAll());

        return http.build();
    }
}
