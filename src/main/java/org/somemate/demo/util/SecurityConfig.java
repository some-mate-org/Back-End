package org.somemate.demo.util;

import org.somemate.demo.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .csrf(csrf -> csrf.disable())    // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/questions/**",
                                "/user/checkUserId",
                                "/user/login",
                                "/user/register",
                                "/user/getMatchedUserInfo/**",
                                "/mbti/**",
                                "/matching/history/**",
                                "/user/updateUserMbti",
                                //"/user/refresh" // 토큰 갱신 경로 추가
                                "/user/updateUserMbti",
                                "/user/refresh" // 토큰 갱신 경로 추가
                        ).permitAll() // 인증 없이 접근 가능한 경로
                        .requestMatchers("/mainuser").authenticated()
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .addFilterAfter(new JWTAuthenticationFilter(jwtUtil), CorsFilter.class) // CORS 필터 다음에 JWT 필터 추가
                .formLogin(form -> form.disable()) // 기본 제공하는 form login 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // 기본 제공하는 http basic 인증 비활성화

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 여러 도메인에서 접근 가능하도록 설정
        corsConfiguration.setAllowedOrigins(List.of(
                "http://localhost:5173",   // 개발 환경
                "http://10.10.222.159:5173", //  네트워크 환경에서 접속하려는 IP 주소
                "http://localhost:8080",
                "http://192.168.219.177:5173",  // 네트워크 환경에서 접속하려는 IP 주소
                "http://125.249.153.42:5173"     // 사용자의 공인 IP 주소 추가
        ));

        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true); // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
