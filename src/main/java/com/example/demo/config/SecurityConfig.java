package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 配置 HTTP 授权规则 (访问控制的核心)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 对于 RESTful API，通常禁用 CSRF
                .authorizeHttpRequests(authz -> authz
                        // 允许 USER 和 ADMIN 读取 (GET 请求)
                        .requestMatchers(HttpMethod.GET, "/v1/api/**").hasAnyRole("USER", "ADMIN")

                        // 只允许 ADMIN 进行 CRUD (POST, PUT, DELETE 请求)
                        .requestMatchers(HttpMethod.POST, "/v1/api/**").hasRole("ADMIN") // C (Create)
                        .requestMatchers(HttpMethod.PUT, "/v1/api/**").hasRole("ADMIN")  // U (Update)
                        .requestMatchers(HttpMethod.DELETE, "/v1/api/**").hasRole("ADMIN") // D (Delete)

                        // 其他所有请求必须经过身份验证
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {}); // 使用 HTTP Basic 认证（最简单的方式）
        // .formLogin(Customizer.withDefaults()); // 或者使用表单登录

        return http.build();
    }

    // 2. 配置密码编码器 (必须使用)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. 配置用户详细信息服务 (UserDetailsService) - 示例使用内存用户
    // 实际项目中会替换为从数据库或 LDAP 加载用户
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN", "USER") // 管理员通常也拥有普通用户的权限
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
