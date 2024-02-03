package com.flovvorkServer.flovvorkServer.Security;

import com.flovvorkServer.flovvorkServer.Service.AuthenticationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    public DaoAuthenticationProvider authenticationProvider(AuthenticationUserDetailService authenticationUserDetailService)
    {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(authenticationUserDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(
                configurer ->
                configurer.anyRequest().authenticated()).formLogin(
                        form ->

                                form.loginPage("/authenticatePage").loginProcessingUrl("/authenticateUser").permitAll());

        return http.build();
    }


}
