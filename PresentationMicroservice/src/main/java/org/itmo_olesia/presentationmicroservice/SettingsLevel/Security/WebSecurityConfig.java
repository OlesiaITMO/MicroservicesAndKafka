package org.itmo_olesia.presentationmicroservice.SettingsLevel.Security;


import lombok.SneakyThrows;
import org.itmo_olesia.presentationmicroservice.Repositories.ICatRepository;
import org.itmo_olesia.presentationmicroservice.Repositories.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.itmo_olesia.presentationmicroservice.BrokersAndServices.Services.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    // --
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используем BCryptPasswordEncoder для хеширования паролей
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @SneakyThrows
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) {
        return http
                //.addFilter(basicAuthSecurityFilter)
                .authorizeHttpRequests(authorize ->
                                authorize
                                .requestMatchers("/users/register").permitAll()
//                                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }


}/*
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используем BCryptPasswordEncoder для хеширования паролей
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(new UserService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/users/register").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()).build();
    }
}*/

