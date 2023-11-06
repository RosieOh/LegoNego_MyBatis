package com.chunjae.legonego.conf;


import com.chunjae.legonego.biz.UserService;
import com.chunjae.legonego.biz.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserService userService() { return new UserServiceImpl();  }

    //passwordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //공통 접근 설정
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/idCheck")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/emailCheck")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/emp/**")).hasAnyRole("TEACHER","ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyRole("USER","TEACHER","ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/board/**")).permitAll()
                .anyRequest().authenticated());
        //로그인 설정
        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/auth")
            .usernameParameter("name")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .failureForwardUrl("/");
        //로그아웃 설정
         http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/");

         http.cors().and().csrf().disable();
         http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
         return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
