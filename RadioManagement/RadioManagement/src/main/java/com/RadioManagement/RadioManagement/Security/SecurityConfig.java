package com.RadioManagement.RadioManagement.Security;


import com.RadioManagement.RadioManagement.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsServices userDetailsService;
    @Autowired
    JwtFilter jwtFilter;

    //passwordencoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // ok for exam
    }

    //authprovider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setUserDetailsPasswordService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //authmanager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

    //securitychainfilter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        System.out.println("Entered");
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sess->sess.sessionCreationPolicy((SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.headers(head->head.frameOptions(fr->fr.sameOrigin()));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
