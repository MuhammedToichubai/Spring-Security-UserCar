package peaksoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import peaksoft.repositories.UserRepository;

/**
 * @author Mukhammed Asantegin
 */
@Configuration
@EnableWebSecurity
public class WebAppSecurity {
    private final UserRepository userRepository;

    public WebAppSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        auth -> auth
                                .anyRequest().permitAll()
                ).formLogin().permitAll();
        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationManager(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService( email ->
            userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found!"))
        );
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
