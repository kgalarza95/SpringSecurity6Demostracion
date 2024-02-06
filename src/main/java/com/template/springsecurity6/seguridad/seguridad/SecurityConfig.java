package com.template.springsecurity6.seguridad.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.template.springsecurity6.seguridad.seguridad.JWTRequestFiltro;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author kgalarza
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JWTRequestFiltro jwtRequestFiltro;

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // (2)
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/publico/**").permitAll()
                .requestMatchers("/privado/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/privado/**").authenticated()
                .anyRequest().authenticated()
                )
                .cors(withDefaults())
                .addFilterBefore(jwtRequestFiltro, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
