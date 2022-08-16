package com.example.todo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authz) -> 
				authz.anyRequest()
					 .authenticated())
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/activities", true)
			.failureUrl("/login?error=true")
			.permitAll()
			
			.and()
			.logout()
			.logoutSuccessUrl("/login?logout=true")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll()
			
			.and()
			.rememberMe()
			.key("secureKey")
			.tokenRepository(tokenRepository());

		return http.build();
	}
	
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/bootstrap/css/**",
        											"/bootstrap/js/**",
        											"/jQuery/**",
        											"/register",
        											"/activate");
    }
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl token = new JdbcTokenRepositoryImpl();
		token.setDataSource(dataSource);
		return token;
	}

}
