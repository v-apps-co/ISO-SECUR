package com.vapps.iso.configuration.security.oauth2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.vapps.iso.configuration.security.userdetails.CustomUserDetailsService;

@Configuration
public class JWTConfiguration {
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Bean
	public TokenEnhancerChain tokenEnhancerChain() {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> tokenEnhancerList = new ArrayList<TokenEnhancer>();
		tokenEnhancerList.add(new JWTTokenEnhancer());
		tokenEnhancerList.add(tokenEnhancer());

		tokenEnhancerChain.setTokenEnhancers(tokenEnhancerList);
		return tokenEnhancerChain;
	}

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter());

		jwtAccessTokenConverter.setSigningKey("c5da803c8b0611e7bb31be2e44b06b34");

		return jwtAccessTokenConverter;
	}

	@Bean
	public DefaultAccessTokenConverter defaultAccessTokenConverter() {
		DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();

		DefaultUserAuthenticationConverter userConverter = new DefaultUserAuthenticationConverter();
		userConverter.setUserDetailsService(userDetailsService);

		converter.setUserTokenConverter(userConverter);

		return converter;
	}

	@Bean
	public AuthorizationServerTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setClientDetailsService(clientDetailsService);
		defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
