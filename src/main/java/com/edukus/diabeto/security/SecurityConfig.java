package com.edukus.diabeto.security;

import com.edukus.diabeto.security.keycloak.CustomKeycloakAuthenticationProvider;
import com.edukus.diabeto.security.keycloak.CustomKeycloakSpringBootConfigResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@KeycloakConfiguration
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	/**
	 * Registers the KeycloakAuthenticationProvider with the authentication manager.
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = new CustomKeycloakAuthenticationProvider();
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	/**
	 * Provide a session authentication strategy bean which should be of type
	 * RegisterSessionAuthenticationStrategy for public or confidential applications
	 * and NullAuthenticatedSessionStrategy for bearer-only applications.
	 */
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	/**
	 * Use properties in application.properties instead of keycloak.json
	 */
	@Bean
	@Primary
	public KeycloakConfigResolver keycloakConfigResolver(KeycloakSpringBootProperties properties) {
		return new CustomKeycloakSpringBootConfigResolver(properties);
	}

	/**
	 * Secure appropriate endpoints
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers("/public/**").permitAll()
				.antMatchers("/private/doctor/**").hasRole("DOCTOR")
				.antMatchers("/private/patient/**").hasRole("PATIENT")
				.anyRequest().authenticated();
	}


	// Spring Boot attempts to eagerly register filter beans with the web
	// application context. Therefore, when running the Keycloak Spring Security
	// adapter in a Spring Boot environment, it may be necessary to add two
	// FilterRegistrationBeans to your security configuration to prevent the
	// Keycloak filters from being registered twice.

	@Bean
	public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
			KeycloakAuthenticationProcessingFilter filter) {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}