package org.example.config;


import org.example.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                .antMatchers("/project/**").authenticated()
                .antMatchers("/redirect-index").authenticated()
                .anyRequest().permitAll()
                .and()
                .requiresChannel()
                .antMatchers("/admin/**").requiresSecure()
                .antMatchers("/private-project/**").requiresSecure()
                .antMatchers("/project/**").requiresSecure()
                .anyRequest().requiresInsecure()
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .and()
                .formLogin()
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .and()
                .logout()
                .and()
                .csrf()
                .and()
//                .disable()
// 포스트메소드에 csrf의 토큰값을 넣어줘야함, restapi할땐 disable()사용
                .sessionManagement()
                .sessionFixation()
                .none()
                .and()
                .headers()
                .defaultsDisabled()
                .frameOptions()
                .sameOrigin()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/403")
                .and()
                .build();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider(null));
//    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(github());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .userNameAttributeName("name")
                .clientId("*****")
                .clientSecret("*****")
                .build();
    }

}
