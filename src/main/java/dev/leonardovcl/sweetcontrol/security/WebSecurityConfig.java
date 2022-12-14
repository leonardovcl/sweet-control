package dev.leonardovcl.sweetcontrol.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import dev.leonardovcl.sweetcontrol.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
    	http
        	.authorizeRequests(auth -> auth
        		.antMatchers(HttpMethod.GET, "/").permitAll()
        		.antMatchers("/registration").permitAll()
//        		.antMatchers("/admin/**").hasAuthority("ADMIN")
        		.anyRequest().authenticated()
        	)
        	.formLogin(formLogin -> formLogin
            	.permitAll()
        		.loginPage("/login")
    			.defaultSuccessUrl("/home")
    			.failureUrl("/login?error=true")
    		)
            .logout(logout -> logout
            		.logoutSuccessUrl("/login")
            		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
            	.accessDeniedPage("/accessDenied")
            )
            .userDetailsService(userDetailsService);

        return http.build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return (web) -> {
	    	web
	    		.ignoring()
	    		.antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/error");
	    };
	}
	
}
