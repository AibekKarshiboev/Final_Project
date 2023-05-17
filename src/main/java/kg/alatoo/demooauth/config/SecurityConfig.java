package kg.alatoo.demooauth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(
                        (request)->request
                                .requestMatchers("/","/login**", "/register", "/about").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll().and()
               .oauth2Login(oauth -> oauth

                        .loginPage("/login")
                       .defaultSuccessUrl("/")
                )
                .logout()
                .logoutUrl("/logout")
                .permitAll();
        return http.build();

    }

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }
}
