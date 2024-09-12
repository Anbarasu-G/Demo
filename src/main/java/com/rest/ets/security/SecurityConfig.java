package com.rest.ets.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
 SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

     return   httpSecurity
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests((authorize)->
                       authorize.requestMatchers(
                               "/admins/register",
                                       "/hrs/register",
                                       "/trainers/register",
                                       "/students/register",
                                       "/users/register/otpVerification"
                                       )
                               .permitAll()
                               .anyRequest()
                               .authenticated()

               )
               .formLogin(Customizer.withDefaults())
               .build();
    }
}
